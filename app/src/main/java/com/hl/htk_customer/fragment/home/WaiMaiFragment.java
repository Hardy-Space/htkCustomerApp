package com.hl.htk_customer.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.hl.htk_customer.activity.ChooseAddressActivity;
import com.hl.htk_customer.activity.LoginActivity;
import com.hl.htk_customer.activity.MemberPlatformActivity;
import com.hl.htk_customer.activity.SearchActivity;
import com.hl.htk_customer.activity.diancan.DianCanActivity;
import com.hl.htk_customer.adapter.MyPageAdapter;
import com.hl.htk_customer.adapter.RecommendShopListAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.BestShopEntity;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.entity.ScanEntity;
import com.hl.htk_customer.entity.ScanResultEntity;
import com.hl.htk_customer.entity.TgGuangGaoEntity;
import com.hl.htk_customer.fragment.waimai.ProductFragment;
import com.hl.htk_customer.hldc.main.DCMainActivity;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.EventAddressEntity;
import com.hl.htk_customer.model.LocationInfoManager;
import com.hl.htk_customer.model.ScrollTopEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.ImageLoadManager;
import com.hl.htk_customer.utils.LocationSingle;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 外卖 fragment
 */

public class WaiMaiFragment extends BaseFragment implements View.OnClickListener  {

    private final int REQUEST_CODE = 505;
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private Toolbar mToolBar;
    private ImageView ivErweima;
    private TextView tvLocation;
    private RelativeLayout rlSearch;
    private ViewPager vp;
    private RadioGroup rg;

    private SmartRefreshLayout mRefresh;
    private RecyclerView mRecommendShopListView;
    private List<BestShopEntity.DataBean> mRecommendShopList;
    private FrameLayout errorView;
    private NestedScrollView mNestedScrollView;
    private ImageView mIvGGOne;
    private ImageView mIvGGTwo;
    private ImageView mIvGGThree;
    private ImageView mIvGGFour;
    private ImageView mIvGGFive;


    List<BaseFragment> myFragmentList;
    MyPageAdapter myPageAdapter;

    private int mPageNumber = 1;
    BestShopEntity bestShopEntity;

    private LocationSingle mLocationSingle;
    private UserInfoManager mUserInfoManager;
    private RecommendShopListAdapter mRecommendShopListAdapter;

    private TgGuangGaoEntity wmGGEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_wai_mai, null);
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
    public void lazyInitData() {

        if (isFirst && isPrepared && isVisible) {
            isFirst = false;
            EventBus.getDefault().register(this);
            initView();
            initWidget();
        }

    }

    private void initView() {
        mToolBar = view.findViewById(R.id.toolbar);
        ivErweima = view.findViewById(R.id.iv_erweima);
        tvLocation = view.findViewById(R.id.tv_location);
        rlSearch = view.findViewById(R.id.rl_search);
        vp = view.findViewById(R.id.vp);
        rg = view.findViewById(R.id.rg);
        mRefresh = view.findViewById(R.id.fragment_wai_mai_refresh);
        mRecommendShopListView = view.findViewById(R.id.fragment_wai_mai_recyclerView);
        errorView = view.findViewById(R.id.fragment_wai_mai_error);
        mNestedScrollView = view.findViewById(R.id.fragment_wai_mai_scroll);
        mIvGGOne = view.findViewById(R.id.iv_guanggao1);
        mIvGGTwo = view.findViewById(R.id.iv_guanggao2);
        mIvGGThree = view.findViewById(R.id.iv_guanggao3);
        mIvGGFour = view.findViewById(R.id.iv_guanggao4);
        mIvGGFive = view.findViewById(R.id.iv_guanggao5);
    }

    private void initWidget() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolBar);

        mUserInfoManager = new UserInfoManager(getContext());
        ivErweima.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        rlSearch.setOnClickListener(this);
        errorView.setOnClickListener(this);
        mIvGGOne.setOnClickListener(this);
        mIvGGTwo.setOnClickListener(this);
        mIvGGThree.setOnClickListener(this);
        mIvGGFour.setOnClickListener(this);
        mIvGGFive.setOnClickListener(this);

        initViewPager();

        mRecommendShopListView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecommendShopListView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mRecommendShopListAdapter = new RecommendShopListAdapter(R.layout.item_recommend_shop, null);
        mRecommendShopListView.setAdapter(mRecommendShopListAdapter);

        mRecommendShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BestShopEntity.DataBean item = mRecommendShopListAdapter.getItem(position);
                goWebActivity(item.getShopId());

            }
        });

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNumber++;
                getBestShop();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageNumber = 1;
                getBestShop();
            }
        });

        showLoading();

        //定位
        mLocationSingle = new LocationSingle(tvLocation, getContext());

        getBestShop();
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
        myFragmentList.add(ProductFragment.newInstance(1, 0));
        myFragmentList.add(ProductFragment.newInstance(2, 0));
        myPageAdapter = new MyPageAdapter(myFragmentList, getChildFragmentManager());
        vp.setAdapter(myPageAdapter);
        vp.setOffscreenPageLimit(2);
        vp.setCurrentItem(0);

        for (int i = 0; i < 2; i++) {
            RadioButton tempButton = new RadioButton(getActivity());
            tempButton.setButtonDrawable(android.R.color.transparent);
            tempButton.setBackgroundResource(R.drawable.point_selector);
            tempButton.setId(i);
            tempButton.setGravity(Gravity.CENTER);

            int width = MyUtils.Dp2Px(getContext(), 10);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(width, width);
            layoutParams.setMargins(width, 0, 0, 0);
            rg.addView(tempButton, layoutParams);
        }
        //设置默认选中第一页
        rg.check(0);


        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_erweima:
                //二维码
                Intent scanIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(scanIntent, REQUEST_CODE);
                break;
            case R.id.tv_location:
                //位置
                startActivity(new Intent(getActivity(), ChooseAddressActivity.class));
                break;
            case R.id.rl_search:
                //搜索
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("mark", 0);
                startActivity(intent);
                break;
            case R.id.fragment_wai_mai_error:
                //重新加载分类列表和推荐商家
                showLoading();
                mPageNumber = 1;
                getBestShop();
                getGuanggao();
                mLocationSingle.singleLocation();

                ProductFragment productFragment1 = (ProductFragment) myFragmentList.get(0);
                productFragment1.getProducts();
                ProductFragment productFragment2 = (ProductFragment) myFragmentList.get(1);
                productFragment2.getProducts();
                myPageAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_guanggao1:
                goWebActivity(wmGGEntity.getData().get(0).getShopId());
                break;
            case R.id.iv_guanggao2:
                goWebActivity(wmGGEntity.getData().get(1).getShopId());
                break;
            case R.id.iv_guanggao3:
                goWebActivity(wmGGEntity.getData().get(2).getShopId());
                break;
            case R.id.iv_guanggao4:
                goWebActivity(wmGGEntity.getData().get(3).getShopId());
                break;
            case R.id.iv_guanggao5:
                goWebActivity(wmGGEntity.getData().get(4).getShopId());
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理二维码扫描结果
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Log.i(TAG, "onActivityResult: " + result);
                    ScanResultEntity scanResultEntity = new Gson().fromJson(result, ScanResultEntity.class);
                    Log.i(TAG, "onActivityResult: =scanResultEntity=>" + scanResultEntity.getType());
                    switch (scanResultEntity.getType()){
                        case 0://收藏
                            if (!mUserInfoManager.getISLOGIN()){
                                startActivity(new Intent(getContext() , LoginActivity.class));
                            }else {
                                analysis(scanResultEntity.getShopKey() , 0);
                            }
                            break;
                        case 1://进入自助点餐

                            analysis(scanResultEntity.getShopKey() , 1);
                            break;
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    showMessage("解析二维码失败");
                }
            }
        }
    }

    private void analysis(final String result , final int type) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("qrKey", result);
        AsynClient.post(MyHttpConfing.getshopId, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                Log.i(TAG , "parseResponse=>"+rawJsonData);
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                Log.i(TAG , "onFailure=>"+rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                ScanEntity scanEntity = gson.fromJson(rawJsonResponse, ScanEntity.class);
                if (scanEntity.getCode() == 100) {
                    MyApplication.shopId = scanEntity.getData().getShopId();
                    if (type == 0){
                        changeCollectionState(true , scanEntity.getData().getShopId());
                    }else if (type == 1){
                        // TODO: 2017/11/8 进入自助点餐界面
                        PreferencesUtils.putInt(getContext(),"shopId",scanEntity.getData().getShopId());
                        Intent mIntent = new Intent(getContext(),DCMainActivity.class);
                        mIntent.putExtra("jiacai","diancan");
                        startActivity(mIntent);
                    }
                }
            }
        });

    }

    @Subscribe
    public void onEventMainThread(RefreshInfoEntity event) {
        Log.i(TAG, "onEventMainThread: " + event.isRefresh());
        if (event.isRefresh()){
            mPageNumber = 1;
            getBestShop();
        }

    }

    @Subscribe
    public void onEventMainThread(EventAddressEntity event) {
        String address = event.getAddress();
        if (!TextUtils.isEmpty(address)) {
            tvLocation.setText(address);
        }
    }

    @Subscribe
    public void onEventMainThread(ScrollTopEntity event) {
        if (event.getPage() == 0)
            mNestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationSingle != null)
            mLocationSingle.destroySingleLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mLocationSingle != null)
            mLocationSingle.destroySingleLocation();
    }


    /**
     * 获取推荐商家
     */
    private void getBestShop() {
        RequestParams params = AsynClient.getRequestParams();
        if (LocationInfoManager.getLocation() != null) {
            params.put("userLo", LocationInfoManager.getLocation().getLongitude());
            params.put("userLa", LocationInfoManager.getLocation().getLatitude());
        } else {
            params.put("userLo", 0.0);
            params.put("userLa", 0.0);
        }
        params.put("mark", 0);
        params.put("pageNumber", mPageNumber);
        AsynClient.post(MyHttpConfing.bestShop, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + "code" + statusCode + "message" + rawJsonData);
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();
                hideLoading();
                errorView.setVisibility(View.VISIBLE);
                mNestedScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                errorView.setVisibility(View.GONE);
                mNestedScrollView.setVisibility(View.VISIBLE);

                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                bestShopEntity = gson.fromJson(rawJsonResponse, BestShopEntity.class);
                if (bestShopEntity.getCode() == 100) {
                    if (bestShopEntity.getData() != null) {

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
     * 获取广告
     */
    private void getGuanggao() {
        RequestParams params = AsynClient.getRequestParams();
        AsynClient.get(MyHttpConfing.getWmGuanggao, mContext, params, new GsonHttpResponseHandler() {

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + "code" + statusCode + "message" + rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , "***"+rawJsonResponse);
                Gson gson = new Gson();
                wmGGEntity = gson.fromJson(rawJsonResponse, TgGuangGaoEntity.class);

                if (wmGGEntity.getCode() == 100) {
                    initAdvertising(wmGGEntity.getData());
                }
            }
        });
    }

    public void initAdvertising(List<TgGuangGaoEntity.DataBean> data){
        ImageLoadManager.getInstance().setImage(data.get(0).getImgUrl() , mIvGGOne);
        ImageLoadManager.getInstance().setImage(data.get(1).getImgUrl() , mIvGGTwo);
        ImageLoadManager.getInstance().setImage(data.get(2).getImgUrl() , mIvGGThree);
        ImageLoadManager.getInstance().setImage(data.get(3).getImgUrl() , mIvGGFour);
        ImageLoadManager.getInstance().setImage(data.get(4).getImgUrl() , mIvGGFive);
    }

    /**
     * 改变商家收藏状态
     */
    public void changeCollectionState(boolean state , int shopId){
        showChangeDialog("收藏中");
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("colStatus", state);
        AsynClient.post(MyHttpConfing.collection, getContext(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                showMessage(getString(R.string.server_no_response));
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100){
                    // TODO: 2017/11/8 进入收藏的商家
                }
                hideChangeDialog();
            }
        });
    }

}
