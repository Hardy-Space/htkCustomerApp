package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.MineEntity;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.model.MemberMineEvent;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MineActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_mine_icon)
    SimpleDraweeView ivMineIcon;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.tv_mine_phone)
    TextView tvMinePhone;
    @Bind(R.id.tv_mine_coupon)
    TextView tvMineCoupon;
    @Bind(R.id.ll_mine_coupon)
    LinearLayout llMineCoupon;
    @Bind(R.id.tv_mine_integral)
    TextView tvMineIntegral;
    @Bind(R.id.ll_mine_integral)
    LinearLayout llMineIntegral;
    @Bind(R.id.tv_mine_reservation)
    TextView tvMineReservation;
    @Bind(R.id.tv_mine_transaction_record)
    TextView tvMineTransactionRecord;
    @Bind(R.id.tv_mine_redeem)
    TextView tvMineRedeem;
    @Bind(R.id.tv_mine_about)
    TextView tvMineAbout;
    @Bind(R.id.tv_mine_evaluation)
    TextView tvMineEvaluation;
    @Bind(R.id.cl_mine_head)
    ConstraintLayout clMineHead;
    private int shopId;
    private UserInfoManager userInfoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("我的");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId", 0);
        userInfoManager = new UserInfoManager(this);

        llMineCoupon.setOnClickListener(this);
        llMineIntegral.setOnClickListener(this);
        tvMineReservation.setOnClickListener(this);
        tvMineRedeem.setOnClickListener(this);
        tvMineTransactionRecord.setOnClickListener(this);
        tvMineAbout.setOnClickListener(this);
        tvMineEvaluation.setOnClickListener(this);
        clMineHead.setOnClickListener(this);

        if (userInfoManager.getISLOGIN()) {
            getData();
        } else {
//            tvMineName.setText("立即登录");
//            tvMinePhone.setText("登录后查看个人信息");
            tvMineCoupon.setText("0");
            tvMineIntegral.setText("0");
        }

    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId", shopId);
        AsynClient.post(MyHttpConfing.getMemberAccountMes, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {

            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                MineEntity mineEntity = new Gson().fromJson(rawJsonResponse, MineEntity.class);
                if (mineEntity.getCode() == 100) {
                    tvMineName.setText(mineEntity.getData().getNickName());
                    tvMinePhone.setText(mineEntity.getData().getPhone());
                    tvMineCoupon.setText(String.valueOf(mineEntity.getData().getTicketCount()));
                    tvMineIntegral.setText(String.valueOf(mineEntity.getData().getIntegralCount()));
                    ivMineIcon.setImageURI(mineEntity.getData().getAvatarUrl());
                } else {
                    showMessage(mineEntity.getMessage());
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(RefreshInfoEntity event) {
        Log.i(TAG, "onEventMainThread: " + event.isRefresh());
        if (event.isRefresh())
            getData();
    }

    @Subscribe
    public void onEventMainThread(MemberMineEvent event) {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.cl_mine_head:
//                if (!userInfoManager.getISLOGIN()){
//                    startActivity(new Intent(this, LoginActivity.class));
//                }
                break;
            case R.id.ll_mine_coupon:
                if (userInfoManager.getISLOGIN())
                    CouponActivity.launch(this , shopId);
                else startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.ll_mine_integral:
                if (userInfoManager.getISLOGIN()){
                    Intent intentReservation = new Intent(this, IntegralRecordActivity.class);
                    intentReservation.putExtra("shopId" , shopId);
                    startActivity(intentReservation);
                }
                else startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_mine_reservation:
                if (userInfoManager.getISLOGIN()){
                    Intent intentReservation = new Intent(this, MyReservationActivity.class);
                    intentReservation.putExtra("shopId" , shopId);
                    startActivity(intentReservation);
                }
                else startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_mine_transaction_record:
                if (userInfoManager.getISLOGIN()){
                    Intent intentRecord = new Intent(this, TransactionRecordActivity.class);
                    intentRecord.putExtra("shopId" , shopId);
                    startActivity(intentRecord);
                }
                else startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_mine_redeem:
                if (userInfoManager.getISLOGIN()){
                    Intent intentRedeem = new Intent(this, RedeemActivity.class);
                    intentRedeem.putExtra("shopId" , shopId);
                    startActivity(intentRedeem);
                }
                else startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_mine_about:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra("shopId", shopId);
                startActivity(intent);
                break;
            case R.id.tv_mine_evaluation:
                Intent intentEvaluation = new Intent(this, EvaluationActivity.class);
                intentEvaluation.putExtra("shopId", shopId);
                startActivity(intentEvaluation);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
