package com.hl.htk_customer.activity;

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
import com.hl.htk_customer.adapter.IntegralRecordAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.IntegralRecordEntity;
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
 * Created by Administrator on 2017/11/3.
 */

public class IntegralRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_integral_record)
    RecyclerView rvIntegralRecord;
    @Bind(R.id.refresh_integral_record)
    SmartRefreshLayout refreshIntegralRecord;

    private int pageNumber = 1;
    private IntegralRecordAdapter integralRecordAdapter;
    private int shopId;
    private View errorView;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_record);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("积分消费记录");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId" , 0);
        errorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);
        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);

        showLoadingDialog();
        getData();

        refreshIntegralRecord.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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

        integralRecordAdapter = new IntegralRecordAdapter(R.layout.item_integral_record , null);
        rvIntegralRecord.setLayoutManager(new LinearLayoutManager(this));
        rvIntegralRecord.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        rvIntegralRecord.setAdapter(integralRecordAdapter);

    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        params.put("pageNum" , pageNumber);
        AsynClient.post(MyHttpConfing.getAccountIntegralRecord, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideLoadingDialog();
                refreshIntegralRecord.finishRefresh();
                refreshIntegralRecord.finishLoadmore();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideLoadingDialog();
                refreshIntegralRecord.finishRefresh();
                refreshIntegralRecord.finishLoadmore();
                IntegralRecordEntity integralRecordEntity = new Gson().fromJson(rawJsonResponse, IntegralRecordEntity.class);
                if (integralRecordEntity.getCode() == 100){
                    List<IntegralRecordEntity.DataBean> data = integralRecordEntity.getData();
                    if (pageNumber == 1){
                        if (data == null || data.size() == 0){
                            integralRecordAdapter.setEmptyView(emptyView);
                            refreshIntegralRecord.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            integralRecordAdapter.setNewData(data);
                            refreshIntegralRecord.setEnableLoadmore(false);
                        }else {
                            integralRecordAdapter.setNewData(data);
                            refreshIntegralRecord.setEnableLoadmore(true);
                        }
                    }else {
                        if (data == null || data.size() == 0){
                            refreshIntegralRecord.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            refreshIntegralRecord.setEnableLoadmore(false);
                            integralRecordAdapter.addData(data);
                        }else {
                            refreshIntegralRecord.setEnableLoadmore(true);
                            integralRecordAdapter.addData(data);
                        }
                    }
                }else {
                    showMessage(integralRecordEntity.getMessage());
                    integralRecordAdapter.setEmptyView(errorView);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
