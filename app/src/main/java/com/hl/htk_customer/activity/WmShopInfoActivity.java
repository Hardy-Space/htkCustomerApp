package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.ActionAdapter;
import com.hl.htk_customer.adapter.EvluateAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.ShopInfoEntity;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.model.ActionModel;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerGridItemDecoration;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/22.
 * 商家详情
 */

public class WmShopInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.sdv_shop_logo)
    SimpleDraweeView sdvShopLogo;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tv_score)
    TextView tvScore;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.tv_sell_num)
    TextView tvSellNum;
    @Bind(R.id.tv_mark)
    TextView tvMark;
    @Bind(R.id.listView_evaluate)
    RecyclerView listViewEvaluate;
    @Bind(R.id.listView_action)
    MyListView listViewAction;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tv_deliveryFee)
    TextView tvDeliveryFee;
    @Bind(R.id.tv_gonggao)
    TextView tvGongGao;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_style)
    TextView tvStyle;
    @Bind(R.id.tv_evaluateNum)
    TextView tvEvaluateNum;
    @Bind(R.id.tv_all)
    TextView tvAll;

    EvluateAdapter evluateAdapter;
    private ShopInfoEntity.DataBean dataBean;
    private int shopId = -1;
    //   private int mark = -1;
    List<ActionModel> actionModelList;
    private ActionAdapter actionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_wm_shop_info);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initWidget();
    }

    private void initWidget() {

        tvTitle.setText(getResources().getText(R.string.shangjia_detail));
        llReturn.setOnClickListener(this);
        tvAll.setOnClickListener(this);

        listViewEvaluate.setLayoutManager(new LinearLayoutManager(this));
        listViewEvaluate.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
        evluateAdapter = new EvluateAdapter(R.layout.item_evaluate , null);
        listViewEvaluate.setAdapter(evluateAdapter);
        actionModelList = new ArrayList<>();
        actionAdapter = new ActionAdapter(this);
        listViewAction.setAdapter(actionAdapter);
        disableAutoScrollToBottom();

        shopId = getIntent().getIntExtra("shopId", -1);
        Toast.makeText(WmShopInfoActivity.this,"WmShopInfoActivity.shopId=>"+shopId,Toast.LENGTH_SHORT).show();
        dataBean = (ShopInfoEntity.DataBean) getIntent().getSerializableExtra("info");
        if (dataBean != null) {
            getEvlaute(shopId);
            initData();
        }
    }


    private void initData() {
        //  sdv_shop_logo
        sdvShopLogo.setImageURI(Uri.parse(dataBean.getLogoUrl()));
        tvShopName.setText(dataBean.getShopName());
        ratingBar.setRating((float) dataBean.getScore());
        tvScore.setText(dataBean.getScore() + "");
        tvSellNum.setText("月售" + dataBean.getMonthlySalesVolume() + "单");

        if (!TextUtils.isEmpty(MyApplication.get(this).getLoginState().getLatitude())) {
            double v = Double.parseDouble(MyApplication.get(this).getLoginState().getLatitude());
            double v1 = Double.parseDouble(MyApplication.get(this).getLoginState().getLongitude());
            float distance = MyUtils.getDistance(new LatLng(dataBean.getLatitude(), dataBean.getLongitude()), new LatLng(v, v1));
            distance = (float) (Math.round(distance * 100)) / 100;
            tvDistance.setText(distance + "km");
        } else {
            tvDistance.setText("未知");
        }

        if (dataBean.getDeliveryFee() == 0) {
            tvDeliveryFee.setText("配送费：免费");
        } else {
            tvDeliveryFee.setText("配送费：" + dataBean.getDeliveryFee() + "元");
        }

        tvGongGao.setText("公告：" + dataBean.getShopBulletin().getContent());
        tvTime.setText("营业时间： " + dataBean.getOpeningTime());
        tvAddress.setText("地址： " + dataBean.getLocationAddress());
        tvStyle.setText("品类： " + dataBean.getCategoryName());


        List<ShopInfoEntity.DataBean.ShopBulletinBean> shopConsumptionActivities = dataBean.getShopConsumptionActivities();

        if (shopConsumptionActivities == null) return;
        for (int i = 0; i < shopConsumptionActivities.size(); i++) {
            actionModelList.add(new ActionModel(0, shopConsumptionActivities.get(i).getContent()));
        }
        actionAdapter.setData(actionModelList);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_all:
                Intent intent = new Intent(this, WmEvaluateActivity.class);
                intent.putExtra("shopId", shopId);
                intent.putExtra("mark", 0);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //禁止ScrollView自动滑动到最底部
    private void disableAutoScrollToBottom() {

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }


    private void getEvlaute(int shopId) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("mark", 0);
        AsynClient.post(MyHttpConfing.shopEvaluate, this, params, new GsonHttpResponseHandler() {
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

                Log.i(TAG, rawJsonResponse);

                Gson gson = new Gson();
                WmEvaluateEntity wmEvaluateEntity = gson.fromJson(rawJsonResponse, WmEvaluateEntity.class);
                if (wmEvaluateEntity.getCode() == 100) {

                    List<WmEvaluateEntity.DataBean.ListBean> list = new ArrayList<WmEvaluateEntity.DataBean.ListBean>();

                    if (wmEvaluateEntity.getData() != null && wmEvaluateEntity.getData().getList() != null && wmEvaluateEntity.getData().getCount() != 0){
                        int size = wmEvaluateEntity.getData().getList().size();

                        if (size > 3) {
                            for (int i = 0; i < 3; i++) {
                                list.add(wmEvaluateEntity.getData().getList().get(i));
                            }
                        }
                    }

                    evluateAdapter.setNewData(list);
                    tvEvaluateNum.setText("(" + wmEvaluateEntity.getData().getCount() + ")");
                }

            }
        });

    }


}
