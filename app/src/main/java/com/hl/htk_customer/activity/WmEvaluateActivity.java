package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.EvluateAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/28.
 */

public class WmEvaluateActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.evaluate_rv)
    RecyclerView mEvaluateRv;
    @Bind(R.id.evaluate_refresh)
    SmartRefreshLayout mRefresh;

    private int mark = 0;
    private int shopId = -1;
    private int mPageNumber = 1;
    private EvluateAdapter evluateAdapter;
    private WmEvaluateEntity wmEvaluateEntity;
    private View mEmptyView;
    private View mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_wm_evaluate);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        mark = getIntent().getIntExtra("mark", 0);
        shopId = getIntent().getIntExtra("shopId", -1);
        tvTitle.setText(getResources().getText(R.string.account_evaluate));
        llReturn.setOnClickListener(this);

        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);
        mErrorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);

        mEvaluateRv.setLayoutManager(new LinearLayoutManager(this));
        mEvaluateRv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        evluateAdapter = new EvluateAdapter(R.layout.item_evaluate , null);
        mEvaluateRv.setAdapter(evluateAdapter);

        showLoadingDialog();
        getEvlaute();

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNumber ++;
                getEvlaute();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageNumber = 1;
                getEvlaute();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
        }
    }

    private void getEvlaute() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("pageNumber", mPageNumber);
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.shopEvaluate, this, params, new GsonHttpResponseHandler() {
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

                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                wmEvaluateEntity = gson.fromJson(rawJsonResponse, WmEvaluateEntity.class);
                if (wmEvaluateEntity.getCode() == 100) {
                    if (wmEvaluateEntity.getData() == null || wmEvaluateEntity.getData().getList() == null || wmEvaluateEntity.getData().getList().size() == 0 ){
                        evluateAdapter.setEmptyView(mEmptyView);
                    }else {
                        if (mPageNumber == 1)
                            evluateAdapter.setNewData(wmEvaluateEntity.getData().getList());
                        else evluateAdapter.addData(wmEvaluateEntity.getData().getList());
                    }
                }else {
                    evluateAdapter.setEmptyView(mErrorView);
                }

                hideLoadingDialog();
                mRefresh.finishRefresh();
                mRefresh.finishLoadmore();
            }
        });

    }





}
