package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.diancan.DianCanActivity;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.CollectionDialog;
import com.hl.htk_customer.entity.ScanEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;



/**
 * Created by Administrator on 2017/6/21.
 * 二维码扫描界面
 */

public class CustomScanActivity extends BaseActivity  {

    private static final String TAG = CustomScanActivity.class.getSimpleName();

//    private ZXingView zXingView;
//    private QRCodeView mQRCodeView;
    private String mScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
//
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        zXingView = (ZXingView) findViewById(R.id.zxingview);
//        mQRCodeView = zXingView;
//        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mQRCodeView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
//        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

//    @Override
//    public void onScanQRCodeSuccess(String result) {
//        Log.i(TAG, "result:" + result);
//        mScanResult = result;
//        analysis(result);
//        vibrate();
//        mQRCodeView.startSpot();
//    }
//
//    @Override
//    public void onScanQRCodeOpenCameraError() {
//        Log.e(TAG, "打开相机出错");
//    }

    private boolean isOpen = false;
    public void onClick(View view){
        if (isOpen){
//            mQRCodeView.closeFlashlight();
            isOpen = false;
        }else {
//            mQRCodeView.openFlashlight();
            isOpen = true;
        }
    }

    private void analysis(final String result) {
        RequestParams params = AsynClient.getRequestParams();

        params.put("qrKey", result);

        AsynClient.post(MyHttpConfing.erweima, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {

                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                ScanEntity scanEntity = gson.fromJson(rawJsonResponse, ScanEntity.class);

                if (scanEntity.getCode() == 100) {

                    try {
                        MyApplication.shopId = scanEntity.getData().getShopId();
                    } catch (Exception e) {

                    }

                    if (scanEntity.getData().isResult()) {
                        //已收藏。调到页面
                        Intent intent = new Intent(CustomScanActivity.this, DianCanActivity.class);
                        intent.putExtra("ScanResult", result);
                        startActivity(intent);
                    } else {
                        showMarDialog(scanEntity.getData().getShopId());
                    }
                }
            }
        });

    }


    private void showMarDialog(final int shopId) {

        final CollectionDialog collectionDialog = new CollectionDialog(this);
        collectionDialog.show();
        collectionDialog.setCancelable(false);
        TextView tvCancel = (TextView) collectionDialog.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) collectionDialog.findViewById(R.id.tv_ok);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (collectionDialog != null && collectionDialog.isShowing()) {
                    collectionDialog.dismiss();
                }

            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionDialog.dismiss();
                collection(shopId);
            }
        });

    }

    private void collection(int id) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", id);
        params.put("mark", 1);
        AsynClient.post(MyHttpConfing.collection, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {


                    Intent intent = new Intent(CustomScanActivity.this, DianCanActivity.class);
                    intent.putExtra("ScanResult", mScanResult);
                    startActivity(intent);

                } else {

                }

            }
        });

    }
}
