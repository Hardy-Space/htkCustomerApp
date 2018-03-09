package com.hl.htk_customer.hldc.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.hldc.bean.YiDianFoodParentBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.model.AlreadySelectFoodData;
import com.hl.htk_customer.utils.MPHUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by asus on 2017/10/25.---已点列表
 */

public class OrderedListActivity extends Activity implements View.OnClickListener {
    private static final String TAG = OrderedListActivity.class.getSimpleName();
    private View viewBtm, viewTop;
    private TextView tvLeftState, tvTitle, tvJiaCai, tvComfirmOrder, tvFoodTypeMount, tvFoodMoney;
    private ImageView imgBack;
    private RecyclerView recyclerView;
    private DivideGroupAdapter mAdapter;
    public static OrderedListActivity mInstance;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        this.setContentView(R.layout.orderedlist_layout);
        mType = this.getIntent().getStringExtra("type");
        initViews();
        setOnClickListener();
        orderNumber = PreferencesUtils.getString(this, "orderNumber");
        //        if (!TextUtils.isEmpty(orderNumber)) {
        //            getOrderedGoodsList();
        //        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderedGoodsList();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycle_foodlist);
        viewTop = findViewById(R.id.headview);
        imgBack = viewTop.findViewById(R.id.img_lefticon);
        tvLeftState = viewTop.findViewById(R.id.tv_leftstate);
        tvTitle = viewTop.findViewById(R.id.tv_common_title);
        viewBtm = findViewById(R.id.view_btm);
        tvJiaCai = viewBtm.findViewById(R.id.tv_jiacai);
        tvComfirmOrder = viewBtm.findViewById(R.id.tv_bottompay);
        tvFoodTypeMount = viewBtm.findViewById(R.id.tv_foodtypemount);
        tvFoodMoney = viewBtm.findViewById(R.id.tv_moneytip);
        imgBack.setBackgroundResource(R.drawable.icon_goback);
        tvLeftState.setText(getResources().getString(R.string.goback));
        tvTitle.setText(getResources().getString(R.string.orderedlist));
        //        if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
        //            tvComfirmOrder.setText("确认");
        //        } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
        //            tvComfirmOrder.setText("确认");
        //        }
        tvComfirmOrder.setText("确认");

        recyclerView.setLayoutManager(new LinearLayoutManager(OrderedListActivity.this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DivideGroupAdapter(OrderedListActivity.this, mBean.getOrderProductList());
        recyclerView.setAdapter(mAdapter);

    }

    private void setOnClickListener() {
        imgBack.setOnClickListener(this);
        tvLeftState.setOnClickListener(this);
        tvJiaCai.setOnClickListener(this);
        tvComfirmOrder.setOnClickListener(this);
    }

    private void refreshUI(int mount, double price) {
        tvFoodTypeMount.setText(mount + "份");
        tvFoodMoney.setText("共" + price + "元");
    }

    int mount = 0;
    double price = 0;
    String productStr;

    private void calulateMoneyAndAmount() {
        mount = 0;
        price = 0;
        productStr = "";
        for (int i = 0; i < mBean.getOrderProductList().size(); i++) {
            OrderFoodBean bean = mBean.getOrderProductList().get(i);
            mount += bean.getQuantity();
            price += bean.getPrice() * bean.getQuantity();
            if (i < (mBean.getOrderProductList().size() - 1)) {
                productStr += bean.toString() + ",";
            } else {
                productStr += bean.toString() + "";
            }
        }
        productStr = "[" + productStr + "]";
        refreshUI(mount, price);
        foodMount = mount;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_lefticon:
            case R.id.tv_leftstate:
                OrderedListActivity.this.finish();
                break;
            case R.id.tv_jiacai:
                Intent mIntent = new Intent(OrderedListActivity.this, DCMainActivity.class);
                mIntent.putExtra("jiacai", "jiacai");
                //                startActivityForResult(mIntent, 1);
                startActivity(mIntent);
                break;
            case R.id.tv_bottompay:
                Log.d(TAG, "" + productStr);
                if (foodMount <= 0) {
                    Toast.makeText(OrderedListActivity.this, "食品数量不符合要求", Toast.LENGTH_SHORT).show();
                } else {
//                    if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
//                        xiaDanBtn();
//                    } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
//                        tiaoDanBtn();
//                    }
                    toConfirm();
                }
                break;
        }
    }


    /**
     * @author 马鹏昊
     * @desc 打开确认订单页
     */
    private void toConfirm() {
        Intent intent = new Intent(OrderedListActivity.this, ComfirmOrderActivity.class);
        intent.putExtra("type", mType);
        startActivity(intent);
    }

    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        if (requestCode == 1 && data != null) {
    //            GoodBean goodBean = (GoodBean) data.getSerializableExtra("food");
    //            Log.d(TAG, "goodId == >>>>" + goodBean.getId());
    //            OrderFoodBean orderFoodBean = new OrderFoodBean();
    //            orderFoodBean.setId(goodBean.getId());
    //            orderFoodBean.setImgUrl(goodBean.getImgUrl() + "");
    //            orderFoodBean.setQuantity(1);
    //            orderFoodBean.setPrice(goodBean.getPrice());
    //            orderFoodBean.setCategoryName(goodBean.getCategoryName());
    //            orderFoodBean.setProductName(goodBean.getProductName());
    //            orderFoodBean.setCategoryId(goodBean.getCategoryId());
    //            if (mBean != null && mBean.getOrderProductList() != null) {
    //                mBean.getOrderProductList().add(orderFoodBean);
    //                calulateMoneyAndAmount();
    //            }
    //            mAdapter.notifyDataSetChanged();
    //        }
    //    }

//    private void xiaDanBtn() {
//        HttpHelper.getInstance().commitOrderBtn(OrderedListActivity.this, orderNumber, productStr, new JsonHandler<String>() {
//
//            @Override
//            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
//                int state = ToolUtils.getNetBackCode(responseString);
//                if (state == 100) {
//                    Toast.makeText(OrderedListActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
//                    Intent mIntent1 = new Intent(OrderedListActivity.this, ComfirmOrderActivity.class);
//                    mIntent1.putExtra("type", "xiadan");
//                    startActivity(mIntent1);
//                    OrderedListActivity.this.finish();
//                } else {
//                    Toast.makeText(OrderedListActivity.this, "下单失败" + responseString, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
//                Toast.makeText(OrderedListActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return null;
//            }
//        });
//    }
//
//    private void tiaoDanBtn() {
//        HttpHelper.getInstance().tiaoDan(this, orderNumber, productStr, new JsonHandler<String>() {
//
//            @Override
//            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
//                int state = ToolUtils.getNetBackCode(responseString);
//                if (state == 100) {
//                    Toast.makeText(OrderedListActivity.this, "修改订单信息成功", Toast.LENGTH_SHORT).show();
//                    //                    Toast.makeText(OrderedListActivity.this, "调单成功", Toast.LENGTH_SHORT).show();
//                    Intent mIntent1 = new Intent(OrderedListActivity.this, ComfirmOrderActivity.class);
//                    mIntent1.putExtra("type", "tiaodan");
//                    startActivity(mIntent1);
//                    OrderedListActivity.this.finish();
//                } else {
//                    Toast.makeText(OrderedListActivity.this, "修改订单信息失败" + responseString, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
//                Toast.makeText(OrderedListActivity.this, "接口请求失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return null;
//            }
//        });
//    }

    private int foodMount = 0;

    public void showModify(/*final int x, int y*/) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                foodMount = 0;
                for (int i = 0; i < mBean.getOrderProductList().size(); i++) {
                    Log.d(TAG, "getOrderProductList.QUANTITY" + mBean.getOrderProductList().get(i).getQuantity());
                    foodMount += mBean.getOrderProductList().get(i).getQuantity();
                    Log.d(TAG, "foodMount==>>>" + foodMount);
                    //                    if (mBean.getOrderProductList().get(i).getQuantity() == 0) {
                    //                        mBean.getOrderProductList().remove(i);
                    //                    }
                }
                mAdapter.notifyDataSetChanged();
                calulateMoneyAndAmount();

            }
        }, 0);
    }

    //此次初始化已点数据
    YiDianFoodParentBean mBean = new YiDianFoodParentBean();


    /**
     * @author 马鹏昊
     * @desc 根据本地保存数据初始化已点列表
     */
    private void getOrderedGoodsList() {


        if (mBean!=null&&mBean.getOrderProductList()!=null){

            //先把远程订单的数据填充进去
            addWebData();
        }

    }

    /**
     * @param foodBean 待加入的菜品
     * @author 马鹏昊
     * @desc 保存点餐信息
     */
    public boolean checkIfExist(OrderFoodBean foodBean) {
        Iterator<OrderFoodBean> iter = AlreadySelectFoodData.getAllFoodList().iterator();
        while (iter.hasNext()) {
            OrderFoodBean b = iter.next();
            if (foodBean.getCategoryId() == b.getCategoryId() && foodBean.getId() == b.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author 马鹏昊
     * @desc 填充远程订单菜品数据到本地集合
     */
    private void addWebData() {
        final Dialog loading = MPHUtils.createLoadingDialog(this, "");
        loading.show();

        HttpHelper.getInstance().getOrderDetail(this, orderNumber, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString, Object response) {
                loading.dismiss();
                Log.d(TAG, "onSuccess=>" + responseString);
                JSONObject jb = null;
                try {
                    jb = new JSONObject(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int code = -1;
                if (jb == null) {
                    Toast.makeText(OrderedListActivity.this, "Json解析失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        code = jb.getInt("code");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (code == 100) {
                    String result = ToolUtils.getJsonParseResult(responseString);

                    if (!TextUtils.isEmpty(result)) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(result);
                            String seatName = obj.getString("seatName");
                            PreferencesUtils.putString(OrderedListActivity.this,"seatName",seatName);
                            String productList = obj.getString("productList");
                            JSONArray mArray = new JSONArray(productList);
                            for (int i = 0; i < mArray.length(); i++) {
                                OrderFoodBean bean = new OrderFoodBean();
                                bean.setCategoryId(mArray.getJSONObject(i).getInt("categoryId"));
                                bean.setCategoryName(mArray.getJSONObject(i).getString("categoryName"));
                                bean.setId(mArray.getJSONObject(i).getInt("id"));
                                bean.setOrderId(mArray.getJSONObject(i).getInt("orderId"));
                                bean.setPrice(mArray.getJSONObject(i).getDouble("price"));
                                bean.setProductId(mArray.getJSONObject(i).getInt("productId"));
                                bean.setProductName(mArray.getJSONObject(i).getString("productName"));
                                bean.setQuantity(mArray.getJSONObject(i).getInt("quantity"));
                                bean.setPrice(mArray.getJSONObject(i).getDouble("price"));
                                bean.setImgUrl(mArray.getJSONObject(i).getString("imgUrl"));
                                bean.setState(mArray.getJSONObject(i).getInt("state"));
                                if (!checkIfExist(bean)) {
                                    AlreadySelectFoodData.addBean(bean);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                mBean.getOrderProductList().clear();
                Iterator<OrderFoodBean> iter = AlreadySelectFoodData.getAllFoodList().iterator();
                while (iter.hasNext()) {
                    OrderFoodBean b = iter.next();
                    mBean.getOrderProductList().add(b);
                }
                calulateMoneyAndAmount();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                loading.dismiss();
                Log.d(TAG, "" + responseString);
                Toast.makeText(OrderedListActivity.this, "获取已生成订单的菜品信息接口出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //    private void getOrderedGoodsList() {
    //        HttpHelper.getInstance().getOrderedGoodsPage(this, orderNumber, new JsonHandler<String>() {
    //
    //            @Override
    //            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
    //                Log.d(TAG, "getOrderedGoodsList() ==onSuccess" + responseString);
    //                int result = ToolUtils.getNetBackCode(responseString);
    //                String result1 = ToolUtils.getJsonParseResult(responseString);
    //                Log.d(TAG, "getOrderedGoodsList() ==data==>>" + result1);
    //                if (result == 100) {
    //                    if (!TextUtils.isEmpty(result1)) {
    //                        //                        JSONArray obj;
    //                        JSONArray mArray;
    //                        try {
    //                            //                            obj = new JSONArray(result1);
    //                            //                            mBean.setCategoryId(obj.getInt("categoryId")+"");
    //                            //                            mBean.setCategoryName(obj.getString("categoryName"));
    //                            //                            String productList = obj.getString("orderProductList");
    //                            mArray = new JSONArray(result1);
    //                            if (mArray != null && mArray.length() > 0) {
    //                                for (int i = 0; i < mArray.length(); i++) {
    //                                    OrderFoodBean mBean1 = new OrderFoodBean();
    //                                    mBean1.setCategoryId(mArray.getJSONObject(i).getInt("categoryId"));
    //                                    mBean1.setId(mArray.getJSONObject(i).getInt("id"));
    //                                    mBean1.setOrderId(mArray.getJSONObject(i).getInt("orderId"));
    //                                    mBean1.setPrice(mArray.getJSONObject(i).getDouble("price"));
    //                                    mBean1.setProductId(mArray.getJSONObject(i).getInt("productId"));
    //                                    mBean1.setProductName(mArray.getJSONObject(i).getString("productName"));
    //                                    mBean1.setQuantity(mArray.getJSONObject(i).getInt("quantity"));
    //                                    mBean1.setState(mArray.getJSONObject(i).getInt("state"));
    //                                    mBean1.setImgUrl(mArray.getJSONObject(i).getString("imgUrl"));
    //                                    mBean1.setCategoryName(mArray.getJSONObject(i).getString("categoryName"));
    //                                    mBean.getOrderProductList().add(mBean1);
    //                                }
    //                                calulateMoneyAndAmount();
    //                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderedListActivity.this, LinearLayoutManager.VERTICAL, false));
    //                                mAdapter = new DivideGroupAdapter(OrderedListActivity.this, mBean.getOrderProductList());
    //                                recyclerView.setAdapter(mAdapter);
    //                            } else {
    //                                Toast.makeText(OrderedListActivity.this, "您尚未点餐", Toast.LENGTH_SHORT).show();
    //                            }
    //                        } catch (Exception e) {
    //                            e.printStackTrace();
    //                        }
    //                    }
    //                } else {
    //                    Toast.makeText(OrderedListActivity.this, "获取已点列表失败", Toast.LENGTH_SHORT).show();
    //                }
    //            }
    //
    //            @Override
    //            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
    //                Log.d(TAG, "getOrderedGoodsList()==onFailure() ==onSuccess" + responseString);
    //                Toast.makeText(OrderedListActivity.this, "获取已点列表失败", Toast.LENGTH_SHORT).show();
    //            }
    //
    //            @Override
    //            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
    //                return null;
    //            }
    //        });
    //    }

    String remark;
    double discountAmount = 0;
    String seatName;
    double orderAmount = 0;
    String orderNumber;
    int discountCouponId = 0;

    /**
     * 八、确认下单接口
     * String remark(备注)，double discountAmount(优惠金额)，
     * String seatName(座位号名字)，double orderAmount(订单金额)，
     * String orderNumber(订单号)，int discountCouponId(优惠券id)
     */
//    private void commitOrderBtn() {
//        HttpHelper.getInstance().commitOrderBtn(OrderedListActivity.this, remark, discountAmount,
//                seatName, orderAmount, orderNumber, discountCouponId, new JsonHandler<String>() {
//
//                    @Override
//                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
//                        Log.d(TAG, "commitOrderBtn()==onSuccess()==>>>" + responseString);
//                        int state = ToolUtils.getNetBackCode(responseString);
//                        if (state == 100) {
//                            Toast.makeText(OrderedListActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(OrderedListActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
//                        Log.d(TAG, "commitOrderBtn()==onFailure()==>>>" + responseString);
//                        Toast.makeText(OrderedListActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                        return null;
//                    }
//                });
//    }

}
