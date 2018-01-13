package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.OrderItemDetailAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.CustomizeMsgEntity;
import com.hl.htk_customer.entity.NormalEntity;
import com.hl.htk_customer.entity.WmOrderDetailEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.OrderStateChangeEvent;
import com.hl.htk_customer.model.ShopProduct;
import com.hl.htk_customer.model.TimeChangeEvent;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.service.OrderTimeService;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * 订单详情
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    ImageView llReturn;
    @Bind(R.id.iv_head)
    SimpleDraweeView ivHead;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.tv_thanks)
    TextView tvThanks;
    @Bind(R.id.tv_againItem)
    TextView tvAgainItem;
    @Bind(R.id.tv_reminder)
    TextView tvReminder;
    @Bind(R.id.tv_evaluate)
    TextView tvEvaluate;
    @Bind(R.id.iv_logo)
    SimpleDraweeView ivLogo;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.listView_item)
    MyListView listViewItem;
    @Bind(R.id.tv_price_send)
    TextView tvPriceSend;
    @Bind(R.id.tv_pay_price)
    TextView tvPayPrice;
    @Bind(R.id.tv_call_shop)
    TextView tvCallShop;
    @Bind(R.id.tv_mark_address)
    TextView tvMarkAddress;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.mark_item_info)
    TextView markItemInfo;
    @Bind(R.id.tv_orderId)
    TextView tvOrderId;
    @Bind(R.id.tv_pay_style)
    TextView tvPayStyle;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.iv_callShop)
    TextView ivCallShop;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tv_countdown)
    TextView tvDaoJiShi;
    @Bind(R.id.tv_time)
    TextView tvTime;

    private final int EVALUATE_REQUEST_CODE = 202;
    private List<ShopProduct> productList;
    OrderItemDetailAdapter orderItemDetailAdapter;
    private int orderId = -1;
    private int orderState = -1;
    private String shopPhone = "";
    private String orderNumber = "";
    private String shopName;
    private String shopLogo;

    private int shopId = -1;
    private double orderAmount = 0.0;

    private WmOrderDetailEntity wmOrderDetailEntity;
    private int mark = -1;// 0 支付宝  1微信

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_order_detail);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void onEventMainThread(OrderStateChangeEvent event){
        //接受JPush推送的消息状态后做出改变
        CustomizeMsgEntity msgEntity = new Gson().fromJson(event.getMsg(), CustomizeMsgEntity.class);
        if (msgEntity.getOrderNumber().equals(orderNumber)){
            orderState = msgEntity.getOrderState();
            setState(orderState);
            Log.i(TAG, "onEventMainThread: 1");

            if (orderState == 2 || orderState == 5 ){
                Log.i(TAG, "onEventMainThread: 3");
                //商家已结单，停止计时
                OrderTimeService.stop(getApplicationContext());
                tvTime.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe
    public void onEventMainThread(TimeChangeEvent event){
        if (event.isCancle()){
            OrderTimeService.stop(getApplicationContext());
            tvTime.setVisibility(View.GONE);
            cancelOrder();
        }else {
            tvTime.setVisibility(View.VISIBLE);
            Log.i(TAG, "onEventMainThread: " + event.getTime());
            tvTime.setText(event.getTime());
        }
    }

    private void initWidget() {
        shopId = Integer.valueOf(getIntent().getStringExtra("shopId"));
        orderId = getIntent().getIntExtra("orderId", -1);
        ivHead.setImageURI(Uri.parse(new UserInfoManager(mContext).getAvaUrl()));
        llReturn.setOnClickListener(this);
        ivCallShop.setOnClickListener(this);
        tvAgainItem.setOnClickListener(this);
        tvReminder.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        tvCallShop.setOnClickListener(this);
        tvShopName.setOnClickListener(this);
        orderItemDetailAdapter = new OrderItemDetailAdapter(this);
        listViewItem.setAdapter(orderItemDetailAdapter);
        getDetail();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.iv_callShop:
                showDialog();
                break;
            case R.id.tv_call_shop:
                MyUtils.call(OrderDetailActivity.this, shopPhone);
                break;
            case R.id.tv_shopName:
                Intent intent = new Intent(this, WmShopDetailActivity.class);
                intent.putExtra("shopId", shopId);
                startActivity(intent);
                break;
            case R.id.tv_againItem:
                again();
                break;
            case R.id.tv_reminder:
                showReminderDialog();
                break;
            case R.id.tv_evaluate:
                Intent intentEvaluate = new Intent(mContext, EvaluateActivity.class);
                intentEvaluate.putExtra("orderId", orderId);
                intentEvaluate.putExtra("logo", shopLogo);
                intentEvaluate.putExtra("shopName", shopName);
                startActivityForResult(intentEvaluate , EVALUATE_REQUEST_CODE);
                break;
            default:
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EVALUATE_REQUEST_CODE && resultCode == 203){
            showEvaluateState(1);
        }
    }

    private void showDialog() {
        String prompt = "";
        if (orderState == 1 || orderState == 2) {
            prompt = "确定取消订单？";
        } else if (orderState == 3){
            prompt = "确定收货？";
        }
        PromptDialog.builder(this).create(prompt)
                .setListener(new DialogOnClickListener() {
                    @Override
                    public void onPositive() {
                        if (orderState == 1 || orderState == 2) cancelOrder();
                        else if (orderState == 3) shouhuo();
                    }

                    @Override
                    public void onNegative() {

                    }
                });
    }

    private void showReminderDialog() {
        String prompt = "确认催单？";
        PromptDialog.builder(this).create(prompt)
                .setListener(new DialogOnClickListener() {
                    @Override
                    public void onPositive() {
                        reminder();
                    }

                    @Override
                    public void onNegative() {

                    }
                });
    }

    //获取详情
    private void getDetail() {
        showLoadingDialog();
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderId", orderId);
        AsynClient.post(MyHttpConfing.orderDetail, this, params, new GsonHttpResponseHandler() {
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
                Log.i(TAG, rawJsonResponse);
                Gson gson = new Gson();
                wmOrderDetailEntity = gson.fromJson(rawJsonResponse, WmOrderDetailEntity.class);
                if (wmOrderDetailEntity.getCode() == 100) {
                    initView(wmOrderDetailEntity);
                }
                hideLoadingDialog();
            }
        });
    }

    private void initView(WmOrderDetailEntity wmOrderDetailEntity) {

        orderNumber = wmOrderDetailEntity.getData().getOrderNumber();

        productList = new ArrayList<>();
        List<WmOrderDetailEntity.DataBean.ProductListBean> productLists = wmOrderDetailEntity.getData().getProductList();
        for (int i = 0; i < productLists.size(); i++) {
            WmOrderDetailEntity.DataBean.ProductListBean productListBean = productLists.get(i);
            productList.add(new ShopProduct(productListBean.getProductName(), productListBean.getQuantity(), productListBean.getPrice() + "", productListBean.getProductId()));
        }
        orderItemDetailAdapter.setData(productList);


        WmOrderDetailEntity.DataBean data = wmOrderDetailEntity.getData();
        orderAmount = data.getOrderAmount();
        shopPhone = data.getShopMobilePhone();
        shopName = data.getShopName();
        shopLogo = data.getLogoUrl();
        ivLogo.setImageURI(Uri.parse(data.getLogoUrl()));
        tvShopName.setText(data.getShopName());
        tvPayPrice.setText("实付 ￥" + data.getOrderAmount());
        tvPriceSend.setText(String.valueOf(data.getDeliveryFee()));

        orderState = data.getOrderState();
        showEvaluateState(data.getCommentStatus());
        setState(orderState);


        String sex = "";
        if (data.getSex() == 0) {
            sex = "女士";
        } else {
            sex = "先生";
        }
        tvName.setText(data.getReceiptName() + sex);
        tvPhoneNumber.setText(data.getReceivingCall() + "");
        tvAddress.setText(data.getShippingAddress());
        tvOrderId.setText("订单号：" + data.getOrderNumber());
        String payWay = "";

        if (data.getPaymentMethod() == 0) {
            payWay = "支付宝";
            mark = 0;
        } else {
            payWay = "微信";
            mark = 1;
        }

        tvPayStyle.setText("支付方式：" + payWay);
        tvOrderTime.setText("下单时间：" + data.getOrderTime());

        //未结单状态下，启动计时器,设置2秒延迟
        if (orderState == 1){
            if (data.getTimeLeft() > 2000){
                OrderTimeService.startOrderTimeService(mContext.getApplicationContext() , (int)data.getTimeLeft());
            }else {
                cancelOrder();
            }
        }

    }

    /**
     * 根据评论状态决定是否显示评价按钮
     */
    private void showEvaluateState(int status ) {
        switch (status){
            case 0://未评论
                if (orderState == 4){
                    tvEvaluate.setVisibility(View.VISIBLE);
                }else {
                    tvEvaluate.setVisibility(View.GONE);
                }
                break;
            case 1://已评论
                tvEvaluate.setVisibility(View.GONE);
                break;
        }
    }

    public void setState(int orderState){
        String state = "";
        switch (orderState) {
            case 0:
                state = "等待支付";
                tvThanks.setVisibility(View.GONE);
                ivCallShop.setVisibility(View.GONE);
                tvAgainItem.setVisibility(View.GONE);
                tvReminder.setVisibility(View.GONE);
                break;
            case 1:
                state = "等待商家接单";
                tvThanks.setVisibility(View.GONE);
                ivCallShop.setVisibility(View.VISIBLE);
                ivCallShop.setText("取消订单");
                tvAgainItem.setVisibility(View.GONE);
                tvReminder.setVisibility(View.GONE);

                break;
            case 2:
                state = "商家已接单";
                tvThanks.setVisibility(View.GONE);
                ivCallShop.setVisibility(View.VISIBLE);
                ivCallShop.setText("取消订单");
                tvAgainItem.setVisibility(View.GONE);
                tvReminder.setVisibility(View.VISIBLE);

                break;
            case 3:
                state = "订单派送中";
                tvThanks.setVisibility(View.GONE);
                ivCallShop.setVisibility(View.VISIBLE);
                ivCallShop.setText("确认收货");
                tvAgainItem.setVisibility(View.GONE);
                tvReminder.setVisibility(View.VISIBLE);
                break;
            case 4:
                state = "订单已完成";
                tvThanks.setVisibility(View.VISIBLE);
                ivCallShop.setVisibility(View.GONE);
                tvAgainItem.setVisibility(View.VISIBLE);
                tvReminder.setVisibility(View.GONE);
                break;
            case 5:
                state = "订单已取消";
                tvThanks.setVisibility(View.VISIBLE);
                ivCallShop.setVisibility(View.GONE);
                tvAgainItem.setVisibility(View.VISIBLE);
                tvReminder.setVisibility(View.GONE);

                break;
            default:
                break;
        }

        tvOrderState.setText(state);
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {
        showChangeDialog(getString(R.string.order_cancel_changing));

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("content", "其他原因");
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.cancelOrder, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                showMessage(commonMsg.getMessage());

                hideChangeDialog();
            }
        });

    }

    /**
     * 催单
     */
    public void reminder(){
        showChangeDialog("催单");
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber" , orderNumber );
        AsynClient.post(MyHttpConfing.reminder, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                hideChangeDialog();
                showMessage("请求出错");
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideChangeDialog();
                NormalEntity entity = new Gson().fromJson( rawJsonResponse , NormalEntity.class);
                if (entity.getCode() != 100){
                    showMessage("请求出错");
                }
            }
        });
    }

    /**
     * 再来一单
     */
    private void again() {

        List<ShopProduct> productList1 = new ArrayList<ShopProduct>();

        for (int i = 0; i < productList.size(); i++) {
            ShopProduct shopProduct = productList.get(i);
            productList1.add(new ShopProduct(shopProduct.getGoods(), shopProduct.getNumber(), shopProduct.getPrice(), shopProduct.getId()));
        }

        Intent intent = new Intent(this, WmShopDetailActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) productList1);
        intent.putExtra("price", orderAmount);
        startActivity(intent);


    }

    //确认收货
    private void shouhuo() {
        showChangeDialog(getString(R.string.confirm_receipt));

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfing.shouhuo, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);

                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                showMessage(commonMsg.getMessage());

                hideChangeDialog();
            }
        });


    }
}
