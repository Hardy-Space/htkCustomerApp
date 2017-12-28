package com.hl.htk_customer.activity.diancan;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.alipay.share.sdk.openapi.algorithm.MD5;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.AlPayEntity;
import com.hl.htk_customer.entity.WxEntity;
import com.hl.htk_customer.entity.WxPaySuccessEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.wxapi.PayResult;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/31.
 */

public class ChoosePayStyleActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv_choose1)
    TextView tvChoose1;
    @Bind(R.id.rl_al)
    RelativeLayout rlAl;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv_choose2)
    TextView tvChoose2;
    @Bind(R.id.rl_wx)
    RelativeLayout rlWx;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.head)
    SimpleDraweeView head;

    private int tag = -1;  // 1支付宝  2微信


    private int orderIds = -1;
    private String orderNumber;
    private String orderInfo = "";
    private String orderId = "";
    private String orderBody = "";
    private static final int SDK_PAY_FLAG = 1;
    private int shopId = -1;
    private double price = 0;
    //  MyApplication.diancanList
    private String seatName = "";
    private WxEntity wxEntity;

    private Dialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_choose_style);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }


    private void initWidget() {
        mLoadingDialog = MyUtils.createLoadingDialog(this, "加载中...");
        head.setImageURI(Uri.parse(app.getUserInfoManager().getAvaUrl()));
        EventBus.getDefault().register(this);

        orderIds = getIntent().getIntExtra("orderIds", -1);
        orderNumber = getIntent().getStringExtra("orderNumber");
        //     orderNumber  = getIntent().getIntExtra("orderId" ,-1);
        price = getIntent().getDoubleExtra("price", -1);
        seatName = getIntent().getStringExtra("seatName");
        tvPrice.setText("需支付：￥" + price);
        tvTitle.setText(getResources().getText(R.string.pay));
        llReturn.setOnClickListener(this);
        rlAl.setOnClickListener(this);
        rlWx.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_al:
                tag = 1;
                clear();
                tvChoose1.setBackgroundResource(R.mipmap.xuanze04);
                break;
            case R.id.rl_wx:
                tag = 2;
                clear();
                tvChoose2.setBackgroundResource(R.mipmap.xuanze04);
                break;
            case R.id.tv_submit:

                if (tag == -1) {
                    showMessage("请选择支付方式");
                    return;
                }

                if (tag == 1) {

                    AlPay();
                } else if (tag == 2) {

                    if (mLoadingDialog != null) {
                        mLoadingDialog.show();

                    }


                    wx();
                }


                break;
        }
    }


    private void clear() {
        tvChoose2.setBackgroundResource(R.mipmap.xuanze03);
        tvChoose1.setBackgroundResource(R.mipmap.xuanze03);
    }


    private void AlPay() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("price", price);
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfing.diancanAl, this, params, new GsonHttpResponseHandler() {
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
                AlPayEntity alPayEntity = gson.fromJson(rawJsonResponse, AlPayEntity.class);

                if (alPayEntity.getCode() == 100) {
                    orderInfo = alPayEntity.getData().getAliPayResponseBody();
                    orderId = alPayEntity.getData().getOrderNumber();
                    orderBody = alPayEntity.getData().getOrderBody();

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    showMessage(alPayEntity.getMessage());
                }

            }
        });

    }


    Runnable payRunnable = new Runnable() {
        @Override
        public void run() {

            PayTask alipay = new PayTask(ChoosePayStyleActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            //   Log.i("msp", result.toString());
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

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
                        showMessage("支付成功");
                               confirmOrder(0);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showMessage("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    private void wx() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("price", price);
        params.put("appIp", MyUtils.getHostIP());
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfing.diancanWx, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);

                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                Gson gson = new Gson();
                wxEntity = gson.fromJson(rawJsonResponse, WxEntity.class);

                if (wxEntity.getCode() == 100) {
                    if (wxEntity.getData() == null) return;
                    orderId = wxEntity.getData().getOrderNumber();
                    orderBody = wxEntity.getData().getOrderBody();
                    wx_office();
                } else {
                    showMessage(wxEntity.getMessage());

                    if (mLoadingDialog.isShowing()) {
                        mLoadingDialog.dismiss();
                    }

                }

            }
        });
    }


    //调用微信官方的支付接口
    private void wx_office() {

        //  private IWXAPI api;
        //这里要更换成自己的微信key
        IWXAPI api = WXAPIFactory.createWXAPI(this, "wxb872a94f23cc21a0", false);
        api.registerApp("wxb872a94f23cc21a0");


        WxEntity.DataBean data = wxEntity.getData();
        PayReq payReq = new PayReq();
        payReq.appId = data.getAppId();
        payReq.partnerId = data.getMchId();
        payReq.prepayId = data.getPrePayId();
        payReq.nonceStr = data.getNonceStr();
        payReq.timeStamp = data.getTimestamp();
        payReq.packageValue = "Sign=WXPayWaiMai";
        //  payReq.sign = data.getSign();

        String stringA =
                "appid=" + data.getAppId()
                        + "&noncestr=" + data.getNonceStr()
                        + "&package=" + "Sign=WXPayWaiMai"
                        + "&partnerid=" + data.getMchId()
                        + "&prepayid=" + data.getPrePayId()
                        + "&timestamp=" + data.getTimestamp();

        String stringSignTemp = stringA + "&key=" + "aqkdlpondbzSkpNdopMMqaHJKLpoKjLm";
        String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();
        Log.i("Sign-->", sign);
        payReq.sign = sign;
        api.sendReq(payReq);

        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }


    }


    private void confirmOrder(int tag) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderId", orderIds);
        params.put("paymentMethodId", tag);
        AsynClient.post(MyHttpConfing.diancanSuccess, this, params, new GsonHttpResponseHandler() {
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

                    finish();

                }
                showMessage(commonMsg.getMessage());

            }
        });
    }




    @Subscribe
    public void onEventMainThread(WxPaySuccessEntity event) {

        if (event.getExtData().equals(WxPaySuccessEntity.ZIZUDIANCAN)) {
               confirmOrder(1);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
