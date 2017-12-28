package com.hl.htk_customer.fragment.dingdan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.hl.htk_customer.activity.EvaluateActivity;
import com.hl.htk_customer.activity.TgOrderDetailActivity;
import com.hl.htk_customer.activity.TgTaoCanDetailActivity;
import com.hl.htk_customer.adapter.DingDanTuanGouAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.TgOrderListEntity;
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

public class DingDanTuanGouFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    /* @Bind(R.id.empty_view)
     ImageView emptyView;
     */
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    // @Bind(R.id.listView)
    private PullToRefreshListView listView;
    private ImageView emptyView;

    DingDanTuanGouAdapter dingDanTuanGouAdapter;

    private int page = 1;
    private TgOrderListEntity tgOrderListEntity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_dingdan_tuangou, null);
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

        dingDanTuanGouAdapter = new DingDanTuanGouAdapter(getActivity());
        listView.setAdapter(dingDanTuanGouAdapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(emptyView);

        dingDanTuanGouAdapter.setTuanGouAgainListener(new DingDanTuanGouAdapter.TuanGouAgainListener() {
            @Override
            public void againClick(int position) {
                TgOrderListEntity.DataBean item = (TgOrderListEntity.DataBean) dingDanTuanGouAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), TgTaoCanDetailActivity.class);
                intent.putExtra("id", item.getPackageId());
                startActivity(intent);
            }
        });


        dingDanTuanGouAdapter.setTuanGouEvaluateListener(new DingDanTuanGouAdapter.TuanGouEvaluateListener() {
            @Override
            public void evaluaterClick(int position) {
                //     startActivity(new Intent(getActivity(), EvaluateActivity.class));
                TgOrderListEntity.DataBean item = (TgOrderListEntity.DataBean) dingDanTuanGouAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                intent.putExtra("orderId", item.getOrderId());
                intent.putExtra("logo", item.getLogoUrl());
                intent.putExtra("shopName", item.getShopName());
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

                if (tgOrderListEntity != null && tgOrderListEntity.getData() != null && tgOrderListEntity.getData().size() < 8) {
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
        //  startActivity(new Intent(getActivity(), TuanGouShopActivity.class));
        TgOrderListEntity.DataBean item = (TgOrderListEntity.DataBean) dingDanTuanGouAdapter.getItem(position - 1);
        Intent intent = new Intent(getActivity(), TgOrderDetailActivity.class);
        intent.putExtra("orderId", item.getOrderId());
        intent.putExtra("shopId", item.getShopId());
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

    }


    private void getList(final int page) {


        RequestParams params = AsynClient.getRequestParams();
        params.put("mark", 1);
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
                tgOrderListEntity = gson.fromJson(rawJsonResponse, TgOrderListEntity.class);
                if (tgOrderListEntity.getCode() == 100) {
                    if (tgOrderListEntity.getData() == null) {

                        if (page == 1) {

                            List<TgOrderListEntity.DataBean> list = new ArrayList<TgOrderListEntity.DataBean>();
                            dingDanTuanGouAdapter.setData(list);
                        } else {

                        }
                        complete();
                        return;

                    }


                    //           return;

                    if (page == 1) {
                        dingDanTuanGouAdapter.setData(tgOrderListEntity.getData());
                    } else {
                        dingDanTuanGouAdapter.addData(tgOrderListEntity.getData());
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
