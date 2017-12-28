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

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.MyReservationAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.MyReservationEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerGridItemDecoration;
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

public class MyReservationActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_my_reservation)
    RecyclerView rvMyReservation;
    @Bind(R.id.refresh_my_reservation)
    SmartRefreshLayout refreshMyReservation;

    private int pageNumber = 1;
    private MyReservationAdapter myReservationAdapter;
    private int shopId;
    private View errorView;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("我的预约");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId" , 0);
        errorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);
        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);

        showLoadingDialog();
        getData();

        refreshMyReservation.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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

        rvMyReservation.setLayoutManager(new LinearLayoutManager(this));
        myReservationAdapter = new MyReservationAdapter(R.layout.item_my_reservation , null);
        rvMyReservation.setAdapter(myReservationAdapter);

    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        params.put("pageNum" , pageNumber);
        AsynClient.post(MyHttpConfing.getAccountReserve, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideLoadingDialog();
                refreshMyReservation.finishRefresh();
                refreshMyReservation.finishLoadmore();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideLoadingDialog();
                refreshMyReservation.finishRefresh();
                refreshMyReservation.finishLoadmore();
                MyReservationEntity myReservationEntity = new Gson().fromJson(rawJsonResponse, MyReservationEntity.class);
                if (myReservationEntity.getCode() == 100){
                    List<MyReservationEntity.DataBean> data = myReservationEntity.getData();
                    if (pageNumber == 1){
                        if (data == null || data.size() == 0){
                            myReservationAdapter.setEmptyView(emptyView);
                        }else {
                            myReservationAdapter.setNewData(data);
                        }
                    }else {
                        if (data == null || data.size() == 0){
                            refreshMyReservation.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            refreshMyReservation.setEnableLoadmore(false);
                            myReservationAdapter.addData(data);
                        }else {
                            refreshMyReservation.setEnableLoadmore(true);
                            myReservationAdapter.addData(data);
                        }
                    }

                }else {
                    showMessage(myReservationEntity.getMessage());
                    myReservationAdapter.setEmptyView(errorView);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
