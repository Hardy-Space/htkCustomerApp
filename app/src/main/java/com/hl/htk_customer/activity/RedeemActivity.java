package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.hl.htk_customer.adapter.RedeemAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.NormalEntity;
import com.hl.htk_customer.entity.RedeemEntity;
import com.hl.htk_customer.model.MemberMineEvent;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public class RedeemActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_redeem)
    RecyclerView rvRedeem;
    @Bind(R.id.refresh_redeem)
    SmartRefreshLayout refreshRedeem;

    private int pageNumber = 1;
    private RedeemAdapter redeemAdapter;
    private int shopId;
    private View emptyView;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        ButterKnife.bind(this);
        initBar();
        init();
    }

    private void initBar() {
        title.setText("积分兑换");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId" , 0);
        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view , null);
        errorView = LayoutInflater.from(this).inflate(R.layout.error_view , null);

        showLoadingDialog();
        getData();

        refreshRedeem.setEnableLoadmoreWhenContentNotFull(true);
        refreshRedeem.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNumber ++ ;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber = 1;
                getData();
            }
        });

        redeemAdapter = new RedeemAdapter(R.layout.item_redeem , null);
        rvRedeem.setLayoutManager(new GridLayoutManager(this , 2));
        rvRedeem.setAdapter(redeemAdapter);

        redeemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                PromptDialog.builder(RedeemActivity.this).create("确认兑换？")
                        .setListener(new DialogOnClickListener() {
                            @Override
                            public void onPositive() {
                                convert(redeemAdapter.getData().get(position).getId());
                            }

                            @Override
                            public void onNegative() {

                            }
                        });
            }
        });
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        params.put("pageNum" , pageNumber);
        AsynClient.post(MyHttpConfing.getIntegralBuyData, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideLoadingDialog();
                refreshRedeem.finishLoadmore();
                refreshRedeem.finishRefresh();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideLoadingDialog();
                refreshRedeem.finishLoadmore();
                refreshRedeem.finishRefresh();
                RedeemEntity redeemEntity = new Gson().fromJson(rawJsonResponse, RedeemEntity.class);
                if (redeemEntity.getCode() == 100){
                    List<RedeemEntity.DataBean> data = redeemEntity.getData();
                    if (pageNumber == 1){
                        if (data == null || data.size() == 0){
                            redeemAdapter.setEmptyView(emptyView);
                            refreshRedeem.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            redeemAdapter.setNewData(data);
                            refreshRedeem.setEnableLoadmore(false);
                        }else {
                            redeemAdapter.setNewData(data);
                            refreshRedeem.setEnableLoadmore(true);
                        }
                    }else {
                        if (data == null || data.size() == 0){
                            refreshRedeem.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            refreshRedeem.setEnableLoadmore(false);
                            redeemAdapter.addData(data);
                        }else {
                            refreshRedeem.setEnableLoadmore(true);
                            redeemAdapter.addData(data);
                        }
                    }
                }else {
                    showMessage(redeemEntity.getMessage());
                    redeemAdapter.setEmptyView(errorView);
                }
            }
        });
    }

    private void convert(int id){
        showChangeDialog("兑换中");
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId );
        params.put("ticketId" , id);
        AsynClient.post(MyHttpConfing.redeemOperation, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " +rawJsonResponse);
                hideChangeDialog();
                NormalEntity entity = new Gson().fromJson(rawJsonResponse, NormalEntity.class);
                if (entity.getCode() == 100){
                    showMessage("兑换成功，请进入优惠券界面查看");
                    EventBus.getDefault().post(new MemberMineEvent());
                }else {
                    showMessage(entity.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
