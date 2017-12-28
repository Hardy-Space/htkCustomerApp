package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.amap.api.maps2d.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.TaoCanDetailsAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.AlPayEntity;
import com.hl.htk_customer.entity.SuccessEntity;
import com.hl.htk_customer.entity.TaoCanDetailEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.ImageLoadManager;
import com.hl.htk_customer.utils.LocationUtils;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyRatingBar;
import com.hl.htk_customer.wxapi.PayResult;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * 团购套餐详情
 */

public class TgTaoCanDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_mark2)
    TextView tvMark2;
    @Bind(R.id.tv_pay_price)
    TextView tvPayPrice;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.rl_pay)
    RelativeLayout rlPay;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.ratingBar)
    MyRatingBar ratingBar;
    @Bind(R.id.tv_pingfen)
    TextView tvPingfen;
    @Bind(R.id.tv_evaluate_num)
    TextView tvEvaluateNum;
    @Bind(R.id.rl_evaluate)
    RelativeLayout rlEvaluate;
    @Bind(R.id.line3)
    View line3;
    @Bind(R.id.tv_shop_info)
    TextView tvShopInfo;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.iv_call)
    ImageView ivCall;
    @Bind(R.id.rl_shop_info)
    RelativeLayout rlShopInfo;
    @Bind(R.id.line4)
    View line4;
    @Bind(R.id.tv_mark_taocan)
    TextView tvMarkTaocan;
    @Bind(R.id.order_details)
    RecyclerView rvOrderDetails;
    @Bind(R.id.tv_rule2)
    TextView tvRule2;
    @Bind(R.id.tv_mark_rule)
    TextView tvMarkRule;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.bannerImg)
    SimpleDraweeView bannerImg;
    @Bind(R.id.tv_use_time_round)
    TextView tvUseTime;

    private int id = -1;
    private int shopId = -1;
    private String phoneNumber = "";
    private double price = 0.0;
    private String orderInfo = "";
    private String orderId = "";
    private String orderBody = "";
    private static final int SDK_PAY_FLAG = 1;
    private int orderIds = -1;
    private TaoCanDetailsAdapter detailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_taocan_detail);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initWidget();
    }

    private void initWidget() {

        id = getIntent().getIntExtra("id", -1);
        shopId = getIntent().getIntExtra("shopId", -1);

        tvTitle.setText(getResources().getText(R.string.tuangou_detail));
        llReturn.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        rlEvaluate.setOnClickListener(this);
        tvShopInfo.setOnClickListener(this);

        rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
        rvOrderDetails.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST , R.drawable.shape_line));
        detailsAdapter = new TaoCanDetailsAdapter(R.layout.item_taocan_details , null);
        rvOrderDetails.setAdapter(detailsAdapter);

        showLoadingDialog();
        getTaoCanDetail();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.iv_call:
                if (TextUtils.isEmpty(phoneNumber)) return;
                MyUtils.call(TgTaoCanDetailActivity.this, phoneNumber);
                break;
            case R.id.tv_pay:
                //支付
                if (price == 0.0) return;
                if (!new UserInfoManager(mContext).getISLOGIN()) {
                    showMessage("请先登录");
                    return;
                }

                Intent intent1 = new Intent(TgTaoCanDetailActivity.this, ConfirmTgOrderActivity.class);
                intent1.putExtra("packageId", id);
                intent1.putExtra("shopId", shopId);
                startActivity(intent1);

                break;
            case R.id.rl_evaluate:
                Intent intent = new Intent(TgTaoCanDetailActivity.this, TaoCanEvaluateActivity.class);
                intent.putExtra("packageId", id);
                startActivity(intent);
                break;
            case R.id.tv_shop_info:
                finish();
                break;

        }
    }

    private void getTaoCanDetail() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("packageId", id);
        AsynClient.post(MyHttpConfing.TuanGouTaoCanDetail, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                TaoCanDetailEntity taoCanDetailEntity = gson.fromJson(rawJsonResponse, TaoCanDetailEntity.class);

                if (taoCanDetailEntity.getCode() == 100) {

                    initViewDatas(taoCanDetailEntity);
                }
                hideLoadingDialog();

            }
        });

    }


    private void initViewDatas(TaoCanDetailEntity taoCanDetailEntity) {

        if (taoCanDetailEntity.getData() == null) return;
        TaoCanDetailEntity.DataBean data = taoCanDetailEntity.getData();
        TaoCanDetailEntity.DataBean.BuyPackageBean buyPackage = data.getBuyPackage();
        price = buyPackage.getPrice();
        phoneNumber = data.getPhone();

//        bannerImg.setImageURI(buyPackage.getImgUrl());
        ImageLoadManager.getInstance().setImage(buyPackage.getImgUrl() , bannerImg);

        tvMark2.setText(buyPackage.getPackageName());  //店铺名
        tvPayPrice.setText("￥" + buyPackage.getPrice());
        tvPrice.setText("门市价： " + buyPackage.getRetailPrice());
        ratingBar.setCountSelected((int) buyPackage.getScore());
        tvPingfen.setText(buyPackage.getScore() + "分");
        if (data.getCommentCount() == null){
            tvEvaluateNum.setText("0人评价");
        }else {
            tvEvaluateNum.setText(data.getCommentCount() + "人评价");
        }

        tvShopName.setText(data.getShopName());
        tvAddress.setText(data.getLocationAddress());

        detailsAdapter.setNewData(data.getBuyPackageContentList());

        tvRule2.setText(buyPackage.getUseRules());
        tvUseTime.setText(buyPackage.getUsageTime());

        float distance = LocationUtils.getDistance(data.getLatitude(), data.getLongitude());
        if (distance == 0){
            tvDistance.setText("未知");
        }else {
            if (distance >= 1000)
                tvDistance.setText(String.format(getString(R.string.join_distance_km) , distance/1000));
            else
                tvDistance.setText(String.format(getString(R.string.join_distance) , distance));
        }


    }


}
