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
import com.hl.htk_customer.activity.ConfirmOrderActivity;
import com.hl.htk_customer.activity.OrderDetailActivity;
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
 * 支付宝支付 ， 外卖
 * <p>
 * Created by Administrator on 2017/9/25.
 * Int  flag  1 支付宝    2微信
 * String token 用户token
 * String appIP  微信支付必须传
 * String jsonProductList  产品集合字符串  {productName, quantity, price,productId}
 * Int  shopId  商铺id
 * Double orderAmount  订单总金额
 * Int mark  ０外卖　　１团购
 * String shippingAddress  收货地址
 * String receivingCall  收货人手机号
 * String receiptName  收货人名字
 * private Double longitude;   //地址经度
 * private Double latitude;   //地址纬度
 * private Integer sex;   //收货人性别
 */

public class AliPayWaiMai implements PayStyle {

    private static final int SDK_PAY_FLAG = 1;
    private int orderId;
    private String orderNumber;

    private int flag = 1;  // 1支付宝  2微信
    private int mark = 0;  // 0外卖  1团购

    private final String mJsonProductList;
    private Activity mContext;
    private String orderAmount;
    private String shopId;

    /**
     * @modified by 马鹏昊
     * @date 2018.1.3
     * @desc 向后台传入优惠券id
     */
    private String mCouponId;

    private String shippingAddress;  //收货地址
    private String receivingCall;  //收货人手机号
    private String receiptName;  //收货人名字
    private Double longitude;   //地址经度

    private Double latitude;   //地址纬度
    private Integer sex;   //收货人性别

    private TextView submit;//提交按钮

    private Gson gson;

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
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        mContext.finish();

                        //开启订单详情界面
                        Intent intent = new Intent(mContext, OrderDetailActivity.class);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("shopId", shopId);
                        mContext.startActivity(intent);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
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

    public AliPayWaiMai(Activity context, String couponId, String orderAmount, String shopId, List<ProductList> list,
                        String shippingAddress, String receivingCall, String receiptName, Double longitude, Double latitude, Integer sex, TextView submit) {
        this.mContext = context;
        this.orderAmount = orderAmount;
        this.shopId = shopId;
        this.shippingAddress = shippingAddress;
        this.receivingCall = receivingCall;
        this.receiptName = receiptName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.sex = sex;
        this.submit = submit;

        /**
         * @modified by 马鹏昊
         * @date 2018.1.3
         * @desc 向后台传入优惠券id
         */
        this.mCouponId = couponId;

        gson = new Gson();
        mJsonProductList = gson.toJson(list);
    }

    @Override
    public void pay() {
        submit.setClickable(false);

        RequestParams params = AsynClient.getRequestParams();
        params.put("mark", mark);
        params.put("flag", flag);

        /**
         * @modified by 马鹏昊
         * @date 2018.1.3
         * @desc 如果选择了优惠券就向后台传入优惠券id，否则不传该值
         */
        String noCoupon = String.valueOf(ConfirmOrderActivity.NON_COUPON);
        if (!noCoupon.equals(mCouponId))
            params.put("couponId", mCouponId);

        params.put("appIp", IPUtils.getIp());
        params.put("jsonProductList", mJsonProductList);
        params.put("shopId", shopId);
        params.put("orderAmount", orderAmount);
        params.put("shippingAddress", shippingAddress);
        params.put("receivingCall", receivingCall);
        params.put("receiptName", receiptName);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("sex", sex);
        AsynClient.post(MyHttpConfing.pay, mContext, params, new GsonHttpResponseHandler() {

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                submit.setClickable(true);
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                submit.setClickable(true);
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                submit.setClickable(true);
                Gson gson = new Gson();
                AlPayEntity alPayEntity = gson.fromJson(rawJsonResponse, AlPayEntity.class);

                if (alPayEntity.getCode() == 100) {

                    final String orderInfo = alPayEntity.getData().getAliPayResponseBody();
                    orderNumber = alPayEntity.getData().getOrderNumber();
                    orderId = alPayEntity.getData().getOrderId();

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
                    Toast.makeText(mContext, alPayEntity.getMessage(), Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                Toast.makeText(mContext, commonMsg.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void deleteOrder() {
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

                //                Toast.makeText(mContext , commonMsg.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static class ProductList {
        private String productName;
        private int quantity;
        private double price;
        private int productId;

        public ProductList(String productName, int quantity, double price, int productId) {
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.productId = productId;
        }
    }

}
