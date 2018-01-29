package com.hl.htk_customer.hldc.main;

import android.app.Activity;
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
import com.hl.htk_customer.hldc.bean.GoodBean;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.hldc.bean.YiDianFoodParentBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;

import org.json.JSONArray;

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
        if (!TextUtils.isEmpty(orderNumber)) {
            getOrderedGoodsList();
        }
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
        if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
            tvComfirmOrder.setText("确认下单");
        } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
            tvComfirmOrder.setText("确认调单");
        }

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
                startActivityForResult(mIntent, 1);
                break;
            case R.id.tv_bottompay:
                Log.d(TAG, "" + productStr);
                if (foodMount <= 0) {
                    Toast.makeText(OrderedListActivity.this, "食品数量不符合要求", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(mType) && "xiadan".equals(mType)) {
                        xiaDanBtn();
                    } else if (!TextUtils.isEmpty(mType) && "tiaodan".equals(mType)) {
                        tiaoDanBtn();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            GoodBean goodBean = (GoodBean) data.getSerializableExtra("food");
            Log.d(TAG, "goodId == >>>>" + goodBean.getId());
            OrderFoodBean orderFoodBean = new OrderFoodBean();
            orderFoodBean.setId(goodBean.getId());
            orderFoodBean.setImgUrl(goodBean.getImgUrl() + "");
            orderFoodBean.setQuantity(1);
            orderFoodBean.setPrice(goodBean.getPrice());
            orderFoodBean.setCategoryName(goodBean.getCategoryName());
            orderFoodBean.setProductName(goodBean.getProductName());
            orderFoodBean.setCategoryId(goodBean.getCategoryId());
            if (mBean != null && mBean.getOrderProductList() != null) {
                mBean.getOrderProductList().add(orderFoodBean);
                calulateMoneyAndAmount();
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private void xiaDanBtn() {
        HttpHelper.getInstance().commitOrderBtn(OrderedListActivity.this, orderNumber, productStr, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                int state = ToolUtils.getNetBackCode(responseString);
                if (state == 100) {
                    Toast.makeText(OrderedListActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                    Intent mIntent1 = new Intent(OrderedListActivity.this, ComfirmOrderActivity.class);
                    mIntent1.putExtra("type", "xiadan");
                    startActivity(mIntent1);
                    OrderedListActivity.this.finish();
                } else {
                    Toast.makeText(OrderedListActivity.this, "下单失败" + responseString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(OrderedListActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void tiaoDanBtn() {
        HttpHelper.getInstance().tiaoDan(this, orderNumber, productStr, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                int state = ToolUtils.getNetBackCode(responseString);
                if (state == 100) {
                    Toast.makeText(OrderedListActivity.this, "修改订单信息成功", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(OrderedListActivity.this, "调单成功", Toast.LENGTH_SHORT).show();
                    Intent mIntent1 = new Intent(OrderedListActivity.this, ComfirmOrderActivity.class);
                    mIntent1.putExtra("type", "tiaodan");
                    startActivity(mIntent1);
                    OrderedListActivity.this.finish();
                } else {
                    Toast.makeText(OrderedListActivity.this, "修改订单信息失败" + responseString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(OrderedListActivity.this, "接口请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private int foodMount = 0;

    public void showModify(final int x, int y) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "quantity=>" + mBean.getOrderProductList().get(x).getQuantity() + "<<==productName==>>" +
                        mBean.getOrderProductList().get(x).getProductName());
                Log.d(TAG, "getOrderProductList.SIZE()" + mBean.getOrderProductList().size());
                foodMount = 0;
                for (int i = 0; i < mBean.getOrderProductList().size(); i++) {
                    Log.d(TAG, "getOrderProductList.QUANTITY" + mBean.getOrderProductList().get(i).getQuantity());
                    foodMount += mBean.getOrderProductList().get(i).getQuantity();
                    Log.d(TAG, "foodMount==>>>" + foodMount);
                    if (mBean.getOrderProductList().get(i).getQuantity() == 0) {
                        mBean.getOrderProductList().remove(i);
                    }
                }
                mAdapter.notifyDataSetChanged();
                calulateMoneyAndAmount();

            }
        }, 100);
    }

    YiDianFoodParentBean mBean = new YiDianFoodParentBean();

    private void getOrderedGoodsList() {
        HttpHelper.getInstance().getOrderedGoodsPage(this, orderNumber, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                Log.d(TAG, "getOrderedGoodsList() ==onSuccess" + responseString);
                int result = ToolUtils.getNetBackCode(responseString);
                String result1 = ToolUtils.getJsonParseResult(responseString);
                Log.d(TAG, "getOrderedGoodsList() ==data==>>" + result1);
                if (result == 100) {
                    if (!TextUtils.isEmpty(result1)) {
                        //                        JSONArray obj;
                        JSONArray mArray;
                        try {
                            //                            obj = new JSONArray(result1);
                            //                            mBean.setCategoryId(obj.getInt("categoryId")+"");
                            //                            mBean.setCategoryName(obj.getString("categoryName"));
                            //                            String productList = obj.getString("orderProductList");
                            mArray = new JSONArray(result1);
                            if (mArray != null && mArray.length() > 0) {
                                for (int i = 0; i < mArray.length(); i++) {
                                    OrderFoodBean mBean1 = new OrderFoodBean();
                                    mBean1.setCategoryId(mArray.getJSONObject(i).getInt("categoryId"));
                                    mBean1.setId(mArray.getJSONObject(i).getInt("id"));
                                    mBean1.setOrderId(mArray.getJSONObject(i).getInt("orderId"));
                                    mBean1.setPrice(mArray.getJSONObject(i).getDouble("price"));
                                    mBean1.setProductId(mArray.getJSONObject(i).getInt("productId"));
                                    mBean1.setProductName(mArray.getJSONObject(i).getString("productName"));
                                    mBean1.setQuantity(mArray.getJSONObject(i).getInt("quantity"));
                                    mBean1.setState(mArray.getJSONObject(i).getInt("state"));
                                    mBean1.setImgUrl(mArray.getJSONObject(i).getString("imgUrl"));
                                    mBean1.setCategoryName(mArray.getJSONObject(i).getString("categoryName"));
                                    mBean.getOrderProductList().add(mBean1);
                                }
                                calulateMoneyAndAmount();
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderedListActivity.this, LinearLayoutManager.VERTICAL, false));
                                mAdapter = new DivideGroupAdapter(OrderedListActivity.this, mBean.getOrderProductList());
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(OrderedListActivity.this, "您尚未点餐", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(OrderedListActivity.this, "获取已点列表失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                Log.d(TAG, "getOrderedGoodsList()==onFailure() ==onSuccess" + responseString);
                Toast.makeText(OrderedListActivity.this, "获取已点列表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

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
    private void commitOrderBtn() {
        HttpHelper.getInstance().commitOrderBtn(OrderedListActivity.this, remark, discountAmount,
                seatName, orderAmount, orderNumber, discountCouponId, new JsonHandler<String>() {

                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                        Log.d(TAG, "commitOrderBtn()==onSuccess()==>>>" + responseString);
                        int state = ToolUtils.getNetBackCode(responseString);
                        if (state == 100) {
                            Toast.makeText(OrderedListActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OrderedListActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                        Log.d(TAG, "commitOrderBtn()==onFailure()==>>>" + responseString);
                        Toast.makeText(OrderedListActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return null;
                    }
                });
    }

}
