package com.hl.htk_customer.activity.diancan;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MPHUtils;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 马鹏昊
 * @desc 自助点餐订单详情页
 * @date 2018-2-8
 */
public class BuffetOrderDetailActivity extends BaseActivity {

    @Bind(R.id.ll_return)
    ImageView mLlReturn;
    @Bind(R.id.tv_order_state)
    TextView mTvOrderState;
    @Bind(R.id.img_shop_logo)
    SimpleDraweeView mImgShopLogo;
    @Bind(R.id.tv_shopName)
    TextView mTvShopName;
    @Bind(R.id.tv_orderId)
    TextView mTvOrderId;
    @Bind(R.id.tv_seat_name)
    TextView mTvSeatName;
    @Bind(R.id.tv_order_time)
    TextView mTvOrderTime;
    @Bind(R.id.list_products)
    MyListView mListProducts;
    @Bind(R.id.sumPrice)
    TextView mSumPrice;

    MyAdapter mAdapter;

    private String mOrderNumber;
    private int mShopId;
    private List<OrderFoodBean> mOrderedFoodData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffet_order_detail);
        ButterKnife.bind(this);

        initView();

        getData();

    }

    private void initView() {
        mOrderedFoodData = new ArrayList<>();
        mAdapter = new MyAdapter();
        mListProducts.setAdapter(mAdapter);

        if (getIntent() != null) {

            mOrderNumber = getIntent().getStringExtra("orderNumber");
            mShopId = getIntent().getIntExtra("shopId", -1);
        }

    }

    private void getData() {
        final Dialog loading = MPHUtils.createLoadingDialog(this, "");
        loading.show();

        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", mOrderNumber);
        params.put("shopId", mShopId);

        AsynClient.post(MyHttpConfing.getZZDCOrderDetail, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                loading.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                loading.dismiss();
                JSONObject jb = null;
                try {
                    jb = new JSONObject(rawJsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int code = -1;
                if (jb == null) {
                    Toast.makeText(BuffetOrderDetailActivity.this, "Json解析失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        code = jb.getInt("code");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (code == 100) {
                    String data = ToolUtils.getJsonParseResult(rawJsonResponse);
                    if (!TextUtils.isEmpty(data)) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(data);
                            updateUI(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

    }


    private void updateUI(JSONObject jsonObject) {

        try {
            String orderNumber = jsonObject.getString("orderNumber");
            mTvOrderId.setText("订单号：" + orderNumber);
            double orderAmount = jsonObject.getDouble("orderAmount");
            mSumPrice.setText(orderAmount + "");
            String seat = jsonObject.getString("seatName");
            mTvSeatName.setText("座位号：" + seat);
            String orderTime = jsonObject.getString("orderTime");
            mTvOrderTime.setText("创建时间：" + orderTime);
            String shopName = jsonObject.getString("shopName");
            mTvShopName.setText(shopName);
            String shopLogo = jsonObject.getString("logoUrl");
            mImgShopLogo.setImageURI(shopLogo);
            int orderState = jsonObject.getInt("orderState");
            switch (orderState) {
                case 2:
                    mTvOrderState.setText("已结单");
                    break;
                default:
                    mTvOrderState.setText("未结单");
            }

            mOrderedFoodData.clear();
            String productList = jsonObject.getString("productLists");
            JSONArray mArray = new JSONArray(productList);
            for (int i = 0; i < mArray.length(); i++) {
                OrderFoodBean mBean = new OrderFoodBean();
                JSONObject obj = mArray.getJSONObject(i);
                mBean.setPrice(obj.getDouble("price"));
                mBean.setProductName(obj.getString("productName"));
                mBean.setQuantity(mArray.getJSONObject(i).getInt("quantity"));
                mOrderedFoodData.add(mBean);
            }
            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.ll_return)
    public void onViewClicked() {
        finish();
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mOrderedFoodData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.item_order_detail, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvItemName.setText(mOrderedFoodData.get(position).getProductName());
            viewHolder.tvPrice.setText("￥" + mOrderedFoodData.get(position).getPrice());
            viewHolder.tvNum.setText("x" + mOrderedFoodData.get(position).getQuantity());

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_itemName)
            TextView tvItemName;
            @Bind(R.id.tv_price)
            TextView tvPrice;
            @Bind(R.id.tv_num)
            TextView tvNum;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }
}
