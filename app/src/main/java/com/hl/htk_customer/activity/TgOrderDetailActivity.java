package com.hl.htk_customer.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.TaoCanDetailsAdapter;
import com.hl.htk_customer.adapter.TaoCanOrderDetailsAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.CustomizeMsgEntity;
import com.hl.htk_customer.entity.TgOrderDetailEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.LocationInfoManager;
import com.hl.htk_customer.model.OrderStateChangeEvent;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.LocationUtils;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/3.
 *
 */

public class TgOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_tg_order_icon)
    SimpleDraweeView ivTgOrderIcon;
    @Bind(R.id.tv_tg_order_title)
    TextView tvTgOrderTitle;
    @Bind(R.id.tv_tg_order_content)
    TextView tvTgOrderContent;
    @Bind(R.id.tv_tg_order_original_price)
    TextView tvTgOrderOriginalPrice;
    @Bind(R.id.tv_tg_order_current_price)
    TextView tvTgOrderCurrentPrice;
    @Bind(R.id.tv_tg_order_pay)
    TextView tvTgOrderPay;
    @Bind(R.id.tv_tg_order_businessmen_title)
    TextView tvTgOrderBusinessmenTitle;
    @Bind(R.id.tv_tg_order_businessmen_address)
    TextView tvTgOrderBusinessmenAddress;
    @Bind(R.id.tv_tg_order_businessmen_distance)
    TextView tvTgOrderBusinessmenDistance;
    @Bind(R.id.iv_tg_order_businessmen_phone)
    ImageView ivTgOrderBusinessmenPhone;
    @Bind(R.id.tv_tg_order_number)
    TextView tvTgOrderNumber;
    @Bind(R.id.tv_tg_order_phone)
    TextView tvTgOrderPhone;
    @Bind(R.id.tv_tg_order_time)
    TextView tvTgOrderTime;
    @Bind(R.id.tv_tg_order_num)
    TextView tvTgOrderNum;
    @Bind(R.id.tv_tg_order_total_price)
    TextView tvTgOrderTotalPrice;
    @Bind(R.id.tg_order_detail_refresh)
    SmartRefreshLayout tgOrderDetailRefresh;
    @Bind(R.id.tv_tg_order_group_vouchers)
    TextView tvTgOrderGroupVoucher;
    @Bind(R.id.tv_tg_order_voucher_code)
    TextView tvTgOrderVoucherCode;
    @Bind(R.id.tv_tg_order_voucher_use)
    TextView tvTgOrderVoucherUse;
    @Bind(R.id.rv_tg_order_details)
    RecyclerView rvOrderDetails;

    private int orderId = -1;
    private int shopId = -1;
    private TgOrderDetailEntity orderDetailEntity;

    private String orderNumber = "";
    private String phoneNumber = "";

    private int mark = -1; //0 支付包   1微信
    private int orderState;
    private TaoCanOrderDetailsAdapter detailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tg_order_detail);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initWidget();
    }

    private void initWidget() {

        setSupportActionBar(toolbar);
        title.setText("团购订单详情");

        imgBack.setOnClickListener(this);
        ivTgOrderBusinessmenPhone.setOnClickListener(this);
        tvTgOrderPay.setOnClickListener(this);

        orderId = getIntent().getIntExtra("orderId", -1);
        shopId = getIntent().getIntExtra("shopId", -1);

        rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
        rvOrderDetails.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST , R.drawable.shape_line));
        detailsAdapter = new TaoCanOrderDetailsAdapter(R.layout.item_taocan_details , null);
        rvOrderDetails.setAdapter(detailsAdapter);

        showLoadingDialog();
        getInfo();

        tgOrderDetailRefresh.setEnableLoadmore(false);
        tgOrderDetailRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getInfo();
            }
        });

    }

    private void getInfo() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderId", orderId);
        AsynClient.post(MyHttpConfing.tgTaoOrderDetail, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
                tgOrderDetailRefresh.finishRefresh();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                tgOrderDetailRefresh.finishRefresh();
                hideLoadingDialog();
                Gson gson = new Gson();
                orderDetailEntity = gson.fromJson(rawJsonResponse, TgOrderDetailEntity.class);

                if (orderDetailEntity.getCode() == 100) {
                    if (orderDetailEntity.getData() == null) return;
                    initData(orderDetailEntity.getData());
                }
            }
        });
    }

    public void initData(TgOrderDetailEntity.DataBean data){
        phoneNumber = data.getShopPhone();
        orderNumber = data.getOrderNumber();

        ivTgOrderIcon.setImageURI(Uri.parse(data.getLogoUrl()));
        tvTgOrderTitle.setText(data.getShopName());
        tvTgOrderContent.setText(data.getPackageName());
        tvTgOrderOriginalPrice.setText(String.format(getString(R.string.join_price_in_store) , data.getRetailPrice()));
        tvTgOrderCurrentPrice.setText(String.format(getString(R.string.join_order_money) , data.getOrderAmount()));
        tvTgOrderVoucherCode.setText(String.format(getString(R.string.join_voucher_code) , String.valueOf(data.getVoucherNumber())));
        tvTgOrderGroupVoucher.setText(String.format(getString(R.string.join_group_vouchers) , data.getValidityTime()));

        orderState = data.getOrderState();
        setByOrderState(orderState);

        tvTgOrderBusinessmenTitle.setText(data.getShopName());
        tvTgOrderBusinessmenAddress.setText(data.getShopAddress());

        float distance = LocationUtils.getDistance(data.getLatitude(), data.getLongitude());
        if (distance == 0){
            tvTgOrderBusinessmenDistance.setText("未知");
        }else {
            if (distance >= 1000)
                tvTgOrderBusinessmenDistance.setText(String.format(getString(R.string.join_distance_km) , distance/1000));
            else
                tvTgOrderBusinessmenDistance.setText(String.format(getString(R.string.join_distance) , distance));
        }

        detailsAdapter.setNewData(data.getBuyPackageContentList());

        tvTgOrderNumber.setText(String.format(getString(R.string.text_tg_order_number) , data.getOrderNumber()));
        tvTgOrderPhone.setText(String.format(getString(R.string.text_tg_order_phone) , data.getUserPhone()));
        tvTgOrderTime.setText(String.format(getString(R.string.text_tg_order_time) , data.getOrderTime()));
        tvTgOrderNum.setText(String.format(getString(R.string.text_tg_order_num) , String.valueOf(data.getQuantity())));
        tvTgOrderTotalPrice.setText(String.format(getString(R.string.text_tg_order_total_price) , data.getOrderAmount()));
    }

    @Subscribe
    public void onEventMainThread(OrderStateChangeEvent event){
        //接受JPush推送的消息状态后做出改变
        CustomizeMsgEntity msgEntity = new Gson().fromJson(event.getMsg(), CustomizeMsgEntity.class);
        if (msgEntity.getOrderNumber().equals(orderNumber)){
            orderState = msgEntity.getOrderState();
            setByOrderState(orderState);
        }
    }

    /**
     * 根据状态改变界面
     */
    public void setByOrderState(int state){
        switch (state){
            case 10:
                tvTgOrderPay.setText("退款");
                tvTgOrderPay.setClickable(true);
                tvTgOrderPay.setBackgroundColor(getResources().getColor(R.color.colorOrange));

                tvTgOrderVoucherUse.setText("待消费");
                break;
            case 11:
                tvTgOrderPay.setText("已使用");
                tvTgOrderPay.setClickable(false);
                tvTgOrderPay.setBackgroundColor(getResources().getColor(R.color.colorTvHint));

                tvTgOrderVoucherUse.setText("已使用");
                break;
            case 12:
                tvTgOrderPay.setText("已取消");
                tvTgOrderPay.setClickable(false);
                tvTgOrderPay.setBackgroundColor(getResources().getColor(R.color.colorTvHint));

                tvTgOrderVoucherUse.setText("已取消");
                break;
        }
    }

    private void cancelOrder() {
        showChangeDialog("取消订单");
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("content", "其他");
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
                Log.i(TAG, rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                showMessage(commonMsg.getMessage());
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_tg_order_businessmen_phone:
                MyUtils.call(TgOrderDetailActivity.this, phoneNumber);
                break;
            case R.id.tv_tg_order_pay:
                PromptDialog.builder(TgOrderDetailActivity.this)
                        .create("确认退款？")
                        .setListener(new DialogOnClickListener() {
                            @Override
                            public void onPositive() {
                                cancelOrder();
                            }

                            @Override
                            public void onNegative() {

                            }
                        });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }
}
