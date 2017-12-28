package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.OrderItemDetailAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.OneLineDialog;
import com.hl.htk_customer.model.DefaultAddress;
import com.hl.htk_customer.model.ShopInfoModel;
import com.hl.htk_customer.model.ShopProduct;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.pay.AliPayWaiMai;
import com.hl.htk_customer.utils.pay.PayStyle;
import com.hl.htk_customer.utils.pay.WXPayWaiMai;
import com.hl.htk_customer.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/24.
 * 确认订单
 */

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_userInfo)
    TextView tvUserInfo;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_payWay)
    TextView tvPayWay;
    @Bind(R.id.sdv_logo)
    SimpleDraweeView sdvLogo;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.listView_item)
    MyListView listViewItem;
    @Bind(R.id.tv_daizhifu)
    TextView tvDaizhifu;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.rl_payWay)
    LinearLayout rlPayWay;
    @Bind(R.id.et_mark)
    EditText etMark;
    @Bind(R.id.confirm_order_delivery_fee_num)
    TextView mTvDeliveryFeeNum;
    @Bind(R.id.confirm_order_Vouchers_num)
    TextView mTvVouchersNum;

    private List<ShopProduct> productList;
    private int shopId = -1;
    private double goodsPrice = 0.0;//商品价格
    private double mDeliveryFee = 0.0;//配送费用
    private double mVouchers = 0.0;//代金券
    OrderItemDetailAdapter orderItemDetailAdapter;

    private List<AliPayWaiMai.ProductList> products = new ArrayList<>();

    private OneLineDialog payWayDialog;
    List<String> list = new ArrayList<>();
    private int payWayTag = 0;  // 0 支付宝  1 微信
    String mark = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_confirm_order);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {

        tvTitle.setText(getResources().getText(R.string.confirm_order));
        llReturn.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        rlPayWay.setOnClickListener(this);
        mTvVouchersNum.setOnClickListener(this);
        initDialog();
        initItem();
        initAddress();
    }


    private void initDialog() {
        payWayDialog = new OneLineDialog(this);
        list.add("支付宝");
        list.add("微信");

        payWayDialog.setOngetDataListener(new OneLineDialog.getDataListener() {
            @Override
            public void getData(String time, int viewId) {
                tvPayWay.setText(time);

                if ("支付宝".equals(time)) {
                    payWayTag = 0;
                } else if ("微信".equals(time)) {
                    payWayTag = 1;
                }

            }
        }, 1);


    }


    private void initItem() {
        Bundle bundle = getIntent().getExtras();
        productList = bundle.getParcelableArrayList("productList");

        for (int i = 0; i < productList.size(); i++) {
            products.add(new AliPayWaiMai.ProductList(
                    productList.get(i).getGoods(),
                    productList.get(i).getNumber(),
                    Double.valueOf(productList.get(i).getPrice()),
                    productList.get(i).getId()
            ));
        }

        shopId = bundle.getInt("shopId");
        mDeliveryFee = bundle.getDouble("deliveryFee");
        goodsPrice = bundle.getDouble("price");
        orderItemDetailAdapter = new OrderItemDetailAdapter(this);
        listViewItem.setAdapter(orderItemDetailAdapter);
        orderItemDetailAdapter.setData(productList);
        setTotalPriceText();
        sdvLogo.setImageURI(Uri.parse(ShopInfoModel.getUrl()));
        tvShopName.setText(ShopInfoModel.getShopName());
        mTvDeliveryFeeNum.setText(String.valueOf(mDeliveryFee));

    }

    /**
     * 显示总价
     */
    private void setTotalPriceText() {
        tvDaizhifu.setText(String.format("待支付￥ %1$.2f" , getTotalPrice())  );
        tvPrice.setText(String.format("待支付￥ %1$.2f" , getTotalPrice()));
    }

    /**
     * 计算商品总价
     * @return 总价
     */
    public double getTotalPrice(){
        return goodsPrice + mDeliveryFee - mVouchers;
    }


    private void initAddress() {

        if (app.getDefaultAddress().getAddressId() == -1) {
            tvUserInfo.setText("请选择收货地址");
            return;
        }
        String sex = "";
        if (app.getDefaultAddress().getSex() == 0) {
            sex = "女士";
        } else {
            sex = "先生";
        }

        tvUserInfo.setText(app.getDefaultAddress().getUserName() + sex + "  " + app.getDefaultAddress().getPhoneNumber());
        tvAddress.setText(app.getDefaultAddress().getLocation() + app.getDefaultAddress().getAddress());

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CouponActivity.REQUESTCODE && resultCode == CouponActivity.RESULTCODE){
            int id = data.getIntExtra("id", 0);
            mVouchers = data.getDoubleExtra("amount", 0);
            mTvVouchersNum.setText(String.format(getString(R.string.join_amount_cut) , mVouchers));
            setTotalPriceText();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_address:
                Intent intent = new Intent(ConfirmOrderActivity.this, HomeAddressActivity.class);
                intent.putExtra("TAG", 1);
                intent.putExtra("ChooseTag", 1);
                startActivity(intent);
                break;
            case R.id.tv_submit:

                if (app.getDefaultAddress().getAddressId() == -1) {
                    showMessage("请选择收货地址");
                    return;
                }

                if (payWayTag == 0) {
                    DefaultAddress defaultAddress = app.getDefaultAddress();
                    PayStyle pay = new AliPayWaiMai(ConfirmOrderActivity.this , String.valueOf(getTotalPrice()) , String.valueOf(shopId) , products ,
                            defaultAddress.getLocation() + defaultAddress.getAddress(),
                            String.valueOf(defaultAddress.getPhoneNumber()),
                            defaultAddress.getUserName(),
                            MyApplication.getmAMapLocation().getLongitude(),
                            MyApplication.getmAMapLocation().getLatitude(),
                            defaultAddress.getSex() , tvSubmit);
                    pay.pay();
                } else {
                    DefaultAddress defaultAddress = app.getDefaultAddress();
                    PayStyle pay = new WXPayWaiMai(ConfirmOrderActivity.this , String.valueOf(getTotalPrice()) , String.valueOf(shopId) , products ,
                            defaultAddress.getLocation() + defaultAddress.getAddress(),
                            String.valueOf(defaultAddress.getPhoneNumber()),
                            defaultAddress.getUserName(),
                            MyApplication.getmAMapLocation().getLongitude(),
                            MyApplication.getmAMapLocation().getLatitude(),
                            defaultAddress.getSex() , tvSubmit);
                    pay.pay();
                }
                break;
            case R.id.rl_payWay:
                payWayDialog.setData(list);
                payWayDialog.setShowOne("支付宝");
                payWayDialog.show();
                break;
            case R.id.confirm_order_Vouchers_num:
                CouponActivity.launch(this , 1 , shopId);
                break;
            default:
                break;
        }

    }

}
