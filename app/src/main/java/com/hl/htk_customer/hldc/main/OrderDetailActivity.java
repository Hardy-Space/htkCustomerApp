package com.hl.htk_customer.hldc.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.hldc.bean.OrderedFoodBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by asus on 2017/10/27.---订单详情页面 -- 已弃用
 */

public class OrderDetailActivity extends Activity implements View.OnClickListener{

    private View mView, viewsp1, viewsp2;
    private TextView tvTitle,tvLeftState,tvPaid, tvCook, tvFinished;
    private ImageView imgBack;
//    private TextView tvXiaDan, tvTiaoDan, tvCuiDan;
//    private ImageView imgXiaDan, imgTiaoDan, imgCuiDan;
    private RecyclerView recyclerView;
    private OrderedAdapter orderedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.orderdetail_layout);
        initViews();
        orderedAdapter = new OrderedAdapter(this,mOrderedFoodBean.getProductList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(orderedAdapter);
        if(mOrderedFoodBean != null && mOrderedFoodBean.getProductList() != null && mOrderedFoodBean.getProductList().size() > 0){
            recyclerView.scrollToPosition(0);
        }
        getOrderDetail();
    }

    private void initViews(){
        mView = findViewById(R.id.headview);
        imgBack = mView.findViewById(R.id.img_lefticon);
        tvLeftState = mView.findViewById(R.id.tv_common_title);
        tvTitle = mView.findViewById(R.id.tv_common_title);
        tvTitle.setText(getResources().getString(R.string.orderdetail));
        tvLeftState.setText(getResources().getString(R.string.goback));
        recyclerView = findViewById(R.id.recycle_goods);
        viewsp1 = findViewById(R.id.view_1);
        viewsp2 = findViewById(R.id.view_2);
        tvPaid = findViewById(R.id.tv_paidtip);
        tvCook = findViewById(R.id.tv_cookingtip);
        tvFinished = findViewById(R.id.tv_finishedtip);
//        tvXiaDan = findViewById(R.id.tv_xiadan);
//        tvTiaoDan = findViewById(R.id.tv_tiaodan);
//        tvCuiDan = findViewById(R.id.tv_cuidan);
//        imgXiaDan = findViewById(R.id.img_xiadan);
//        imgCuiDan = findViewById(R.id.img_cuidan);
//        imgTiaoDan = findViewById(R.id.img_tiaodan);
        setOnClickListener();
    }

    private void setOnClickListener(){
        imgBack.setOnClickListener(this);
//        tvXiaDan.setOnClickListener(this);
//        imgXiaDan.setOnClickListener(this);
//        tvCuiDan.setOnClickListener(this);
//        imgCuiDan.setOnClickListener(this);
//        tvTiaoDan.setOnClickListener(this);
//        imgTiaoDan.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_lefticon:
                OrderDetailActivity.this.finish();
                break;
//            case R.id.img_xiadan:
//            case R.id.tv_xiadan:
//                Intent mIntent = new Intent(OrderDetailActivity.this, OrderedListActivity.class);
////                Toast.makeText(OrderDetailActivity.this, "下单按钮", Toast.LENGTH_SHORT).show();
////                xiaDanBtn();
//                break;
//            case R.id.img_cuidan:
//            case R.id.tv_cuidan:
//                Toast.makeText(OrderDetailActivity.this, "催单按钮", Toast.LENGTH_SHORT).show();
//                cuiDanBtn();
//                break;
//            case R.id.img_tiaodan:
//            case R.id.tv_tiaodan:
//                Toast.makeText(OrderDetailActivity.this, "调单按钮", Toast.LENGTH_SHORT).show();
//                tiaoDanBtn();
//                break;
        }
    }

    private void tiaoDanBtn(){
        HttpHelper.getInstance().tiaoDan(this, orderNumber, "", new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                int state = ToolUtils.getNetBackCode(responseString);
                if(state == 100){
                    Toast.makeText(OrderDetailActivity.this, "调单成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderDetailActivity.this, "调单失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(OrderDetailActivity.this, "调单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    private void cuiDanBtn(){
        HttpHelper.getInstance().cuiDan(this, orderNumber, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                int state = ToolUtils.getNetBackCode(responseString);
                if(state == 100){
                    Toast.makeText(OrderDetailActivity.this, "催单成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderDetailActivity.this, "催单失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(OrderDetailActivity.this, "催单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void xiaDanBtn(){
        HttpHelper.getInstance().commitOrderBtn(this, orderNumber, "", new JsonHandler<String>() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                int state = ToolUtils.getNetBackCode(responseString);
                if(state == 100){
                    Toast.makeText(OrderDetailActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderDetailActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(OrderDetailActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    OrderedFoodBean mOrderedFoodBean = new OrderedFoodBean();
    String orderNumber;
    private void getOrderDetail(){
        HttpHelper.getInstance().getOrderDetail(this, orderNumber, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                String result = ToolUtils.getJsonParseResult(responseString);
                if(!TextUtils.isEmpty(result)){
                    JSONObject obj;
                    try {
                        obj = new JSONObject(result);
                        mOrderedFoodBean.setIsCollect(obj.getInt("isCollect"));
                        mOrderedFoodBean.setOrderState(obj.getInt("orderState"));
                        mOrderedFoodBean.setOrderTime(obj.getString("orderTime"));
                        mOrderedFoodBean.setCommitTime(obj.getString("commitTime"));
                        mOrderedFoodBean.setOrderNumber(obj.getString("orderNumber"));
                        orderNumber = mOrderedFoodBean.getOrderNumber();
                        String productList = obj.getString("productList");
                        JSONArray mArray = new JSONArray(productList);
                        for(int i=0;i<mArray.length();i++){
                            OrderFoodBean mBean = new OrderFoodBean();
                            mBean.setCategoryId(((JSONObject)mArray.get(i)).getInt("categoryId"));
                            mBean.setId(((JSONObject)mArray.get(i)).getInt("id"));
                            mBean.setOrderId(((JSONObject)mArray.get(i)).getInt("orderId"));
                            mBean.setPrice(((JSONObject)mArray.get(i)).getDouble("price"));
                            mBean.setProductId(((JSONObject)mArray.get(i)).getInt("productId"));
                            mBean.setProductName(((JSONObject)mArray.get(i)).getString("productName"));
                            mBean.setQuantity(((JSONObject)mArray.get(i)).getInt("quantity"));
                            mBean.setState(((JSONObject)mArray.get(i)).getInt("state"));
                            mOrderedFoodBean.getProductList().add(mBean);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(OrderDetailActivity.this, "获取订单详情失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }



}
