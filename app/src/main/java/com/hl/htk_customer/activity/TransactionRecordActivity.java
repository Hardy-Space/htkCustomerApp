package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.TransactionRecordAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.TransactionRecordEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public class TransactionRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_transaction_record)
    RecyclerView rvTransactionRecord;
    @Bind(R.id.refresh_transaction_record)
    SmartRefreshLayout refreshTransactionRecord;

    private int pageNumber = 1;
    private TransactionRecordAdapter transactionRecordAdapter;
    private int shopId;
    private View emptyView;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_record);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("交易记录");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId" , 0);
        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);
        errorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);
        showLoadingDialog();
        getData();

        refreshTransactionRecord.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNumber++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber = 1;
                getData();
            }
        });

        rvTransactionRecord.setLayoutManager(new LinearLayoutManager(this));
        transactionRecordAdapter = new TransactionRecordAdapter(R.layout.item_transaction_record , null);
        rvTransactionRecord.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        rvTransactionRecord.setAdapter(transactionRecordAdapter);

        transactionRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TransactionRecordActivity.this, TransactionRecordDetailsActivity.class);
                TransactionRecordEntity.DataBean dataBean = transactionRecordAdapter.getData().get(position);
                intent.putExtra("amount" , dataBean.getPayAmount() );
                intent.putExtra("payMethod" , dataBean.getPayMethod() );
                intent.putExtra("createTime" , dataBean.getCreateTime() );
                intent.putExtra("orderNumber" , dataBean.getOrderNumber() );
                intent.putExtra("orderType" , dataBean.getOrderType() );
                startActivity(intent);
            }
        });
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        params.put("pageNum" , pageNumber);
        AsynClient.post(MyHttpConfing.getAccountTradingRecord, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideLoadingDialog();
                refreshTransactionRecord.finishRefresh();
                refreshTransactionRecord.finishLoadmore();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " +rawJsonResponse);
                hideLoadingDialog();
                refreshTransactionRecord.finishRefresh();
                refreshTransactionRecord.finishLoadmore();
                TransactionRecordEntity transactionRecordEntity = new Gson().fromJson(rawJsonResponse, TransactionRecordEntity.class);
                if (transactionRecordEntity.getCode() == 100){
                    List<TransactionRecordEntity.DataBean> data = transactionRecordEntity.getData();
                    if (pageNumber == 1){
                        if (data == null || data.size() == 0){
                            transactionRecordAdapter.setEmptyView(emptyView);
                            refreshTransactionRecord.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            transactionRecordAdapter.setNewData(data);
                            refreshTransactionRecord.setEnableLoadmore(false);
                        }else {
                            transactionRecordAdapter.setNewData(data);
                            refreshTransactionRecord.setEnableLoadmore(true);
                        }
                    }else {
                        if (data == null || data.size() == 0){
                            refreshTransactionRecord.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            transactionRecordAdapter.addData(data);
                            refreshTransactionRecord.setEnableLoadmore(false);
                        }else {
                            transactionRecordAdapter.addData(data);
                            refreshTransactionRecord.setEnableLoadmore(true);
                        }
                    }
                }else {
                    transactionRecordAdapter.setEmptyView(errorView);
                    showMessage(transactionRecordEntity.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
