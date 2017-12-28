package com.hl.htk_customer.fragment.dingdan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.ConfirmOrderActivity;
import com.hl.htk_customer.activity.EvaluateActivity;
import com.hl.htk_customer.activity.OrderDetailActivity;
import com.hl.htk_customer.activity.WmShopDetailActivity;
import com.hl.htk_customer.adapter.DingDanWaiMaiAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.WmOrderDetailEntity;
import com.hl.htk_customer.entity.WmOrderListEntity;
import com.hl.htk_customer.model.ShopProduct;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19.
 */

public class DingDanWaiMaiFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;


    //    @Bind(R.id.listView)
    private PullToRefreshListView listView;
    private ImageView emptyView;
    DingDanWaiMaiAdapter dingDanWaiMaiAdapter;

    private int page = 1;
    private WmOrderListEntity wmOrderListEntity;
    // private int shopId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_dingdan_waimai, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void lazyInitData() {

        if (isVisible && isFirst && isPrepared) {
            isFirst = false;
            initWidget();
        }


    }

    private void initView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView);
        emptyView = (ImageView) view.findViewById(R.id.empty_view);
    }

    private void initWidget() {

        initView();
        dingDanWaiMaiAdapter = new DingDanWaiMaiAdapter(getActivity());
        listView.setAdapter(dingDanWaiMaiAdapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(emptyView);

        dingDanWaiMaiAdapter.setWaiMaiEvaluateListener(new DingDanWaiMaiAdapter.WaiMaiEvaluateListener() {
            @Override
            public void evaluaterClick(int position) {
                //   startActivity(new Intent(getActivity(), EvaluateActivity.class));
                WmOrderListEntity.DataBean item = (WmOrderListEntity.DataBean) dingDanWaiMaiAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                intent.putExtra("orderId", item.getOrderId());
                intent.putExtra("logo", item.getLogoUrl());
                intent.putExtra("shopName", item.getShopName());
                startActivity(intent);
            }
        });

        dingDanWaiMaiAdapter.setWaiMaiAgainListener(new DingDanWaiMaiAdapter.WaiMaiAgainListener() {
            @Override
            public void againClick(int position) {

                WmOrderListEntity.DataBean item = (WmOrderListEntity.DataBean) dingDanWaiMaiAdapter.getItem(position);
                List<ShopProduct> productList = new ArrayList<ShopProduct>();

                for (int i = 0; i < item.getProductList().size(); i++) {
                    WmOrderListEntity.DataBean.ProductListBean productListBean = item.getProductList().get(i);
                    productList.add(new ShopProduct(productListBean.getProductName(), productListBean.getQuantity(), productListBean.getPrice() + "", productListBean.getProductId()));
                }


                Intent intent = new Intent(getActivity(), WmShopDetailActivity.class);
                intent.putExtra("shopId", item.getShopId());
                intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) productList);
                intent.putExtra("price", item.getOrderAmount());
                startActivity(intent);

            }
        });

        getList(page);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getList(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (wmOrderListEntity != null && wmOrderListEntity.getData() != null && wmOrderListEntity.getData().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    page++;
                    getList(page);
                }


            }
        });

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1000) {
                complete();
            }

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        WmOrderListEntity.DataBean item = (WmOrderListEntity.DataBean) dingDanWaiMaiAdapter.getItem(position - 1);

        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("orderId", item.getOrderId());
        intent.putExtra("shopId", item.getShopId());
        startActivity(intent);


    }


    private void getList(final int page) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("mark", 0);
        params.put("pageNumber", page);
        AsynClient.post(MyHttpConfing.WmOrderList, getActivity(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                wmOrderListEntity = gson.fromJson(rawJsonResponse, WmOrderListEntity.class);
                if (wmOrderListEntity.getCode() == 100) {

                    if (wmOrderListEntity.getData() == null){

                        if(page ==1){

                            List<WmOrderListEntity.DataBean> list = new ArrayList<WmOrderListEntity.DataBean>();
                            dingDanWaiMaiAdapter.setData(list);
                        }
                        else {}
                        complete();
                        return;

                    }

                    //return;

                    if (page == 1) {
                        dingDanWaiMaiAdapter.setData(wmOrderListEntity.getData());
                    } else {
                        dingDanWaiMaiAdapter.addData(wmOrderListEntity.getData());
                    }
                }
                complete();

            }
        });

    }


    private void complete() {
        if (listView != null) {
            listView.onRefreshComplete();
        }

    }


}
