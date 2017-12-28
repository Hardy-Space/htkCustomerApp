package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.base_lib.popupwindow.CustomPopWindow;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.CollectionAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.MyCollectionEntity;
import com.hl.htk_customer.model.CommonMsg;
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

/**
 * Created by Administrator on 2017/6/20.
 * 我的收藏
 */

public class MyCollectionActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.collection_refresh)
    SmartRefreshLayout mRefresh;
    @Bind(R.id.collection_rv)
    RecyclerView mRecyclerView;

    private int mPageNumber = 1;
    private MyCollectionEntity myCollectionEntity;
    private CollectionAdapter mAdapter;
    private View mEmptyView;
    private View mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_collection);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();

    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.my_collection));
        llReturn.setOnClickListener(this);

        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);
        mErrorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CollectionAdapter(R.layout.item_recommend_shop , null);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyCollectionEntity.DataBean item = (MyCollectionEntity.DataBean) adapter.getItem(position);
                Intent intent;
                //mark   0 外卖  1团购
                switch (item.getMark()) {
                    case 0:
                        intent = new Intent(MyCollectionActivity.this, WmShopDetailActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MyCollectionActivity.this, TuanGouShopActivity.class);
                        break;
                    default:
                        intent = new Intent();
                        break;
                }
                intent.putExtra("shopId", item.getShopId());
                startActivity(intent);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
                View viewByPosition = mAdapter.getViewByPosition(mRecyclerView , position, R.id.iv_collection);
                final CustomPopWindow popWindow = new CustomPopWindow.CustomPopWindowBuilder(mContext)
                        .setLayoutId(R.layout.pop_text)
                        .create()
                        .showAtLocation(viewByPosition);
                popWindow.getContentView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeCollectionState(false , mAdapter.getData().get(position).getShopId() , position);
                        popWindow.dismiss();
                    }
                });

                return true;
            }
        });

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNumber++;
                getMyCollection();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageNumber = 1;
                getMyCollection();
            }
        });

        showLoadingDialog();
        getMyCollection();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
        }
    }

    private void getMyCollection() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", mPageNumber);
        AsynClient.post(MyHttpConfing.myCollection, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                Gson gson = new Gson();
                myCollectionEntity = gson.fromJson(rawJsonResponse, MyCollectionEntity.class);

                if (myCollectionEntity.getCode() == 100) {

                    if (myCollectionEntity.getData() == null || myCollectionEntity.getData().size() == 0) {
                        mAdapter.setEmptyView(mEmptyView);
                    } else {
                        if (mPageNumber == 1){
                            mAdapter.setNewData(myCollectionEntity.getData());
                        }else {
                            mAdapter.addData(myCollectionEntity.getData());
                        }
                    }

                }else {
                    mAdapter.setEmptyView(mErrorView);
                }

                hideLoadingDialog();
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();

            }
        });

    }

    /**
     * 改变商家收藏状态
     */
    public void changeCollectionState(boolean state , int shopId , final int position){
        showChangeDialog("取消收藏");
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
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100){
                    mAdapter.remove(position);
                }else {
                    showMessage(getString(R.string.server_no_response));
                }

                hideChangeDialog();
            }
        });
    }
}
