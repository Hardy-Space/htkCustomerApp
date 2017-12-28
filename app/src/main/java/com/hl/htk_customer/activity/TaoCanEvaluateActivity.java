package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.EvluateAdapter;
import com.hl.htk_customer.adapter.TaoCanEvaluateAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.TaoCanEvaluateEntity;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/1.
 */

public class TaoCanEvaluateActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_mark)
    TextView tvMark;
    @Bind(R.id.tv_evaluate_num)
    TextView tvEvaluateNum;
    @Bind(R.id.listView)
    MyListView listView;
    @Bind(R.id.scrollView)
    SmartRefreshLayout scrollView;

    private int page = 1;
    private int packageId = -1;
    private TaoCanEvaluateAdapter evluateAdapter;
    private TaoCanEvaluateEntity wmEvaluateEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_taocan_evaluate);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }


    private void initWidget() {

        packageId = getIntent().getIntExtra("packageId", -1);
        tvTitle.setText(getResources().getText(R.string.account_evaluate));
        llReturn.setOnClickListener(this);
        evluateAdapter = new TaoCanEvaluateAdapter(this);
        listView.setAdapter(evluateAdapter);

        showLoadingDialog();
        getEvaluate(page);

        scrollView.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getEvaluate(page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getEvaluate(page);
            }
        });


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


    private void getEvaluate(final int page) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("packageId", packageId);
        AsynClient.post(MyHttpConfing.taocanEvaluate, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                scrollView.finishLoadmore();
                scrollView.finishRefresh();
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);

                Gson gson = new Gson();
                wmEvaluateEntity = gson.fromJson(rawJsonResponse, TaoCanEvaluateEntity.class);
                if (wmEvaluateEntity.getCode() == 100) {
                    if (page == 1) {
                        tvEvaluateNum.setText("(" + wmEvaluateEntity.getData().size() + ")");
                         evluateAdapter.setData(wmEvaluateEntity.getData());
                    } else {
                          evluateAdapter.addData(wmEvaluateEntity.getData());
                    }
                }

                scrollView.finishLoadmore();
                scrollView.finishRefresh();
                hideLoadingDialog();
            }
        });

    }


}
