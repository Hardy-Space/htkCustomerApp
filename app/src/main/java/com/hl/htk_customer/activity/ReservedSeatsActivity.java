package com.hl.htk_customer.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.NormalEntity;
import com.hl.htk_customer.entity.ShopImageEntity;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.TimeUtils;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/30.
 */

public class ReservedSeatsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_reserved_seats)
    SimpleDraweeView ivReservedSeats;
    @Bind(R.id.edt_reserved_seats_name)
    EditText edtReservedSeatsName;
    @Bind(R.id.edt_reserved_seats_phone)
    EditText edtReservedSeatsPhone;
    @Bind(R.id.tv_reserved_seats_time)
    TextView tvReservedSeatsTime;
    @Bind(R.id.edt_reserved_seats_person_num)
    EditText edtReservedSeatsPersonNum;
    @Bind(R.id.btn_reserved_seats_submit)
    TextView btnReservedSeatsSubmit;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_seats);
        ButterKnife.bind(this);

        initToolBar();
        init();
    }

    private void initToolBar() {
        title.setText("预定信息登记表");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        tvReservedSeatsTime.setOnClickListener(this);
        btnReservedSeatsSubmit.setOnClickListener(this);

        shopId = getIntent().getIntExtra("shopId" , 0);

        getImage();
    }

    private void getImage() {
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        AsynClient.post(MyHttpConfing.getShopImgUrl, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                ShopImageEntity shopImageEntity = new Gson().fromJson(rawJsonResponse, ShopImageEntity.class);
                if (shopImageEntity.getCode() == 100){
                    ivReservedSeats.setImageURI(shopImageEntity.getData().getShopImgUrl());
                }else {
                    showMessage(shopImageEntity.getMessage());
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
            case R.id.tv_reserved_seats_time:
                TimeUtils timeUtils = TimeUtils.getInstance(this);
                timeUtils.showDataPicker();
                timeUtils.setResultListener(new TimeUtils.ResultListener() {
                    @Override
                    public void getResult(String result) {
                        tvReservedSeatsTime.setText(result);
                    }
                });
                break;
            case R.id.btn_reserved_seats_submit:
                if (!new UserInfoManager(this).getISLOGIN()){
                    startActivity(new Intent(this , LoginActivity.class));
                    return;
                }

                if (TextUtils.isEmpty(edtReservedSeatsName.getText().toString()) || TextUtils.isEmpty(edtReservedSeatsPhone.getText().toString())
                        || TextUtils.isEmpty(tvReservedSeatsTime.getText().toString()) || TextUtils.isEmpty(edtReservedSeatsPersonNum.getText().toString())){
                    showMessage("信息填写不完整");
                    return;
                }

                PromptDialog.builder(this).create("确认提交")
                        .setListener(new DialogOnClickListener() {
                            @Override
                            public void onPositive() {
                                submitData();
                            }

                            @Override
                            public void onNegative() {

                            }
                        });
                break;
        }
    }

    /**
     * 提交预定信息
     */
    private void submitData() {

        showChangeDialog("提交中");
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        params.put("scheduledName" , edtReservedSeatsName.getText().toString());
        params.put("seatPhone" , edtReservedSeatsPhone.getText().toString());
        params.put("scheduledTime" , tvReservedSeatsTime.getText().toString());
        params.put("seatCount" , edtReservedSeatsPersonNum.getText().toString());
        AsynClient.post(MyHttpConfing.addReserveRequest, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                hideChangeDialog();
                Log.i(TAG, "onFailure: " + rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideChangeDialog();
                NormalEntity entity = new Gson().fromJson(rawJsonResponse, NormalEntity.class);
                if (entity.getCode() == 100){
                    showMessage(entity.getMessage());
                    Intent intentReservation = new Intent(ReservedSeatsActivity.this, MyReservationActivity.class);
                    intentReservation.putExtra("shopId" , shopId);
                    startActivity(intentReservation);
                    finish();
                }else {
                    showMessage(entity.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
