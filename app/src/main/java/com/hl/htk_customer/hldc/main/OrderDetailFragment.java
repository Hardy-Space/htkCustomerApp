package com.hl.htk_customer.hldc.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.OrderFoodBean;
import com.hl.htk_customer.hldc.bean.OrderedFoodBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.utils.MPHUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import butterknife.ButterKnife;


/**
 * Created by asus on 2017/11/3.-- laughing 订单详情
 */

public class OrderDetailFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = OrderDetailFragment.class.getSimpleName();
    private View mView, viewsp1, viewsp2;
    private TextView tvTitle, tvLeftState, tvPaid, tvCook, tvFinished, tvDingDanNumber, tvData, tvStateTip,
            tvGoodMountTip, tvCommittedTime, tvTotalAmount, tvTotalPrice;
    private ImageView imgBack;
    //    private TextView tvXiaDan, tvTiaoDan, tvCuiDan;
    //    private ImageView imgXiaDan, imgTiaoDan, imgCuiDan;

    private LinearLayout cuidanArea, tiaodanArea;
    private TextView mNonDataTip;
    private ScrollView mHasDataArea;

    private RecyclerView recyclerView;
    private OrderedAdapter orderedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.orderdetail_layout, container, false);
        initViews(mView);
        refreshCurrentUI();
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged === >>>>" + hidden);
        if (mOrderedFoodBean != null) {
            mOrderedFoodBean.setCommitTime(null);
            mOrderedFoodBean.setIsCollect(0);
            mOrderedFoodBean.setOrderState(0);
            mOrderedFoodBean.setOrderNumber(null);
            mOrderedFoodBean.getProductList().clear();
        }
        if (!hidden) {
            //            Toast.makeText(mActivity, "show", Toast.LENGTH_SHORT).show();
            refreshCurrentUI();
        } else {
            //            Toast.makeText(mActivity, "hide", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible())
            refreshCurrentUI();
    }

    public void refreshCurrentUI() {
        Log.d(TAG, "refreshCurrentUI() == >>>>");
        orderNumber = PreferencesUtils.getString(getActivity(), "orderNumber");
        if (!TextUtils.isEmpty(orderNumber)) {
            getOrderDetail();
        } else {
            Toast.makeText(getActivity(), "暂无订单记录", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(View view) {
        mView = view.findViewById(R.id.headview);
        imgBack = mView.findViewById(R.id.img_lefticon);
        tvLeftState = mView.findViewById(R.id.tv_leftstate);
        tvTitle = mView.findViewById(R.id.tv_common_title);
        tvTitle.setText(getResources().getString(R.string.orderdetail));
        recyclerView = view.findViewById(R.id.recycle_goods);
        viewsp1 = view.findViewById(R.id.view_1);
        viewsp2 = view.findViewById(R.id.view_2);
        tvPaid = view.findViewById(R.id.tv_paidtip);
        tvCook = view.findViewById(R.id.tv_cookingtip);
        tvFinished = view.findViewById(R.id.tv_finishedtip);
        tvDingDanNumber = view.findViewById(R.id.tv_orderno);
        tvData = view.findViewById(R.id.tv_date);
        tvStateTip = view.findViewById(R.id.tv_statetip);
        tvGoodMountTip = view.findViewById(R.id.tv_goodsmounttip);
        tvCommittedTime = view.findViewById(R.id.tv_committedtime);
        tvTotalAmount = view.findViewById(R.id.tv_totalamount);
        tvTotalPrice = view.findViewById(R.id.tv_zongji);
        //        tvXiaDan = view.findViewById(R.id.tv_xiadan);
        //        tvTiaoDan = view.findViewById(R.id.tv_tiaodan);
        //        tvCuiDan = view.findViewById(R.id.tv_cuidan);
        //        imgXiaDan = view.findViewById(R.id.img_xiadan);
        //        imgCuiDan = view.findViewById(R.id.img_cuidan);
        //        imgTiaoDan = view.findViewById(R.id.img_tiaodan);

        cuidanArea = view.findViewById(R.id.cuidanArea);
        tiaodanArea = view.findViewById(R.id.tiaodanArea);

        mNonDataTip = view.findViewById(R.id.nonDataTip);
        mHasDataArea = view.findViewById(R.id.hasDataArea);

        setOnClickListener();

    }

    private void setOnClickListener() {
        imgBack.setOnClickListener(this);
        cuidanArea.setOnClickListener(this);
        tiaodanArea.setOnClickListener(this);
        //        tvXiaDan.setOnClickListener(this);
        //        imgXiaDan.setOnClickListener(this);
        //        tvCuiDan.setOnClickListener(this);
        //        imgCuiDan.setOnClickListener(this);
        //        tvTiaoDan.setOnClickListener(this);
        //        imgTiaoDan.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_lefticon:
                break;
            //            case R.id.img_xiadan:
            //            case R.id.tv_xiadan:
            //                if (!TextUtils.isEmpty(orderNumber)) {
            //                    Intent mIntent = new Intent(getActivity(), OrderedListActivity.class);
            //                    mIntent.putExtra("type", "xiadan");
            //                    getActivity().startActivity(mIntent);
            //                } else {
            //                    Toast.makeText(getActivity(), "尚未初始化下单信息", Toast.LENGTH_SHORT).show();
            //                }
            //                break;

            case R.id.cuidanArea:
                if (!TextUtils.isEmpty(orderNumber)) {
                    //如果商家已接单
                    if (mOrderedFoodBean.getOrderState() == 1) {
                        long time = getReduceTimePeriod();
                        if (time > 10) {
                            cuiDanBtn();
                        } else {
                            Toast.makeText(getActivity(), "美食正在烹饪，请不要频繁催单哦，" + (10 - time) + "分钟后再尝试吧~", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "商家尚未接单，无法催单", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "尚未初始化下单信息", Toast.LENGTH_SHORT).show();
                }
                break;

            //            case R.id.img_cuidan:
            //            case R.id.tv_cuidan:
            //                if (!TextUtils.isEmpty(orderNumber)) {
            //                    if (mOrderedFoodBean.getOrderState() == 1) {
            //                        cuiDanBtn();
            //                    } else {
            //                        Toast.makeText(getActivity(), "无法催单", Toast.LENGTH_SHORT).show();
            //                    }
            //                } else {
            //                    Toast.makeText(getActivity(), "尚未初始化下单信息", Toast.LENGTH_SHORT).show();
            //                }
            //                break;

            case R.id.tiaodanArea:
                if (!TextUtils.isEmpty(orderNumber)) {
                    Intent mIntent1 = new Intent(getActivity(), OrderedListActivity.class);
                    mIntent1.putExtra("type", "tiaodan");
                    getActivity().startActivity(mIntent1);
                } else {
                    Toast.makeText(getActivity(), "尚未初始化下单信息", Toast.LENGTH_SHORT).show();
                }
                break;

            //            case R.id.img_tiaodan:
            //            case R.id.tv_tiaodan:
            //                if (!TextUtils.isEmpty(orderNumber)) {
            //                    Intent mIntent1 = new Intent(getActivity(), OrderedListActivity.class);
            //                    mIntent1.putExtra("type", "tiaodan");
            //                    getActivity().startActivity(mIntent1);
            //                } else {
            //                    Toast.makeText(getActivity(), "尚未初始化下单信息", Toast.LENGTH_SHORT).show();
            //                }
            //                break;
        }
    }

    private long getReduceTimePeriod() {
        long lastDate = PreferencesUtils.getLong(getActivity(), "cuidanLastDate", 0);
        Date date = new Date();
        long nowDate = date.getTime();
        long periodTime = nowDate - lastDate;
        long minute = periodTime / 1000 / 60;
        return minute;
    }

    private void tiaoDanBtn() {
        HttpHelper.getInstance().tiaoDan(getActivity(), orderNumber, "", new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString, Object response) {
                int state = ToolUtils.getNetBackCode(responseString);
                if (state == 100) {
                    Toast.makeText(getActivity(), "调单成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "调单失败" + responseString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "调单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    private void cuiDanBtn() {

        final Dialog loading = MPHUtils.createLoadingDialog(getActivity(), "");
        loading.show();

        PreferencesUtils.putLong(getActivity(), "cuidanLastDate", new Date().getTime());

        HttpHelper.getInstance().cuiDan(getActivity(), orderNumber, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                loading.dismiss();
                //                int state = ToolUtils.getNetBackCode(rawJsonResponse);
                //                if (state == 100) {
                //                    Toast.makeText(getActivity(), "催单成功", Toast.LENGTH_SHORT).show();
                //                } else {
                //                    Toast.makeText(getActivity(), "催单失败" + rawJsonResponse, Toast.LENGTH_SHORT).show();
                //                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                loading.dismiss();
                //                Toast.makeText(getActivity(), "催单失败" + responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    private void xiaDanBtn() {
        HttpHelper.getInstance().commitOrderBtn(getActivity(), orderNumber, "", new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                int state = ToolUtils.getNetBackCode(rawJsonResponse);
                if (state == 100) {
                    Toast.makeText(getActivity(), "下单成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "下单失败" + rawJsonResponse, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "下单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    OrderedFoodBean mOrderedFoodBean = new OrderedFoodBean();
    String orderNumber;

    private void getOrderDetail() {

        final Dialog loading = MPHUtils.createLoadingDialog(getActivity(), "");
        loading.show();

        HttpHelper.getInstance().getOrderDetail(getActivity(), orderNumber, new JsonHandler<String>() {

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
                    Toast.makeText(getActivity(), "Json解析失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        code = jb.getInt("code");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mOrderedFoodBean.getProductList().clear();
                if (code == 100) {
                    String result = ToolUtils.getJsonParseResult(responseString);
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(result);
                            mOrderedFoodBean.setCommitTime(null);
                            mOrderedFoodBean.setIsCollect(0);
                            mOrderedFoodBean.setOrderState(0);
                            mOrderedFoodBean.setOrderNumber(null);
                            mOrderedFoodBean.getProductList().clear();
                            mOrderedFoodBean.setOrderState(obj.getInt("orderState"));
                            mOrderedFoodBean.setOrderTime(obj.getString("orderTime"));
                            mOrderedFoodBean.setCommitTime(obj.getString("commitTime"));
                            mOrderedFoodBean.setOrderNumber(obj.getString("orderNumber"));
                            String productList = obj.getString("productList");
                            JSONArray mArray = new JSONArray(productList);
                            for (int i = 0; i < mArray.length(); i++) {
                                OrderFoodBean mBean = new OrderFoodBean();
                                mBean.setCategoryId(mArray.getJSONObject(i).getInt("categoryId"));
                                mBean.setId(mArray.getJSONObject(i).getInt("id"));
                                mBean.setOrderId(mArray.getJSONObject(i).getInt("orderId"));
                                mBean.setPrice(mArray.getJSONObject(i).getDouble("price"));
                                mBean.setProductId(mArray.getJSONObject(i).getInt("productId"));
                                mBean.setProductName(mArray.getJSONObject(i).getString("productName"));
                                mBean.setQuantity(mArray.getJSONObject(i).getInt("quantity"));
                                mBean.setState(mArray.getJSONObject(i).getInt("state"));
                                mOrderedFoodBean.getProductList().add(mBean);
                            }
                            if (mOrderedFoodBean.getOrderState() == 2) {
                                Toast.makeText(getActivity(), "订单已完成", Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(getActivity(), "orderNumber", "");
                                orderNumber = "";
                            }
                            refreshCookingUI();
                            tvDingDanNumber.setText(getResources().getString(R.string.dingdanbianhao) + orderNumber);
                            if (orderedAdapter == null) {
                                orderedAdapter = new OrderedAdapter(getActivity(), mOrderedFoodBean.getProductList());
                                orderedAdapter.notifyDataSetChanged();
                            } else {
                                orderedAdapter.notifyDataSetChanged();
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(orderedAdapter);
                            if (mOrderedFoodBean != null && mOrderedFoodBean.getProductList() != null && mOrderedFoodBean.getProductList().size() > 0) {
                                recyclerView.scrollToPosition(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mNonDataTip.setVisibility(View.GONE);
                        mHasDataArea.setVisibility(View.VISIBLE);
                    } else {
                        mNonDataTip.setVisibility(View.VISIBLE);
                        mHasDataArea.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                loading.dismiss();
                Log.d(TAG, "" + responseString);
                Toast.makeText(getActivity(), "获取订单详情失败", Toast.LENGTH_SHORT).show();
                mNonDataTip.setVisibility(View.VISIBLE);
                mHasDataArea.setVisibility(View.GONE);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    int totalMount = 0;
    double totalConsume = 0;

    private void caculateTotalConsume() {
        totalMount = 0;
        totalConsume = 0;
        for (int i = 0; i < mOrderedFoodBean.getProductList().size(); i++) {
            totalMount += mOrderedFoodBean.getProductList().get(i).getQuantity();
            totalConsume += mOrderedFoodBean.getProductList().get(i).getQuantity() * mOrderedFoodBean.getProductList().get(i).getPrice();
        }
    }

    /**
     * 0 - 初始化订单，1，下单成功，2，烹制完成
     */
    private void refreshCookingUI() {
        tvData.setText("" + mOrderedFoodBean.getOrderTime());
        tvCommittedTime.setText(getResources().getString(R.string.orderedtimetip) + mOrderedFoodBean.getCommitTime());
        if (mOrderedFoodBean.getProductList() != null && mOrderedFoodBean.getProductList().size() > 0) {
            tvGoodMountTip.setText(getResources().getString(R.string.orderedgoods) + "(" + mOrderedFoodBean.getProductList().size() + ")");
            caculateTotalConsume();
        }
        tvTotalAmount.setText("共" + totalMount + "件");
        tvTotalPrice.setText("" + totalConsume + getResources().getString(R.string.yuan1));
        if (mOrderedFoodBean.getOrderState() == 0) {
            tvPaid.setText("未付款");
            tvCook.setText("未开始");
            tvFinished.setText("未开始");
            viewsp1.setBackgroundColor(getResources().getColor(R.color.color_cccccc));
            viewsp2.setBackgroundColor(getResources().getColor(R.color.color_cccccc));
            tvStateTip.setText("未付款");
        } else if (mOrderedFoodBean.getOrderState() == 1) {
            tvPaid.setText(getResources().getString(R.string.paid_state));
            tvCook.setText(getResources().getString(R.string.cooking_state));
            tvFinished.setText("未完成");
            viewsp1.setBackgroundColor(getResources().getColor(R.color.color_common_bg));
            viewsp2.setBackgroundColor(getResources().getColor(R.color.color_cccccc));
            tvStateTip.setText("烹饪中");
        } else if (mOrderedFoodBean.getOrderState() == 2) {
            tvPaid.setText(getResources().getString(R.string.paid_state));
            tvCook.setText(getResources().getString(R.string.finished_state));
            tvFinished.setText(getResources().getString(R.string.finished_state));
            viewsp1.setBackgroundColor(getResources().getColor(R.color.color_common_bg));
            viewsp2.setBackgroundColor(getResources().getColor(R.color.color_common_bg));
            tvStateTip.setText("已完成");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
