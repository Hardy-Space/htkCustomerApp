package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.OrderItemDetailAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.OneLineDialog;
import com.hl.htk_customer.model.DefaultAddress;
import com.hl.htk_customer.model.ShopInfoModel;
import com.hl.htk_customer.model.ShopProduct;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.Arith;
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
    //选择的优惠券ID
    public static final int NON_COUPON = -1;
    private int mCouponId = NON_COUPON;


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
        tvDaizhifu.setText(String.format("待支付￥ %1$.2f", getTotalPrice()));
        tvPrice.setText(String.format("待支付￥ %1$.2f", getTotalPrice()));
    }

    /**
     * 计算商品总价
     *
     * @return 总价
     */
    public double getTotalPrice() {
        double r = Arith.add(goodsPrice, mDeliveryFee);
        return Arith.sub(r, mVouchers);
    }


    private void initAddress() {

        if (app.getDefaultAddress().getAddressId() == -1) {
            tvUserInfo.setText("请选择收货地址");
            return;
        }

        /**
         * @author 马鹏昊
         * @desc 判断当前用户是否是上次保存地址信息的用户, 如果不是不要初始化地址信息
         * @date 2018-4-24
         */
        UserInfoManager nowUser = new UserInfoManager(this);
        String nowToken = nowUser.getToken();
        String lastToken = app.getDefaultAddress().getToken();
        if (nowToken.equals(lastToken)) {
            String sex;
            if (app.getDefaultAddress().getSex() == 0) {
                sex = "女士";
            } else {
                sex = "先生";
            }

            tvUserInfo.setText(app.getDefaultAddress().getUserName() + sex + "  " + app.getDefaultAddress().getPhoneNumber());
            tvAddress.setText(app.getDefaultAddress().getLocation() + app.getDefaultAddress().getAddress());
        }

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CouponActivity.REQUESTCODE && resultCode == CouponActivity.RESULTCODE) {
            mCouponId = data.getIntExtra("id", 0);
            mVouchers = data.getDoubleExtra("amount", 0);
            mTvVouchersNum.setText(String.format(getString(R.string.join_amount_cut), mVouchers));
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

                //                if (app.getDefaultAddress().getAddressId() == -1) {
                //                    showMessage("请选择收货地址");
                //                    return;
                //                }
                /**
                 * @author 马鹏昊
                 * @desc 之前是通过判断保存的地址信息有没有，现在改为判断当前页面的地址信息有没有
                 * @date 2018-4-24
                 */
                String showInfo = tvUserInfo.getText().toString();
                if (TextUtils.isEmpty(showInfo)) {
                    showMessage("请选择收货地址");
                    return;
                }

                if (payWayTag == 0) {
                    DefaultAddress defaultAddress = app.getDefaultAddress();
                    double p = getTotalPrice();
                    /**
                     *  @author 马鹏昊
                     *  @desc 这个判断是要防止价格为0的时候接口会成功生成订单，这意味着优惠券已经减去了，但是回调之后支付框架吊起支付页的时候会提示无法获取订单信息（支付价格不能为0）,
                     *  所以在这里加上限制
                     */
                    if (p <= 0) {
                        Toast.makeText(mContext, "支付价格不能为0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String price = String.valueOf(p);
                    PayStyle pay = new AliPayWaiMai(ConfirmOrderActivity.this, String.valueOf(mCouponId), price, String.valueOf(shopId), products,
                            defaultAddress.getLocation() + defaultAddress.getAddress(),
                            String.valueOf(defaultAddress.getPhoneNumber()),
                            defaultAddress.getUserName(),
                            MyApplication.getmAMapLocation().getLongitude(),
                            MyApplication.getmAMapLocation().getLatitude(),
                            defaultAddress.getSex(), tvSubmit,etMark.getText().toString());
                    pay.pay();
                } else {
                    DefaultAddress defaultAddress = app.getDefaultAddress();
                    double p = getTotalPrice();
                    /**
                     *  @author 马鹏昊
                     *  @desc 这个判断是要防止价格为0的时候接口会成功生成订单，这意味着优惠券已经减去了，但是回调之后支付框架吊起支付页的时候会提示无法获取订单信息（支付价格不能为0）,
                     *  所以在这里加上限制
                     */
                    if (p <= 0) {
                        Toast.makeText(mContext, "支付价格不能为0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String price = String.valueOf(p);
                    PayStyle pay = new WXPayWaiMai(ConfirmOrderActivity.this, String.valueOf(mCouponId), price, String.valueOf(shopId), products,
                            defaultAddress.getLocation() + defaultAddress.getAddress(),
                            String.valueOf(defaultAddress.getPhoneNumber()),
                            defaultAddress.getUserName(),
                            MyApplication.getmAMapLocation().getLongitude(),
                            MyApplication.getmAMapLocation().getLatitude(),
                            defaultAddress.getSex(), tvSubmit ,etMark.getText().toString());
                    pay.pay();
                }
                break;
            case R.id.rl_payWay:

                /**
                 * @author 马鹏昊
                 * @desc 微信支付转账暂时不能自动转到银行卡，所以先屏蔽
                 */
                //                payWayDialog.setData(list);
                //                payWayDialog.setShowOne("支付宝");
                //                payWayDialog.show();
                break;
            case R.id.confirm_order_Vouchers_num:
                CouponActivity.launch(this, 1, shopId, goodsPrice);
                break;
            default:
                break;
        }

    }

}
