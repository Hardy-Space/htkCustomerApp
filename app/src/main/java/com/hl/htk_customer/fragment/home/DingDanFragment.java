package com.hl.htk_customer.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.OrderDetailActivity;
import com.hl.htk_customer.activity.SeatOrderDetailActivity;
import com.hl.htk_customer.activity.TgOrderDetailActivity;
import com.hl.htk_customer.activity.WmShopDetailActivity;
import com.hl.htk_customer.adapter.OrderListAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.OrderListEntity;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.OrderStateChangeEvent;
import com.hl.htk_customer.model.ScrollTopEntity;
import com.hl.htk_customer.model.SeatOrderDetailModel;
import com.hl.htk_customer.model.ShopProduct;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 订单 fragment
 */

public class DingDanFragment extends BaseFragment {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private Toolbar mToolBar;
    private ImageView mBack;
    private TextView mTitle;
    private RecyclerView mOrderListView;
    private int pageNumber = 1;

    private List<OrderListEntity.DataBean> mOrderList;
    private LayoutInflater mInflater;
    private View mNoDataView;
    private View mErrorView;
    private OrderListAdapter mOrderListAdapter;
    private SmartRefreshLayout mRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(OrderStateChangeEvent event){
        mRefresh.autoRefresh();
    }

    @Subscribe
    public void onEventMainThread(RefreshInfoEntity event) {
        Log.i(TAG, "onEventMainThread: " + event.isRefresh());
        if (event.isRefresh() && mRefresh != null)
            mRefresh.autoRefresh();
    }

    @Subscribe
    public void onEventMainThread(ScrollTopEntity event) {
        if (event.getPage() == 2)
            mOrderListView.scrollToPosition(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_dingdan, null);
            mInflater = inflater;
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }
        return view;
    }


    @Override
    public void lazyInitData() {
        if (isVisible && isFirst && isPrepared) {
            isFirst = false;
            initView();
            initWidget();
        }
    }

    private void initView() {
        mTitle = (TextView) view.findViewById(R.id.title);
        mBack = (ImageView) view.findViewById(R.id.img_back);
        mOrderListView = (RecyclerView) view.findViewById(R.id.order_rv);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.order_refresh);
        mToolBar = view.findViewById(R.id.toolbar);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolBar);

    }

    private void initWidget() {
        mBack.setVisibility(View.GONE);
        mTitle.setText(getString(R.string.order));

        mOrderListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mOrderListAdapter = new OrderListAdapter(R.layout.item_order_list, mOrderList);
        mOrderListView.setAdapter(mOrderListAdapter);

        mNoDataView = mInflater.inflate(R.layout.empty_view , null);
        mErrorView = mInflater.inflate(R.layout.error_view , null);

        showLoading();
        getOrderList();

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber = 1;
                getOrderList();
            }
        });
        mRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNumber ++ ;
                getOrderList();
            }
        });

        mOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderListEntity.DataBean data = mOrderListAdapter.getData().get(position);
                Intent intent;
                switch (data.getMark()) {
                    case 0://外卖详情
                        intent = new Intent(getActivity(), OrderDetailActivity.class);
                        intent.putExtra("orderId", data.getOrderId());
                        intent.putExtra("shopId", data.getShopId());
                        startActivity(intent);
                        break;
                    case 1://团购详情
                        intent = new Intent(getActivity(), TgOrderDetailActivity.class);
                        intent.putExtra("orderId", data.getOrderId());
                        intent.putExtra("shopId", data.getShopId());
                        startActivity(intent);
                        break;
                    case 2://订座详情
                        intent = new Intent(getActivity(), SeatOrderDetailActivity.class);
                        intent.putExtra("orderNumber", data.getOrderNumber());
                        intent.putExtra("shopId", data.getShopId());
                        startActivity(intent);
                        break;
                    case 3://自助点餐详情
                        break;
                }
            }
        });

        mOrderListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {

                final int tag = setClickPrompt(position);
                String clickPrompt = "";
                if (tag == 1){
                    clickPrompt = "取消订单";
                }else if (tag == 2){
                    clickPrompt = "删除订单";
                }

                PromptDialog.builder(getContext())
                        .create(clickPrompt)
                        .setListener(new DialogOnClickListener() {
                            @Override
                            public void onPositive() {
                                OrderListEntity.DataBean data = mOrderListAdapter.getData().get(position);
                                if (tag == 1){
                                    cancelOrder(data.getOrderNumber() , data.getMark());
                                }else if (tag == 2){
                                    deleteOrder(data.getOrderNumber() , data.getMark());
                                }
                            }

                            @Override
                            public void onNegative() {

                            }
                        });

                return true;
            }
        });

        mOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderListEntity.DataBean data = mOrderListAdapter.getData().get(position);
                again(data.getProductList() , data.getShopId() , data.getOrderAmount());
            }
        });
    }

    /**
     * 再来一单
     * @param productList 订单产品
     * @param shopId 商铺id
     * @param orderAmount 价格
     */
    private void again(List<OrderListEntity.DataBean.ProductListBean> productList , int shopId , double orderAmount) {

        List<ShopProduct> productList1 = new ArrayList<ShopProduct>();

        for (int i = 0; i < productList.size(); i++) {
            OrderListEntity.DataBean.ProductListBean shopProduct = productList.get(i);
            productList1.add(new ShopProduct(shopProduct.getProductName(), shopProduct.getQuantity(), String.valueOf(shopProduct.getPrice()), shopProduct.getProductId()));
        }

        Intent intent = new Intent(mContext, WmShopDetailActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) productList1);
        intent.putExtra("price", orderAmount);
        startActivity(intent);
    }

    /**
     * 取消订单
     */
    private void cancelOrder(String orderNumber , int mark) {
        showChangeDialog("取消订单");
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("content", "其他原因");
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.cancelOrder, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                hideChangeDialog();

                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100){
                    mRefresh.autoRefresh();
                }
                Toast.makeText(mContext , commonMsg.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 删除订单
     */
    private void deleteOrder(String orderNumber , int mark) {
        showChangeDialog("删除订单");
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.deleteOrder, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                hideChangeDialog();

                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100){
                    mRefresh.autoRefresh();
                }
                Toast.makeText(mContext , commonMsg.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int setClickPrompt(int position) {
        int tag = -1;//1可取消订单  2可删除订单
        OrderListEntity.DataBean data = mOrderListAdapter.getData().get(position);
        switch (data.getMark()){
            case 0:
                switch (data.getOrderState()){
                    case 1: //下单成功
                    case 2://接单，未配送
                    case 6://催单
                        tag = 1;
                        break;
                    case 3://正在配送中
                        // TODO: 2017/10/25
                        break;
                    case 0:
                    case 4://订单完成，已收货
                    case 5://取消
                        tag = 2;
                        break;
                }
                break;
            case 1:
                switch (data.getOrderState()){
                    case 10://下单成功
                        tag = 1;
                        break;
                    case 0:
                    case 11://已消费
                    case 12://已取消
                        tag = 2;
                        break;
                }
                break;
            case 2:
                switch (data.getOrderState()){
                    case 0://未上齐菜
                        tag = 1;
                        break;
                    case 1://菜已上齐
                        tag = 2;
                        break;
                }
                break;
            case 3:
                break;
        }

        return tag;
    }

    public void getOrderList() {
        UserInfoManager infoEntity = new UserInfoManager(getContext());
        RequestParams params = new RequestParams();
        params.put("token" , infoEntity.getToken());
        params.put("pageNumber" , pageNumber);
        AsynClient.post(MyHttpConfing.getOrderList, getContext(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                mOrderListAdapter.setEmptyView(mErrorView);
                if (mRefresh.isRefreshing()) mRefresh.finishRefresh();
                if (mRefresh.isLoading()) mRefresh.finishLoadmore();
                hideLoading();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                OrderListEntity orderListEntity = new Gson().fromJson(rawJsonResponse, OrderListEntity.class);
                if (orderListEntity.getCode() == 100){
                    if (orderListEntity.getData() != null && orderListEntity.getData().size() > 0){
                        if (orderListEntity.getData().size() < 8){
                            mRefresh.setEnableLoadmore(false);
                        }else {
                            mRefresh.setEnableLoadmore(true);
                        }

                        //pageNumber是1代表初次加载，不是1代表加载更多
                        if (pageNumber == 1){
                            mOrderListAdapter.setNewData(orderListEntity.getData());
                        }else {
                            mOrderListAdapter.addData(orderListEntity.getData());
                        }
                    }else {
                        mOrderListAdapter.setEmptyView(mNoDataView);
                    }
                }else {
                    mOrderListAdapter.setNewData(null);
                    mOrderListAdapter.setEmptyView(mErrorView);
                }

                if (mRefresh.isRefreshing()) mRefresh.finishRefresh();
                if (mRefresh.isLoading()) mRefresh.finishLoadmore();
                hideLoading();
            }
        });
    }
}
