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
import com.hl.htk_customer.activity.DianCanDetailActivity;
import com.hl.htk_customer.adapter.DianCanRecordAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.DianCanOrderEntity;
import com.hl.htk_customer.entity.WmOrderListEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/15.
 */

public class ZiZhuDianCanFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private int pageNumber = 1;
    private PullToRefreshListView listView;
    private ImageView emptyView;

    private DianCanRecordAdapter adapter;
    private DianCanOrderEntity dianCanOrderEntity;

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


    private void initWidget() {
        initView();
    }


    private void initView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView);
        emptyView = (ImageView) view.findViewById(R.id.empty_view);
        listView.setOnItemClickListener(this);
        adapter = new DianCanRecordAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
        getDatas(pageNumber);


        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber = 1;
                getDatas(pageNumber);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (dianCanOrderEntity != null && dianCanOrderEntity.getData() != null && dianCanOrderEntity.getData().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    pageNumber++;
                    getDatas(pageNumber);
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
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DianCanOrderEntity.DataBean item = (DianCanOrderEntity.DataBean) adapter.getItem(position - 1);

        Intent intent = new Intent(getActivity(), DianCanDetailActivity.class);

        intent.putExtra("orderId", item.getId());

        startActivity(intent);


    }


    private void getDatas(final int pageNumber) {


        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", pageNumber);
        AsynClient.post(MyHttpConfing.diancanRecord, getActivity(), params, new GsonHttpResponseHandler() {
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
                dianCanOrderEntity = gson.fromJson(rawJsonResponse, DianCanOrderEntity.class);

                if (dianCanOrderEntity.getCode() == 100) {

                    if (dianCanOrderEntity.getData() == null) {

                        if (pageNumber == 1) {

                            List<DianCanOrderEntity.DataBean> list = new ArrayList<DianCanOrderEntity.DataBean>();
                            adapter.setData(list);
                        } else {
                        }
                        complete();
                        return;

                    } //return;

                    if (pageNumber == 1) {
                        adapter.setData(dianCanOrderEntity.getData());
                    } else {
                        adapter.addData(dianCanOrderEntity.getData());
                    }

                    complete();
                }

            }
        });


    }


    private void complete() {
        if (listView != null) {
            listView.onRefreshComplete();
        }

    }


}
