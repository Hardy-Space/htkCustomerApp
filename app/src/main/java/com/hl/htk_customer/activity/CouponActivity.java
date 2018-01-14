package com.hl.htk_customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.CouponAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.CouponEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/30.
 * 优惠券选择
 */

public class CouponActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_coupon)
    RecyclerView rvCoupon;
    @Bind(R.id.refresh_coupon)
    SmartRefreshLayout refreshCoupon;

    private static final String EXTRA_TAG = "EXTRA_TAG";
    private static final String SHOPID = "shopId";
    private static final String PRODUCT_PRICE = "productPrice";
    public static final int REQUESTCODE = 233;
    public static final int RESULTCODE = 233 + 233;

    /**
     * tag表示进入该界面的方式，
     * 0表示从我的界面进入，此时不需要显示CheckBox
     * 1表示从支付界面进入，此时显示CheckBox，同时增加点击效果
     */
    private int tag;

    private CouponAdapter couponAdapter;
    private int shopId;


    private double mGetProductPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    public static void launch(Activity context, int shopId) {
        launch(context, 0, shopId, 0);
    }

    public static void launch(Activity context, int tag, int shopId, double productPrice) {
        Intent intent = new Intent(context, CouponActivity.class);
        intent.putExtra(EXTRA_TAG, tag);
        intent.putExtra(SHOPID, shopId);
        intent.putExtra(PRODUCT_PRICE, productPrice);
        context.startActivityForResult(intent, REQUESTCODE);
    }

    private void initBar() {
        title.setText("我的优惠");
        imgBack.setOnClickListener(this);
        titleRight.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {

        tag = getIntent().getIntExtra(EXTRA_TAG, 0);
        shopId = getIntent().getIntExtra(SHOPID, 0);
        mGetProductPrice = getIntent().getDoubleExtra(PRODUCT_PRICE, 0);

        showLoadingDialog();
        getData();

        refreshCoupon.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData();
            }
        });

        rvCoupon.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider));
        rvCoupon.addItemDecoration(divider);
        couponAdapter = new CouponAdapter(R.layout.item_coupon, null, tag, mGetProductPrice);
        couponAdapter.bindToRecyclerView(rvCoupon);
        rvCoupon.setAdapter(couponAdapter);

        couponAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (tag != 0) {

                    if (!checkIfCanClick(position)) {
                        return;
                    }

                    BaseViewHolder holder = (BaseViewHolder) rvCoupon.findViewHolderForLayoutPosition(couponAdapter.getmSelectPos());
                    if (holder != null) {
                        CheckBox checkBox = holder.getView(R.id.tv_item_coupon_check);
                        checkBox.setChecked(false);
                    } else {
                        couponAdapter.notifyItemChanged(couponAdapter.getmSelectPos());
                    }
                    couponAdapter.getData().get(position).setSelect(true);
                    if (couponAdapter.getmSelectPos() != -1) {
                        couponAdapter.getData().get(couponAdapter.getmSelectPos()).setSelect(false);
                    }
                    couponAdapter.setmSelectPos(position);

                    CheckBox checkBox = (CheckBox) couponAdapter.getViewByPosition(position, R.id.tv_item_coupon_check);
                    checkBox.setChecked(true);

                    Intent intent = new Intent();
                    intent.putExtra("id", couponAdapter.getData().get(position).getId());
                    intent.putExtra("amount", couponAdapter.getData().get(position).getTMoney());
                    setResult(RESULTCODE, intent);
                    finish();
                }
            }
        });
    }

    /**
     * @modified by 马鹏昊
     * @date 2018.1.3
     * @desc 根据满减限制和有效期限制设置是否可点击
     */
    private boolean checkIfCanClick(int position) {

        CouponEntity.DataBean dataBean = couponAdapter.getData().get(position);
        double tUseMoney = dataBean.getTUseMoney();
        String expirationStr = dataBean.getTExpiration();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expirationDate = null;
        try {
            expirationDate = dateFormat.parse(expirationStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        int i = expirationDate.compareTo(currentDate);//i<0表示前者在后者前边
        boolean ifIllegalPrice = mGetProductPrice < tUseMoney;//是否符合满减条件
        boolean ifIllegalDate = (i <= 0);//是否符合满减条件
        if (ifIllegalPrice || ifIllegalDate) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取优惠券数据
     */
    private void getData() {
        RequestParams params = new RequestParams();
        params.put("shopId", shopId);
        AsynClient.post(MyHttpConfing.getAccountCouponList, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideLoadingDialog();
                refreshCoupon.finishRefresh();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideLoadingDialog();
                refreshCoupon.finishRefresh();
                CouponEntity couponEntity = new Gson().fromJson(rawJsonResponse, CouponEntity.class);
                if (couponEntity.getCode() == 100) {
                    couponAdapter.setNewData(couponEntity.getData());
                } else {
                    showMessage(couponEntity.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.title_right:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
