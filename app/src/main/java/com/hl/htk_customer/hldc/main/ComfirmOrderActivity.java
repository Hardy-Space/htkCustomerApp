package com.hl.htk_customer.hldc.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.MineBean;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.hldc.bean.OrderedFoodBean;
import com.hl.htk_customer.hldc.bean.SeatBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.hldc.view.SeatPopupWindow;
import com.hl.htk_customer.model.AlreadySelectFoodData;
import com.hl.htk_customer.utils.MPHUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laughing on 2017/10/25.---确认订单界面
 */

public class ComfirmOrderActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = ComfirmOrderActivity.class.getSimpleName();
    private View headView;
    private ScrollView scrollView;
    private ImageView imgBack, imgHead;
    private TextView tvState, tvTitle, tvNickName, tvZhuoNo, tvMount, tvMoney, tvBeiZhu, tvTotalAmount, tvShiFu, tvMoney2;
    private LinearLayout linearLocation, linearConsume;
    private RelativeLayout relBeiZhu;
    private RecyclerView recyclerView;
    private TextView tvNoCaiTip, tvMount1, tvMoney1, tvPay;
    private View viewBtm;
    private OrderedAdapter orderedAdapter;
    String zhuoNo, nickName, imgUrl;
    String mType;
    private TextView tvDiscountAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comfirm_order_layout);
        zhuoNo = PreferencesUtils.getString(this, "zhuoNo");
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mType = this.getIntent().getStringExtra("type");
        initViews();
        setOnClickListener();
//        orderNumber = PreferencesUtils.getString(this, "orderNumber");
        //        if (!TextUtils.isEmpty(orderNumber)) {
        //            getComfirmOrderList();
        //        }

        //直接用本地数据生成
        initMenuInfo();


        getMineInfo();
        getPositionList();
    }

    /**
     * @author 马鹏昊
     * @desc 用本地数据初始化菜单信息
     */
    private void initMenuInfo() {

        if (mOrderedFoodBean != null) {
            mOrderedFoodBean.getProductList().clear();
        }

        for (int i = 0; i < AlreadySelectFoodData.getAllFoodList().size(); i++) {
            OrderFoodBean mBean = AlreadySelectFoodData.getAllFoodList().get(i);
            mOrderedFoodBean.getProductList().add(mBean);
        }
        calulateMoneyAndAmount();
        orderedAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        scrollView = findViewById(R.id.scrollview);
        headView = findViewById(R.id.headview);
        imgBack = headView.findViewById(R.id.img_lefticon);
        tvState = headView.findViewById(R.id.tv_leftstate);
        tvTitle = headView.findViewById(R.id.tv_common_title);
        tvTitle.setText(getResources().getString(R.string.comfirmorder));
        tvState.setText(getResources().getString(R.string.goback));
        imgBack.setBackgroundResource(R.drawable.icon_goback);
        imgHead = findViewById(R.id.img_headicon);
        tvNickName = findViewById(R.id.tv_nickname);
        tvZhuoNo = findViewById(R.id.tv_zhuohao);
        tvMount = findViewById(R.id.tv_foodamount);
        tvMoney = findViewById(R.id.tv_moneyamount);
        //        tvMoney2 = findViewById(R.id.tv_money);
        tvTotalAmount = findViewById(R.id.tv_totalamount);
//        tvShiFu = findViewById(R.id.tv_shifu);
        tvShiFu = findViewById(R.id.tv_orderAmount);
        linearConsume = findViewById(R.id.linear_consume);
        linearLocation = findViewById(R.id.linear_editlocation);
        relBeiZhu = findViewById(R.id.rel_beizhu);
        tvBeiZhu = findViewById(R.id.tv_beizhu_state);
        recyclerView = findViewById(R.id.goods_recycler);
        tvNoCaiTip = findViewById(R.id.tv_nocaitip);
        viewBtm = findViewById(R.id.view_btm);
        tvMount1 = viewBtm.findViewById(R.id.tv_number);
        tvMoney1 = viewBtm.findViewById(R.id.tv_moneytip);
        tvPay = viewBtm.findViewById(R.id.tv_bottompay);
        //        tvPay.setText(getResources().getString(R.string.comfirmorder_state));
        if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
            tvPay.setText(getResources().getString(R.string.comfirmorder_state));
        } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
            tvPay.setText(getResources().getString(R.string.comfirm_modify_order));
        }
        tvZhuoNo.setText("" + zhuoNo);

        tvDiscountAmount = findViewById(R.id.tv_discountAmount);

        orderedAdapter = new OrderedAdapter(ComfirmOrderActivity.this, mOrderedFoodBean.getProductList());
        recyclerView.setLayoutManager(new LinearLayoutManager(ComfirmOrderActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(orderedAdapter);
    }

    private void setOnClickListener() {
        imgBack.setOnClickListener(this);
        tvState.setOnClickListener(this);
        linearLocation.setOnClickListener(this);
        linearConsume.setOnClickListener(this);
        relBeiZhu.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }

    private void refreshUserUI() {
        tvZhuoNo.setText(zhuoNo + "");
        tvNickName.setText(nickName + "");
    }

    private void refreshHeadIcon(String url) {
        Glide.with(this).load(url).into(imgHead);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_lefticon:
            case R.id.tv_leftstate:
                ComfirmOrderActivity.this.finish();
                break;
            case R.id.linear_consume:
                //                Toast.makeText(ComfirmOrderActivity.this, "食品数量和费用", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_editlocation:
                initPopupWindow();
                break;
            case R.id.rel_beizhu:
                //                Toast.makeText(ComfirmOrderActivity.this, "修改备注", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_bottompay:
//                if (!TextUtils.isEmpty(orderNumber)) {
//                    if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
//                        commitOrderBtn();
//                    } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
//                        comfirmTiaoDan();
//                    }
//                } else {
//                    Toast.makeText(ComfirmOrderActivity.this, "无初始订单编号", Toast.LENGTH_SHORT).show();
//                }
                if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
                    commitOrderBtn();
                } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
                    comfirmTiaoDan();
                }
                break;
        }
    }

    private List<SeatBean> seatList = new ArrayList<>();
    private int shopId = 0;

    private void getPositionList() {
        shopId = PreferencesUtils.getInt(ComfirmOrderActivity.this, "shopId");

        final Dialog loading = MPHUtils.createLoadingDialog(this, "");
        loading.show();

        HttpHelper.getInstance().getSeatPosition(ComfirmOrderActivity.this, shopId, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                loading.dismiss();
                Log.d(TAG, "getPositionList=>" + responseString);
                int state = ToolUtils.getNetBackCode(responseString);
                String result = ToolUtils.getJsonParseResult(responseString);
                if (state == 100) {
                    JSONArray mArr;
                    try {
                        mArr = new JSONArray(result);
                        for (int i = 0; i < mArr.length(); i++) {
                            JSONObject obj = mArr.getJSONObject(i);
                            SeatBean seatBean = new SeatBean();
                            seatBean.setNumberSeat(obj.getInt("numberSeat"));
                            seatBean.setSeatName(obj.getString("seatName"));
                            seatBean.setShopId(obj.getInt("shopId"));
                            seatList.add(seatBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chairData.addAll(seatList);
                    if (chairData != null && chairData.size() > 0) {
                        zhuoNo = chairData.get(0).getSeatName();
                        tvZhuoNo.setText("" + zhuoNo);
                    }
                    PreferencesUtils.putString(ComfirmOrderActivity.this, "zhuoNo", "" + zhuoNo);
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                loading.dismiss();
                Toast.makeText(ComfirmOrderActivity.this, "获取座位列表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    int mount = 0;
    double money = 0;
    String productStr = "";

    private void calulateMoneyAndAmount() {
        for (int i = 0; i < mOrderedFoodBean.getProductList().size(); i++) {
            OrderFoodBean bean = mOrderedFoodBean.getProductList().get(i);
            if (bean != null) {
                mount += bean.getQuantity();
                money += bean.getPrice() * bean.getQuantity();
            }
            if (i < (mOrderedFoodBean.getProductList().size() - 1)) {
                productStr += bean.toString() + ",";
            } else {
                productStr += bean.toString();
            }
        }
        productStr = "[" + productStr + "]";
        refreshUI(); //ceshi
    }

    private void refreshUI() {
        tvMount.setText(mount + "份");
        tvMount1.setText(mount + "");
        tvMoney.setText("共" + money + "元");
        tvMoney1.setText(money + "元");
        //        tvMoney2.setText("共"+ money+"元");
        tvTotalAmount.setText("总价:" + money + "元");
//        tvShiFu.setText("实付:" + money + "元");
        tvShiFu.setText( money +"");
    }

    OrderedFoodBean mOrderedFoodBean = new OrderedFoodBean();

    //    private void getComfirmOrderList() {
    //        HttpHelper.getInstance().getOrderedGoodsList(ComfirmOrderActivity.this, orderNumber, new JsonHandler<String>() {
    //
    //            @Override
    //            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
    //                String result = ToolUtils.getJsonParseResult(responseString);
    //                if (!TextUtils.isEmpty(result)) {
    //                    JSONObject obj;
    //                    try {
    //                        obj = new JSONObject(result);
    //                        //                        mOrderedFoodBean.setIsCollect(obj.getInt("isCollect"));
    //                        mOrderedFoodBean.setOrderState(obj.getInt("orderState"));
    //                        mOrderedFoodBean.setOrderTime(obj.getString("orderTime"));
    //                        mOrderedFoodBean.setCommitTime(obj.getString("commitTime"));
    //                        //                        mOrderedFoodBean.setOrderNumber(obj.getString("orderNumber"));
    //                        String productList = obj.getString("productList");
    //                        JSONArray mArray = new JSONArray(productList);
    //                        for (int i = 0; i < mArray.length(); i++) {
    //                            OrderFoodBean mBean = new OrderFoodBean();
    //                            mBean.setCategoryId(mArray.getJSONObject(i).getInt("categoryId"));
    //                            mBean.setId(mArray.getJSONObject(i).getInt("id"));
    //                            mBean.setOrderId(mArray.getJSONObject(i).getInt("orderId"));
    //                            mBean.setPrice(mArray.getJSONObject(i).getDouble("price"));
    //                            mBean.setProductId(mArray.getJSONObject(i).getInt("productId"));
    //                            mBean.setProductName(mArray.getJSONObject(i).getString("productName"));
    //                            mBean.setQuantity(mArray.getJSONObject(i).getInt("quantity"));
    //                            mBean.setState(mArray.getJSONObject(i).getInt("state"));
    //                            mOrderedFoodBean.getProductList().add(mBean);
    //                        }
    //                        orderedAdapter = new OrderedAdapter(ComfirmOrderActivity.this, mOrderedFoodBean.getProductList());
    //                        recyclerView.setLayoutManager(new LinearLayoutManager(ComfirmOrderActivity.this, LinearLayoutManager.VERTICAL, false));
    //                        recyclerView.setAdapter(orderedAdapter);
    //                        if (mOrderedFoodBean != null && mOrderedFoodBean.getProductList() != null && mOrderedFoodBean.getProductList().size() > 0) {
    //                            recyclerView.scrollToPosition(0);
    //                        }
    //                        calulateMoneyAndAmount();
    //                        new Handler().postDelayed(new Runnable() {
    //                            @Override
    //                            public void run() {
    //                                scrollView.scrollTo(0, 60);
    //                            }
    //                        }, 200);
    //                    } catch (Exception e) {
    //                        e.printStackTrace();
    //                    }
    //                }
    //            }
    //
    //            @Override
    //            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
    //                Toast.makeText(ComfirmOrderActivity.this, "获取已点商品列表失败", Toast.LENGTH_SHORT).show();
    //            }
    //
    //            @Override
    //            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
    //                return null;
    //            }
    //        });
    //    }

    MineBean mineBean = new MineBean();

    private void getMineInfo() {

        final Dialog loading = MPHUtils.createLoadingDialog(this, "");
        loading.show();

        HttpHelper.getInstance().getMineInfo(ComfirmOrderActivity.this, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                loading.dismiss();
                Log.d(TAG, "onSuccess() === >>>" + responseString);
                int state = ToolUtils.getNetBackCode(responseString);
                String result = ToolUtils.getJsonParseResult(responseString);
                if (state == 100) {
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                        if (obj != null) {
                            mineBean.setNickName(obj.getString("nickName"));
                            mineBean.setUserPhone(obj.getString("userPhone"));
                            mineBean.setImgUrl(obj.getString("imgUrl"));
                            mineBean.setDiscountCouponCount(obj.getInt("discountCouponCount"));
                            mineBean.setIntegralVal(obj.getInt("integralVal"));
                            myHandler.sendEmptyMessage(REFRESH_COMMONUI);
                        } else {
                            Toast.makeText(ComfirmOrderActivity.this, "未成功获取个人信息", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ComfirmOrderActivity.this, "Loading Failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                loading.dismiss();
                Log.d(TAG, "getMineInfo()==onFailure==>>" + responseString);
                Toast.makeText(ComfirmOrderActivity.this, "Loading Failed...", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private static final int REFRESH_COMMONUI = 1;
    private static final int REFRESH_SEATLIST = 2;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMMONUI:
                    nickName = mineBean.getNickName();
                    //                    zhuoNo = "moren";
                    refreshUserUI();
                    refreshHeadIcon(mineBean.getImgUrl());
                    break;
                case REFRESH_SEATLIST:
                    initPopupWindow();
                    break;
            }
        }
    };


    /**
     * popup窗口
     */
    private SeatPopupWindow typeSelectPopup;
    /**
     * 数据
     */
    private List<SeatBean> chairData = new ArrayList<>();

    private void initPopupWindow() {
        if (chairData == null || chairData.size() <= 0) {
            Toast.makeText(ComfirmOrderActivity.this, "获取座位列表失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //            Toast.makeText(ComfirmOrderActivity.this,"chairData.size()==>>>"+chairData.size(),Toast.LENGTH_SHORT).show();
        }
        typeSelectPopup = new SeatPopupWindow(ComfirmOrderActivity.this, this, chairData);
        typeSelectPopup.showPopupWindow(linearLocation);
    }


    /**
     * String remark(备注)，double discountAmount(优惠金额)，
     * String seatName(座位号名字)，double orderAmount(订单金额)，
     * String orderNumber(订单号)，int discountCouponId(优惠券id)
     */
    String remark;
    double discountAmount = 0;
    String seatName = "A区1号";
    double orderAmount = 0;
    String orderNumber;
    int discountCouponId = 0;

    /**
     * @desc 确认下单接口
     * @author 马鹏昊
     */
    private void commitOrderBtn() {
        seatName = PreferencesUtils.getString(ComfirmOrderActivity.this, "zhuoNo", null);
        discountAmount = Double.parseDouble(tvDiscountAmount.getText().toString());
        remark = tvBeiZhu.getText().toString();
        orderAmount =  Double.parseDouble(tvShiFu.getText().toString());
        String jsonStr = createJsonStr();

        final Dialog loading = MPHUtils.createLoadingDialog(this, "");
        loading.show();

        HttpHelper.getInstance().commitOrderBtn(ComfirmOrderActivity.this,PreferencesUtils.getInt(this,"shopId")+"", remark, discountAmount,
                seatName, orderAmount, jsonStr, discountCouponId, new JsonHandler<String>() {

                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                        loading.dismiss();
                        Log.d(TAG, "commitOrderBtn()==onSuccess()==>>>" + responseString);
                        int state = ToolUtils.getNetBackCode(responseString);
                        if (state == 100) {
                            String result = ToolUtils.getJsonParseResult(responseString);
                            PreferencesUtils.putString(ComfirmOrderActivity.this, "orderNumber", result);
                            Toast.makeText(ComfirmOrderActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ComfirmOrderActivity.this,DCMainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ComfirmOrderActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                        loading.dismiss();
                        Log.d(TAG, "commitOrderBtn()==onFailure()==>>>" + responseString);
                        Toast.makeText(ComfirmOrderActivity.this, "接口出错", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return null;
                    }
                });
    }

    private String createJsonStr() {
        StringBuffer result = new StringBuffer();
        result.append("[");
        for (int i = 0; i < mOrderedFoodBean.getProductList().size(); i++) {
            OrderFoodBean bean = mOrderedFoodBean.getProductList().get(i);
            result.append(bean.toString()+",");
        }
        result.deleteCharAt(result.length()-1);
        result.append("]");
        return result.toString();
    }

    //    /**
    //     * 八、确认下单接口
    //     * String remark(备注)，double discountAmount(优惠金额)，
    //     * String seatName(座位号名字)，double orderAmount(订单金额)，
    //     * String orderNumber(订单号)，int discountCouponId(优惠券id)
    //     */
    //    private void commitOrderBtn() {
    //        seatName = PreferencesUtils.getString(ComfirmOrderActivity.this, "zhuoNo", null);
    //        HttpHelper.getInstance().commitOrderBtn(ComfirmOrderActivity.this, remark, discountAmount,
    //                seatName, orderAmount, orderNumber, discountCouponId, new JsonHandler<String>() {
    //
    //                    @Override
    //                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
    //                        Log.d(TAG, "commitOrderBtn()==onSuccess()==>>>" + responseString);
    //                        int state = ToolUtils.getNetBackCode(responseString);
    //                        if (state == 100) {
    //                            Toast.makeText(ComfirmOrderActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
    //                        } else {
    //                            Toast.makeText(ComfirmOrderActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
    //                        }
    //                    }
    //
    //                    @Override
    //                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
    //                        Log.d(TAG, "commitOrderBtn()==onFailure()==>>>" + responseString);
    //                        Toast.makeText(ComfirmOrderActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
    //                    }
    //
    //                    @Override
    //                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
    //                        return null;
    //                    }
    //                });
    //    }

//    private void comfirmTiaoDan() {
//        HttpHelper.getInstance().comfirmTiaoDan(ComfirmOrderActivity.this, orderNumber, productStr, new JsonHandler<String>() {
//
//            @Override
//            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
//                int state = ToolUtils.getNetBackCode(responseString);
//                if (state == 100) {
//                    Toast.makeText(ComfirmOrderActivity.this, "发送调单请求成功，请等待商家确认", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ComfirmOrderActivity.this, "发送调单请求失败", Toast.LENGTH_SHORT).show();
//                }
//                finish();
//            }
//
//            @Override
//            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
//                Log.d(TAG, "commitOrderBtn()==onFailure()==>>>" + responseString);
//                Toast.makeText(ComfirmOrderActivity.this, "接口请求失败", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return null;
//            }
//        });
//    }

    private void comfirmTiaoDan() {
        orderNumber = PreferencesUtils.getString(this, "orderNumber");


        final Dialog loading = MPHUtils.createLoadingDialog(this, "");
        loading.show();

        //因为后台推送会有时间延迟导致调用超时，所以不管回调成功还是失败都提示成功即可（后台能够成功保存数据）
        HttpHelper.getInstance().comfirmTiaoDan(ComfirmOrderActivity.this, orderNumber,PreferencesUtils.getInt(this,"shopId")+"", productStr, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                loading.dismiss();
                int state = ToolUtils.getNetBackCode(responseString);
                if (state == 100) {
                    Toast.makeText(ComfirmOrderActivity.this, "发送调单请求成功，请等待商家确认", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ComfirmOrderActivity.this,DCMainActivity.class));
                    finish();
                } else {
                    Toast.makeText(ComfirmOrderActivity.this, "商家未接单，无法调单", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                loading.dismiss();
                Log.d(TAG, "commitOrderBtn()==onFailure()==>>>" + responseString);
//                Toast.makeText(ComfirmOrderActivity.this, "接口请求失败", Toast.LENGTH_SHORT).show();
                Toast.makeText(ComfirmOrderActivity.this, "发送调单请求成功，请等待商家确认", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ComfirmOrderActivity.this,DCMainActivity.class));
                finish();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        typeSelectPopup.showPopupWindow(linearLocation);
        zhuoNo = chairData.get(position).getSeatName();
        tvZhuoNo.setText(zhuoNo + "");
        PreferencesUtils.putString(this, "zhuoNo", zhuoNo);
    }
}
