package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.TgOrderDetailEntity;
import com.hl.htk_customer.model.SeatOrderDetailModel;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SeatOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_tg_order_businessmen_title)
    TextView tvTgOrderBusinessmenTitle;
    @Bind(R.id.tv_tg_order_businessmen_address)
    TextView tvTgOrderBusinessmenAddress;
    @Bind(R.id.tv_tg_order_businessmen_distance)
    TextView tvTgOrderBusinessmenDistance;
    @Bind(R.id.iv_tg_order_businessmen_phone)
    ImageView ivTgOrderBusinessmenPhone;
    @Bind(R.id.tv_xiadan_time)
    TextView tvXiadanTime;
    @Bind(R.id.tv_yuding_name)
    TextView tvYudingName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_yuding_time)
    TextView tvYudingTime;
    @Bind(R.id.tv_yuding_peoplenum)
    TextView tvYudingPeoplenum;
    @Bind(R.id.tv_yuding_num)
    TextView tvYudingNum;
    @Bind(R.id.tg_order_detail_refresh)
    SmartRefreshLayout tgOrderDetailRefresh;

    private SeatOrderDetailModel orderDetailEntity;

    private String orderNumber = "";
    private String phoneNumber = "";
    private int orderId = -1;
    private int shopId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_orderdetail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        title.setText("预定订单详情");
        orderNumber = getIntent().getStringExtra("orderNumber");
        shopId = getIntent().getIntExtra("shopId", -1);

        imgBack.setOnClickListener(this);
        ivTgOrderBusinessmenPhone.setOnClickListener(this);
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

        showLoadingDialog();
        getInfo();
    }

    private void getInfo(){
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfing.getSeatOrderDetail, this, params, new GsonHttpResponseHandler() {
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
                orderDetailEntity = gson.fromJson(rawJsonResponse, SeatOrderDetailModel.class);

                if (orderDetailEntity.getCode() == 100) {
                    if (orderDetailEntity.getData() == null) {
                        return;
                    }else{
                        initData(orderDetailEntity.getData());
                    }
                }else{
                    Toast.makeText(SeatOrderDetailActivity.this,
                            orderDetailEntity.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initData(SeatOrderDetailModel.DataBean dataBean){
        phoneNumber = dataBean.getPhone();
        orderNumber = dataBean.getOrderNumber();
        tvTgOrderBusinessmenTitle.setText(dataBean.getShopName());
        tvTgOrderBusinessmenAddress.setText(dataBean.getAddress());
        tvXiadanTime.setText(dataBean.getOrderTime());
        tvYudingName.setText(dataBean.getScheduledName());
        tvPhone.setText(dataBean.getSeatPhone());
        tvYudingTime.setText(dataBean.getScheduledTime());
        tvYudingPeoplenum.setText(dataBean.getSeatCount());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_tg_order_businessmen_phone:
                MyUtils.call(SeatOrderDetailActivity.this, phoneNumber);
                break;
        }
    }
}
