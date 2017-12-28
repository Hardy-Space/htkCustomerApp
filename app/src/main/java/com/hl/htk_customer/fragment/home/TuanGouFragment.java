package com.hl.htk_customer.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.MemberPlatformActivity;
import com.hl.htk_customer.activity.MyWebViewActivity;
import com.hl.htk_customer.activity.SearchActivity;
import com.hl.htk_customer.adapter.MyPageAdapter;
import com.hl.htk_customer.adapter.RecommendShopListAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.BestShopEntity;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.entity.TgGuangGaoEntity;
import com.hl.htk_customer.fragment.waimai.ProductFragment;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.LocationInfoManager;
import com.hl.htk_customer.model.ScrollTopEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.ImageLoadManager;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 团购 fragment
 */

public class TuanGouFragment extends BaseFragment implements View.OnClickListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private ViewPager mFunctionViewPager;
    private RelativeLayout rlSearch;
    private SmartRefreshLayout mRefresh;
    private RecyclerView mShopList;
    private RadioGroup rg;
    private FrameLayout mErrorView;
    private NestedScrollView mNestedScrollView;
    private ImageView mAdvertisingOne;
    private ImageView mAdvertisingTwo;
    private ImageView mAdvertisingThree;
    private ImageView mAdvertisingFour;
    private ImageView mAdvertisingFive;

    private int mPageNumber = 1;
    private int mMark = 1;//团购
    private BestShopEntity bestShopEntity;

    List<BaseFragment> myFragmentList;
    MyPageAdapter myPageAdapter;
    private RecommendShopListAdapter mRecommendShopListAdapter;
    private UserInfoManager mUserInfoManager;

    private TgGuangGaoEntity tgGuangGaoEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tuangou, null);
            EventBus.getDefault().register(this);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void lazyInitData() {
        if (isVisible && isFirst && isPrepared) {
            isFirst = false;
            initView();
            initWidget();
            initViewPager();
        }
    }

    private void initView() {
        rg =  view.findViewById(R.id.fragment_tuangou_rg);
        mFunctionViewPager =  view.findViewById(R.id.fragment_tuangou_vp);
        rlSearch =  view.findViewById(R.id.rl_search);
        mShopList =  view.findViewById(R.id.fragment_tuangou_recyclerView);
        mRefresh = view.findViewById(R.id.fragment_tuangou_refresh);
        mErrorView = view.findViewById(R.id.fragment_tuangou_error);
        mNestedScrollView = view.findViewById(R.id.fragment_tuangou_scroll);
        mAdvertisingOne = view.findViewById(R.id.iv_guanggao1);
        mAdvertisingTwo = view.findViewById(R.id.iv_guanggao2);
        mAdvertisingThree = view.findViewById(R.id.iv_guanggao3);
        mAdvertisingFour = view.findViewById(R.id.iv_guanggao4);
        mAdvertisingFive = view.findViewById(R.id.iv_guanggao5);

        rlSearch.setOnClickListener(this);
        mErrorView.setOnClickListener(this);
        mAdvertisingOne.setOnClickListener(this);
        mAdvertisingTwo.setOnClickListener(this);
        mAdvertisingThree.setOnClickListener(this);
        mAdvertisingFour.setOnClickListener(this);
        mAdvertisingFive.setOnClickListener(this);
    }

    private void initWidget() {

        mUserInfoManager = new UserInfoManager(getContext());

        mShopList.setLayoutManager(new LinearLayoutManager(mContext));
        mShopList.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        mRecommendShopListAdapter = new RecommendShopListAdapter(R.layout.item_recommend_shop , null );
        mShopList.setAdapter(mRecommendShopListAdapter);

        mRecommendShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BestShopEntity.DataBean item = mRecommendShopListAdapter.getItem(position);
                goWebActivity(item.getShopId());
            }
        });

        showLoading();
        getTuanGouBestShop();

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNumber ++;
                getTuanGouBestShop();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageNumber = 1;
                getTuanGouBestShop();
            }
        });

        getGuanggao();
    }


    /**
     * 进入会员尊享平台
     * @param shopId
     */
    private void goWebActivity(int shopId) {
        Intent intent = new Intent(getActivity(), MemberPlatformActivity.class);
        intent.putExtra("shopId", shopId);
        startActivity(intent);
    }

    private void initViewPager() {
        myFragmentList = new ArrayList<>();
        myFragmentList.add(ProductFragment.newInstance(1, 1));
        myFragmentList.add(ProductFragment.newInstance(2, 1));
        myPageAdapter = new MyPageAdapter(myFragmentList, getChildFragmentManager());
        mFunctionViewPager.setAdapter(myPageAdapter);
        mFunctionViewPager.setOffscreenPageLimit(2);
        mFunctionViewPager.setCurrentItem(0);

        for (int i = 0; i < 2; i++) {
            RadioButton tempButton = new RadioButton(getActivity());
            tempButton.setButtonDrawable(android.R.color.transparent);
            tempButton.setBackgroundResource(R.drawable.point_selector);
            tempButton.setId(i);
            tempButton.setGravity(Gravity.CENTER);

            int width = MyUtils.Dp2Px(getContext() , 10);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(width, width);
            layoutParams.setMargins(width , 0 , 0 , 0);
            rg.addView(tempButton , layoutParams);
        }
        //设置默认选中第一页
        rg.check(0);

        mFunctionViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton tempButton = (RadioButton) rg.findViewById(position);
                tempButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Subscribe
    public void onEventMainThread(RefreshInfoEntity event) {
        Log.i(TAG, "onEventMainThread: " + event.isRefresh());
        if (event.isRefresh()){
            mPageNumber = 1;
            getTuanGouBestShop();
        }

    }

    @Subscribe
    public void onEventMainThread(ScrollTopEntity event) {
        if (event.getPage() == 1)
            mNestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("mark", 1);
                startActivity(intent);
                break;
            case R.id.fragment_tuangou_error:

                showLoading();
                ProductFragment productFragment1 = (ProductFragment) myFragmentList.get(0);
                productFragment1.getProducts();
                ProductFragment productFragment2 = (ProductFragment) myFragmentList.get(1);
                productFragment2.getProducts();
                myPageAdapter.notifyDataSetChanged();

                mPageNumber = 1;
                getTuanGouBestShop();
                break;
            case R.id.iv_guanggao1:
                goWebActivity(tgGuangGaoEntity.getData().get(0).getShopId());
                break;
            case R.id.iv_guanggao2:
                goWebActivity(tgGuangGaoEntity.getData().get(1).getShopId());
                break;
            case R.id.iv_guanggao3:
                goWebActivity(tgGuangGaoEntity.getData().get(2).getShopId());
                break;
            case R.id.iv_guanggao4:
                goWebActivity(tgGuangGaoEntity.getData().get(3).getShopId());
                break;
            case R.id.iv_guanggao5:
                goWebActivity(tgGuangGaoEntity.getData().get(4).getShopId());
                break;
            default:
                break;
        }
    }


    private void getTuanGouBestShop() {

        RequestParams params = AsynClient.getRequestParams();
        if (LocationInfoManager.getLocation() != null) {
            params.put("userLo", LocationInfoManager.getLocation().getLongitude());
            params.put("userLa", LocationInfoManager.getLocation().getLatitude());
        } else {
            params.put("userLo", 0.0);
            params.put("userLa", 0.0);
        }
        params.put("mark", mMark);
        params.put("pageNumber", mPageNumber);
        AsynClient.post(MyHttpConfing.bestShop, getActivity(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();
                hideLoading();
                mErrorView.setVisibility(View.VISIBLE);
                mNestedScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG + "团购", rawJsonResponse);
                mErrorView.setVisibility(View.GONE);
                mNestedScrollView.setVisibility(View.VISIBLE);

                Gson gson = new Gson();
                bestShopEntity = gson.fromJson(rawJsonResponse, BestShopEntity.class);
                if (bestShopEntity.getCode() == 100) {
                    if (bestShopEntity.getData() != null){

                        if (mPageNumber == 1)
                            mRecommendShopListAdapter.setNewData(bestShopEntity.getData());
                        else
                            mRecommendShopListAdapter.addData(bestShopEntity.getData());
                    }else {
                        if (mPageNumber == 1)
                            mRecommendShopListAdapter.setNewData(bestShopEntity.getData());
                    }

                    if (bestShopEntity.getData() == null || bestShopEntity.getData().size() < 8){
                        mRefresh.setEnableLoadmore(false);
                    }else {
                        mRefresh.setEnableLoadmore(true);
                    }
                }

                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();
                hideLoading();
            }
        });

    }

    /**
     * 改变商家收藏状态
     */
    public void changeCollectionState(boolean state , int shopId , final int position){
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("colStatus", state);
        AsynClient.post(MyHttpConfing.collection, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                showMessage(getString(R.string.server_no_response));
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100){
                    mRecommendShopListAdapter.remove(position);
                }else {
                    showMessage(getString(R.string.server_no_response));
                }
            }
        });
    }


    private void getGuanggao() {
        RequestParams params = AsynClient.getRequestParams();
        AsynClient.post(MyHttpConfing.TgGuangGao, getActivity(), params, new GsonHttpResponseHandler() {

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , "***"+rawJsonResponse);
                Gson gson = new Gson();
                tgGuangGaoEntity = gson.fromJson(rawJsonResponse, TgGuangGaoEntity.class);

                if (tgGuangGaoEntity.getCode() == 100) {
                    initAdvertising(tgGuangGaoEntity.getData());
                }
            }
        });
    }

    public void initAdvertising(List<TgGuangGaoEntity.DataBean> data){
        ImageLoadManager.getInstance().setImage(data.get(0).getImgUrl() , mAdvertisingOne);
        ImageLoadManager.getInstance().setImage(data.get(1).getImgUrl() , mAdvertisingTwo);
        ImageLoadManager.getInstance().setImage(data.get(2).getImgUrl() , mAdvertisingThree);
        ImageLoadManager.getInstance().setImage(data.get(3).getImgUrl() , mAdvertisingFour);
        ImageLoadManager.getInstance().setImage(data.get(4).getImgUrl() , mAdvertisingFive);
    }
}
