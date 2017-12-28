package com.hl.htk_customer.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.WxPayFailedEntity;
import com.hl.htk_customer.entity.WxPaySuccessEntity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    public static final int ERRCODE_OK = 0;                 //支付成功的返回码
    public static final int ERRCODE_NO = -1;                //支付失败的
    public static final int ERRCODE_CANCEL = -2;            //用户取消支付

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        //这里填写自己的APPID
        api = WXAPIFactory.createWXAPI(this, getString(R.string.key_wx_appid));
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        //在这个方法中接收回调

        PayResp req1 = (PayResp) resp;

        Log.i(TAG, "onReq: " + req1.extData);
        Log.i("TAG", "PayResult-->" + resp.errCode);

        String s = "";
        switch (resp.errCode) {
            case ERRCODE_OK://支付成功的返回码
                EventBus.getDefault().post(new WxPaySuccessEntity(req1.extData));
                break;
            case ERRCODE_NO:  //支付失败的
                EventBus.getDefault().post(new WxPayFailedEntity(req1.extData));
                s = "支付失败";
                break;
            case ERRCODE_CANCEL://用户取消支付
                EventBus.getDefault().post(new WxPayFailedEntity(req1.extData));
                s = "取消支付";
                break;
        }
        finish();
    }

}
