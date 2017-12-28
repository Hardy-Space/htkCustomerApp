package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.base_lib.popupwindow.CustomPopWindow;
import com.example.base_lib.utils.Utils;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.CollectionListAdapter;
import com.hl.htk_customer.adapter.CollectionListRecommendAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.PopupWindowStringList;
import com.hl.htk_customer.entity.ShopAndRecommendEntity;
import com.hl.htk_customer.entity.StyleEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.LocationInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/19.
 */

public class CollectionListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.collection_list_sort)
    TextView mSort;
    @Bind(R.id.collection_list_distance)
    TextView mDistance;
    @Bind(R.id.collection_list_filter)
    TextView mFilter;
    @Bind(R.id.collection_list_refresh)
    SmartRefreshLayout mRefresh;
    @Bind(R.id.collection_list_rv)
    RecyclerView mCollectionListView;
    @Bind(R.id.collection_tv_recommend)
    TextView mCollectionTvRecommend;
    @Bind(R.id.collection_recommend_list_rv)
    RecyclerView mCollectionListViewRecommend;

    private List<ShopAndRecommendEntity.DataBean.ShopListBean> mCollectionList;
    private CollectionListAdapter mCollectionListAdapter;
    private int mMark;
    private int mCategoryId;
    private int mChildId = 0;
    private int mDistanceType = 0;
    private String mTitleName;
    private int mPageNumber = 1;//初始页
    private CollectionListRecommendAdapter mCollectionListRecommendAdapter;

    private List<String> mSortData = new ArrayList<>();
    private List<String> mDistanceData = new ArrayList<>();
    private List<String> mFilterData = new ArrayList<>();

    private List<StyleEntity.DataBean> mSortDataAll;
    private View emptyView;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        ButterKnife.bind(this);

        init();
        initView();
    }

    private void init() {
        mMark = getIntent().getIntExtra("mark", -1);
        mCategoryId = getIntent().getIntExtra("categoryId", -1);
        mTitleName = getIntent().getStringExtra("name");

        setSupportActionBar(toolbar);

        mTitle.setText(mTitleName);

        mSort.setOnClickListener(this);
        mDistance.setOnClickListener(this);

        getListData();//获取分类列表数据
        String[] distance = getResources().getStringArray(R.array.filter_distance);
        for (int i = 0; i < distance.length; i++) {
            mDistanceData.add(distance[i]);
        }

    }

    private void initView() {

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_view , null);
        errorView = LayoutInflater.from(mContext).inflate(R.layout.error_view , null);

        mCollectionListView.setLayoutManager(new LinearLayoutManager(mContext));
        mCollectionListView.addItemDecoration(new DividerItemDecoration(mContext , DividerItemDecoration.VERTICAL_LIST));
        mCollectionListAdapter = new CollectionListAdapter(R.layout.item_recommend_shop , mCollectionList);
        mCollectionListView.setAdapter(mCollectionListAdapter);

        mCollectionListViewRecommend.setLayoutManager(new LinearLayoutManager(mContext));
        mCollectionListViewRecommend.addItemDecoration(new DividerItemDecoration(mContext , DividerItemDecoration.VERTICAL_LIST));
        mCollectionListRecommendAdapter = new CollectionListRecommendAdapter(R.layout.item_recommend_shop , null);
        mCollectionListViewRecommend.setAdapter(mCollectionListRecommendAdapter);

        //初次进入触发刷新
        mRefresh.autoRefresh();


        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNumber ++ ;
                getShopListData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageNumber = 1;
                getShopListData();
            }
        });

        mCollectionListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopAndRecommendEntity.DataBean.ShopListBean item = mCollectionListAdapter.getItem(position);
                if (mMark == 0) {
                    Intent intent = new Intent(mContext, WmShopDetailActivity.class);
                    intent.putExtra("shopId", item.getShopId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, TuanGouShopActivity.class);
                    intent.putExtra("shopId", item.getShopId());
                    startActivity(intent);
                }
            }
        });

        mCollectionListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                View viewByPosition = mCollectionListAdapter.getViewByPosition(mCollectionListView , position, R.id.iv_collection);
                final CustomPopWindow popWindow = new CustomPopWindow.CustomPopWindowBuilder(mContext)
                        .setLayoutId(R.layout.pop_text)
                        .create()
                        .showAtLocation(viewByPosition);
                popWindow.getContentView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeCollectionState(false , mCollectionListAdapter.getData().get(position).getShopId() , position);
                        popWindow.dismiss();
                    }
                });

                return true;
            }
        });

        mCollectionListRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopAndRecommendEntity.DataBean.RecommendShopListBean item = mCollectionListRecommendAdapter.getItem(position);
                goWebActivity(item.getShopId());
            }
        });

    }

    /**
     * 进入会员尊享平台
     * @param shopId
     */
    private void goWebActivity(int shopId) {
        Intent intent = new Intent(this, MemberPlatformActivity.class);
        intent.putExtra("shopId", shopId);
        startActivity(intent);
    }

    /**
     * 获取美食分类列表数据
     */
    private void getListData() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("categoryId", mCategoryId);//分类列表id
        params.put("mark", mMark); //0 外卖  1 团购
        AsynClient.post(MyHttpConfing.shopStyle, this, params, new GsonHttpResponseHandler() {

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {

            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                StyleEntity json = gson.fromJson(rawJsonResponse, StyleEntity.class);

                if (json.getCode() == 100) {

                    if (json.getData() == null) return;
                    mSortDataAll = json.getData();
                    for (StyleEntity.DataBean bean  : json.getData()) {
                        mSortData.add(bean.getCategoryName());
                    }

                }
            }
        });
    }

    /**
     * 获取商家列表
     */
    public void getShopListData(){
        Log.i(TAG, "getShopListData: " + mPageNumber);
        RequestParams params = AsynClient.getRequestParams();
        params.put("categoryId", mCategoryId);
        params.put("mark", mMark); //0 外卖  1 团购
        params.put("pageNumber", mPageNumber);
        params.put("childCId", mChildId);
        params.put("distanceType", mDistanceType);
        AMapLocation location = LocationInfoManager.getLocation();
        if (location == null) {
            params.put("userLo", 0);
            params.put("userLa", 0);
        }else {
            params.put("userLo", location.getLongitude());
            params.put("userLa", location.getLatitude());
        }



        AsynClient.post(MyHttpConfing.shopListById, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                ShopAndRecommendEntity shopData = gson.fromJson(rawJsonResponse, ShopAndRecommendEntity.class);

                if (shopData.getCode() == 100) {

                    if (shopData.getData() == null){
                        mCollectionTvRecommend.setVisibility(View.GONE);
                        mCollectionListViewRecommend.setVisibility(View.GONE);
                        mCollectionListAdapter.setNewData(null);
                        mCollectionListAdapter.setEmptyView(emptyView);
                    }else {
                        //如果收藏商家为空，收藏列表隐藏，否则显示
                        if (shopData.getData().getShopList() == null || shopData.getData().getShopList().size() == 0){
                            mCollectionListView.setVisibility(View.GONE);
                        }else {
                            if (mPageNumber == 1)
                                mCollectionListAdapter.setNewData(shopData.getData().getShopList());
                            else
                                mCollectionListAdapter.addData(shopData.getData().getShopList());

                            mCollectionListView.setVisibility(View.VISIBLE);
                        }

                        //如果推荐商家为空，推荐列表隐藏，否则显示
                        if (shopData.getData().getRecommendShopList() == null || shopData.getData().getRecommendShopList().size() == 0){
                            mCollectionListViewRecommend.setVisibility(View.GONE);
                            mCollectionTvRecommend.setVisibility(View.GONE);
                        }else {
                            if (mPageNumber == 1)
                                mCollectionListRecommendAdapter.setNewData(shopData.getData().getRecommendShopList());
                            else
                                mCollectionListRecommendAdapter.addData(shopData.getData().getRecommendShopList());

                            mCollectionListViewRecommend.setVisibility(View.VISIBLE);
                            mCollectionTvRecommend.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    mCollectionListAdapter.setEmptyView(errorView);
                }
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();

            }
        });
    }

    /**
     * 改变商家收藏状态
     */
    public void changeCollectionState(boolean state , int shopId , final int position){
        showLoadingDialog();
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("colStatus", state);
        AsynClient.post(MyHttpConfing.collection, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                showMessage(getString(R.string.server_no_response));
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100){
                    mCollectionListAdapter.remove(position);
                }else {
                    showMessage(getString(R.string.server_no_response));
                }
                hideLoadingDialog();
            }
        });
    }

    @OnClick(R.id.img_back)
    void OnClick(){
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.collection_list_sort:
                showPopupWindow(mSortData , mSort , 0);
                break;
            case R.id.collection_list_distance:
                showPopupWindow(mDistanceData , mDistance , 1);
                break;
            case R.id.collection_list_filter:
                break;
        }

    }

    PopupWindowStringList pop;

    /**
     * 显示下拉列表
     * @param list 列表数据
     * @param tv 点击后改变的具体view
     * @param type 0：分类   1：距离
     */
    public void showPopupWindow(final List<String> list , final TextView tv , final int type){
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.pop_list, null);
        pop = new PopupWindowStringList(inflate , Utils.getScreenWidth(mContext) ,
                WindowManager.LayoutParams.WRAP_CONTENT  , list);
        pop.showAsDropDown(mSort);

        pop.setOnItemClickListener(new PopupWindowStringList.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tv.setText(list.get(position));
                pop.dismiss();

                if (position != 0){
                    if (type == 0){
                        mChildId = mSortDataAll.get(position).getId();
                        mRefresh.autoRefresh();
                    }else if (type == 1){
                        mDistanceType = position;
                        mRefresh.autoRefresh();
                    }
                }else {
                    if (type == 0){
                        mChildId = 0;
                        mRefresh.autoRefresh();
                    }else if (type == 1){
                        mDistanceType = 0;
                        mRefresh.autoRefresh();
                    }
                }
            }
        });

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                pop = null;
            }
        });
    }

}
