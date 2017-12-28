package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.CollectionAdapter;
import com.hl.htk_customer.adapter.MessageAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.MessageEntity;
import com.hl.htk_customer.entity.MyCollectionEntity;
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
 * 通知中心
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener {

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
    private MessageAdapter mAdapter;
    private View emptyView;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_collection);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initWidget() {
        llReturn.setOnClickListener(this);
        tvTitle.setText(getResources().getText(R.string.message));

        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);
        errorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MessageAdapter(R.layout.item_message , null);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageEntity.DataBean item = (MessageEntity.DataBean) adapter.getItem(position);
                if (item == null) return;
                //  0 外卖  1团购
                Intent intent;
                if (item.getMark() == 0) {
                    intent = new Intent(MessageActivity.this, OrderDetailActivity.class);
                } else {
                    intent = new Intent(MessageActivity.this, TgOrderDetailActivity.class);
                }
                intent.putExtra("orderId", item.getOrderId());
                intent.putExtra("shopId", item.getShopId());
                startActivity(intent);
            }
        });

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNumber ++;
                getList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageNumber = 1;
                getList();
            }
        });

        showLoadingDialog();
        getList();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            default:
                break;
        }
    }

    private void getList() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", mPageNumber);
        AsynClient.post(MyHttpConfing.message, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
                mRefresh.finishLoadmore();
                mRefresh.finishRefresh();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                Gson gson = new Gson();
                MessageEntity messageEntity = gson.fromJson(rawJsonResponse, MessageEntity.class);

                if (messageEntity.getCode() == 100) {

                    if (messageEntity.getData() == null || messageEntity.getData().size() == 0) {
                        mAdapter.setEmptyView(emptyView);
                    }else {
                        if (mPageNumber == 1)
                            mAdapter.setNewData(messageEntity.getData());
                        else mAdapter.addData(messageEntity.getData());
                    }

                    if (messageEntity.getData() == null || messageEntity.getData().size() < 8){
                        mRefresh.setEnableLoadmore(false);
                    }else {
                        mRefresh.setEnableLoadmore(true);
                    }
                }else {
                    mAdapter.setEmptyView(errorView);
                }

                hideLoadingDialog();
                mRefresh.finishLoadmore();
                mRefresh.finishRefresh();

            }
        });

    }

}
