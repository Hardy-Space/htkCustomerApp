package com.hl.htk_customer.utils.pay;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.activity.TgOrderDetailActivity;
import com.hl.htk_customer.entity.AlPayEntity;
import com.hl.htk_customer.entity.WxEntity;
import com.hl.htk_customer.entity.WxPayFailedEntity;
import com.hl.htk_customer.entity.WxPaySuccessEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.IPUtils;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import wxapi.WeiChat;

/**
 * Created by Administrator on 2017/10/20.
 * 微信支付 ，团购
 */

public class WeChatPayTg implements PayStyle {

    private int orderId;
    private String orderNumber;

    private int flag = 2;  // 1支付宝  2微信
    private int mark = 1;  // 0外卖  1团购

    private Activity mContext;
    private String orderAmount;
    private String shopId;
    private String receiptName;  //收货人名字
    private int quantity;
    private String packageId;

    private TextView submit;

    private Gson gson ;

    public WeChatPayTg(Activity context , String orderAmount , String shopId , String receiptName , int quantity , String packageId , TextView submit) {
        this.mContext = context;
        this.orderAmount = orderAmount;
        this.shopId = shopId;
        this.receiptName = receiptName;
        this.quantity = quantity;
        this.packageId = packageId;
        this.submit = submit;

        gson = new Gson();

        EventBus.getDefault().register(this);
    }

    @Override
    public void pay() {
        submit.setClickable(false);
        RequestParams params = AsynClient.getRequestParams();
        params.put("mark" , mark);
        params.put("flag" , flag);
        params.put("appIp" , IPUtils.getIp());
        params.put("packageId" , packageId);
        params.put("quantity" , quantity);
        params.put("shopId" , shopId);
        params.put("orderAmount" , orderAmount);
        params.put("receiptName" , receiptName);
        AsynClient.post(MyHttpConfing.pay, mContext, params, new GsonHttpResponseHandler() {

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

                AlPayEntity alPayEntity = gson.fromJson(rawJsonResponse, AlPayEntity.class);

                if (alPayEntity.getCode() == 100) {

                    orderId = alPayEntity.getData().getOrderId();
                    orderNumber = alPayEntity.getData().getOrderNumber();

                    new WeiChat(mContext).pay(rawJsonResponse , WxPaySuccessEntity.TUANGOU);

                } else {
                    Toast.makeText(mContext , alPayEntity.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Subscribe
    public void onEventMainThread(WxPaySuccessEntity event) {
        WxEntity.ExtData extData = gson.fromJson(event.getExtData(), WxEntity.ExtData.class);
        if (extData.getOrderNumber().equals(orderNumber)){
            submit.setClickable(true);
            Intent intent = new Intent(mContext, TgOrderDetailActivity.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("shopId", shopId);
            mContext.startActivity(intent);
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventMainThread(WxPayFailedEntity event) {
        WxEntity.ExtData extData = gson.fromJson(event.getExtData(), WxEntity.ExtData.class);
        if (extData.getOrderNumber().equals(orderNumber)){
            submit.setClickable(true);
            deleteOrder();
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("content", "其他原因");
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.cancelOrder, mContext, params, new GsonHttpResponseHandler() {
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

                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                Toast.makeText(mContext , commonMsg.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void deleteOrder(){
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.deleteOrder, mContext, params, new GsonHttpResponseHandler() {
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

                Toast.makeText(mContext , commonMsg.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

}
