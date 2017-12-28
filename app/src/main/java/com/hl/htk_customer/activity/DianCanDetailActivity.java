package com.hl.htk_customer.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.diancan.ChoosePayStyleActivity;
import com.hl.htk_customer.adapter.DianCanOrderAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanOrderDetailEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DianCanDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.iv_callShop)
    TextView ivCallShop;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.tv_thanks)
    TextView tvThanks;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.tv_countdown)
    TextView tvDaojishi;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.iv_logo)
    SimpleDraweeView ivLogo;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.rl_shopName)
    RelativeLayout rlShopName;
    @Bind(R.id.listView_item)
    MyListView listViewItem;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.tv_cheap_price)
    TextView tvCheapPrice;
    @Bind(R.id.tv_pay_price)
    TextView tvPayPrice;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.iv_head)
    SimpleDraweeView ivHead;
    @Bind(R.id.tv_seat)
    TextView tvSeat;
    @Bind(R.id.tv_menu)
    TextView tvMenu;
    @Bind(R.id.tv_jiacai)
    TextView tvJiaCai;
    @Bind(R.id.tv_tuicai)
    TextView tvTuiCai;



    private int orderId = -1;
    private DianCanOrderDetailEntity dianCanOrderDetailEntity;

    private DianCanOrderAdapter adapter;

    private double price;
    private String zuowei;
    private String orderNumber;


    PopupWindow mPopupWindow;


    private   int  cuidanTag  = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diancan_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initWidget();
    }


    private void initWidget() {

        llReturn.setOnClickListener(this);
        tvState.setOnClickListener(this);
      //  tvMenu.setOnClickListener(this);
        tvJiaCai.setOnClickListener(this);
        tvTuiCai.setOnClickListener(this);

        orderId = getIntent().getIntExtra("orderId", -1);

        adapter = new DianCanOrderAdapter(this);
        listViewItem.setAdapter(adapter);

        mPopupWindow = new PopupWindow();

        if (orderId == -1) return;
        getDatas(orderId);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getDatas(orderId);
    }

    private void getDatas(int orderId) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderId", orderId);
        AsynClient.post(MyHttpConfing.diancanDetail, this, params, new GsonHttpResponseHandler() {
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
                dianCanOrderDetailEntity = gson.fromJson(rawJsonResponse, DianCanOrderDetailEntity.class);

                if (dianCanOrderDetailEntity.getCode() == 100) {
                    initViews();
                }


            }
        });


    }


    private void initViews() {

        try {
            ivHead.setImageURI(Uri.parse(app.getUserInfoManager().getAvaUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (dianCanOrderDetailEntity.getData() == null) return;
        DianCanOrderDetailEntity.DataBean data = dianCanOrderDetailEntity.getData();
        try {
            ivLogo.setImageURI(Uri.parse(data.getLogoUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderNumber = data.getOrderNumber();
        tvShopName.setText(data.getShopName());
        zuowei = data.getSeatName();
        tvSeat.setText(zuowei);

        if (data.getPayState() == 0) {
            tvOrderState.setText("未支付");
            tvState.setVisibility(View.VISIBLE);
            tvThanks.setVisibility(View.GONE);
            tvTuiCai.setVisibility(View.VISIBLE);
            tvJiaCai.setVisibility(View.VISIBLE);
        } else {
            tvOrderState.setText("已支付");
            tvState.setVisibility(View.GONE);
            tvThanks.setVisibility(View.VISIBLE);
            tvTuiCai.setVisibility(View.GONE);
            tvJiaCai.setVisibility(View.GONE);
        }


        if (data.getProductLists() != null) {

            adapter.setData(data.getProductLists());

        }


        price = data.getOrderAmount();
        tvPayPrice.setText("实付  ￥" + price);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_state:

                Intent intent = new Intent(DianCanDetailActivity.this, ChoosePayStyleActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("seatName", zuowei);
                intent.putExtra("orderNumber", orderNumber);
                intent.putExtra("orderIds", orderId);
                startActivity(intent);

                break;
            case R.id.tv_jiacai:
                //催单

                if(cuidanTag == -1){
                    cuidanTag = 1;
                    cuidan();
                }
                else {
                    showMessage("已经催过啦！");
                }

                break;
            case R.id.tv_tuicai:
                //退菜

                if (dianCanOrderDetailEntity.getData().getProductLists().size() <= 0) return;

                Intent intent1 = new Intent(DianCanDetailActivity.this, TuiCaiActivity.class);
                intent1.putExtra("orderNumber", orderNumber);
                intent1.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) dianCanOrderDetailEntity.getData().getProductLists());
                startActivity(intent1);

                break;

           /* case R.id.tv_menu:

                showPoupWindow(DianCanDetailActivity.this, mPopupWindow, tvMenu);

                break;*/

            default:
                break;
        }

    }


    private void showPoupWindow(final Context context, final PopupWindow popupWindow, View parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        popupWindow.setContentView(view);
        popupWindow.setHeight(getWindowManager().getDefaultDisplay().getHeight());
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.showAsDropDown(rlTitle);
        view.setBackgroundColor(getResources().getColor(R.color.dialogplus_black_overlay_al));


        TextView cuidan = view.findViewById(R.id.tv_cuidan);
        TextView tuicai = view.findViewById(R.id.tv_tuicai);

        cuidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cuidan();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

            }
        });


        tuicai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dianCanOrderDetailEntity.getData().getProductLists().size() <= 0) return;

                Intent intent = new Intent(context, TuiCaiActivity.class);
                intent.putExtra("orderNumber", orderNumber);
                intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) dianCanOrderDetailEntity.getData().getProductLists());
                startActivity(intent);

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

            }
        });


    }


    //催单
    private void cuidan() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfing.cuidan, this, params, new GsonHttpResponseHandler() {
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

                showMessage(commonMsg.getMessage());


            }
        });


    }


}
