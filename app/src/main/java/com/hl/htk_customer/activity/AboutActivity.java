package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.ShopInfoDetailsEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.LocationUtils;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.widget.MyRatingBar;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_about_business_title)
    TextView tvAboutBusinessTitle;
    @Bind(R.id.ratingBar)
    MyRatingBar ratingBar;
    @Bind(R.id.tv_about_business_score)
    TextView tvAboutBusinessScore;
    @Bind(R.id.tv_about_sales_of_month)
    TextView tvAboutSalesOfMonth;
    @Bind(R.id.tv_about_business_announcement)
    TextView tvAboutBusinessAnnouncement;
    @Bind(R.id.tv_about_business_delivery_fee)
    TextView tvAboutBusinessDeliveryFee;
    @Bind(R.id.cv_about_business_evaluation)
    CardView cvAboutBusinessEvaluation;
    @Bind(R.id.tv_about_business_delivery_distance)
    TextView tvAboutBusinessDeliveryDistance;
    @Bind(R.id.tv_about_business_address)
    TextView tvAboutBusinessAddress;
    @Bind(R.id.tv_about_business_hours)
    TextView tvAboutBusinessHours;
    @Bind(R.id.tv_about_business_phone)
    TextView tvAboutBusinessPhone;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("商家信息");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        cvAboutBusinessEvaluation.setOnClickListener(this);
        shopId = getIntent().getIntExtra("shopId", 0);
        getData(shopId);
    }

    private void getData(final int shopId) {
        showLoadingDialog();
        RequestParams params = new RequestParams();
        params.put("shopId", shopId);
        AsynClient.post(MyHttpConfing.getShopIntroduce, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideLoadingDialog();
                ShopInfoDetailsEntity shopInfoEntity = new Gson().fromJson(rawJsonResponse, ShopInfoDetailsEntity.class);
                if (shopInfoEntity.getCode() == 100) {
                    ShopInfoDetailsEntity.DataBean data = shopInfoEntity.getData();
                    tvAboutBusinessTitle.setText(data.getShopName());
                    ratingBar.setCountSelected((int) data.getScore());
                    tvAboutBusinessScore.setText(String.valueOf(data.getScore()));
                    tvAboutSalesOfMonth.setText(String.format(getString(R.string.join_sales_of_month), data.getMonthlySalesVolume()));

                    tvAboutBusinessAnnouncement.setText(String.format(getString(R.string.join_announcement), data.getShopBulletin().getContent()));
                    tvAboutBusinessDeliveryFee.setText(String.format(getString(R.string.join_delivery_fee), String.valueOf(data.getDeliveryFee())));
                    float distance = LocationUtils.getDistance(data.getLatitude(), data.getLongitude());
                    tvAboutBusinessDeliveryDistance.setText(String.format(getString(R.string.join_delivery_distance), distance));
                    tvAboutBusinessAddress.setText(String.format(getString(R.string.join_address) , data.getLocationAddress() != null?data.getLocationAddress() : ""));
                    tvAboutBusinessHours.setText(String.format(getString(R.string.join_business_hours) , data.getOpeningTime()!= null?data.getOpeningTime() : ""));
                    tvAboutBusinessPhone.setText(String.format(getString(R.string.join_phone) , data.getPhone() != null ? data.getPhone() : ""));

                } else {
                    showMessage(shopInfoEntity.getMessage());
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
            case R.id.cv_about_business_evaluation:
                Intent intent = new Intent(this, WmEvaluateActivity.class);
                intent.putExtra("shopId", shopId);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
