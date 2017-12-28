package com.hl.htk_customer.utils.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.hl.htk_customer.activity.OrderDetailActivity;
import com.hl.htk_customer.activity.TgOrderDetailActivity;
import com.hl.htk_customer.entity.AlPayEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.IPUtils;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.wxapi.PayResult;
import com.loopj.android.http.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/20.
 * 支付宝支付 ， 团购
 * Int  flag  1 支付宝    2微信
 * String token 用户token
 * String appIP  微信支付必须传
 * String packageId   套餐id
 * Int  quantity 数量
 * Int shopid 商铺id
 * Double orderAmount  订单总金额
 * Int mark  ０外卖　　１团购
 * String receipt_name  收货人名字
 */

public class AliPayTg implements PayStyle {

    private static final String TAG = "AliPayTg";
    private static final int SDK_PAY_FLAG = 11;
    private int orderId;
    private String orderNumber;

    private int flag = 1;  // 1支付宝  2微信
    private int mark = 1;  // 0外卖  1团购

    private Activity mContext;
    private String orderAmount;
    private String shopId;
    private String receiptName;  //收货人名字
    private int quantity;
    private String packageId;

    private TextView submit;

    private Gson gson ;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    submit.setClickable(true);
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.i(TAG, "handleMessage: " + resultInfo);

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent(mContext, TgOrderDetailActivity.class);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("shopId", shopId);
                        mContext.startActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext , "支付失败" ,Toast.LENGTH_SHORT).show();
                        //取消订单
                        deleteOrder();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public AliPayTg(Activity context , String orderAmount , String shopId , String receiptName , int quantity , String packageId , TextView submit) {
        this.mContext = context;
        this.orderAmount = orderAmount;
        this.shopId = shopId;
        this.receiptName = receiptName;
        this.quantity = quantity;
        this.packageId = packageId;
        this.submit = submit;

        gson = new Gson();
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
                Log.i(TAG, "onFailure: "+ "code ;" + statusCode + ", rawJsonData :" +  rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                AlPayEntity alPayEntity = gson.fromJson(rawJsonResponse, AlPayEntity.class);

                if (alPayEntity.getCode() == 100) {

                    final String orderInfo = alPayEntity.getData().getAliPayResponseBody();
                    orderId = alPayEntity.getData().getOrderId();
                    orderNumber = alPayEntity.getData().getOrderNumber();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PayTask aliPay = new PayTask(mContext);
                            Map<String, String> result = aliPay.payV2(orderInfo, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }).start();
                } else {
                    Toast.makeText(mContext , alPayEntity.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }
        });
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
                Log.i(MyHttpConfing.tag, rawJsonData);
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
