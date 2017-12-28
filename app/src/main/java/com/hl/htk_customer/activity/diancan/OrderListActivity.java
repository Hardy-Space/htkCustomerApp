package com.hl.htk_customer.activity.diancan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.DianCanListAdapter;
import com.hl.htk_customer.adapter.DianCanRecordAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanOrderEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/9.
 */

public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.listView)
    PullToRefreshListView listView;


    private int pageNumber = 1;
    private DianCanRecordAdapter adapter;
    private DianCanOrderEntity dianCanOrderEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_orderlist);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }


    private void initWidget() {
        tvTitle.setText("点餐记录");
        llReturn.setOnClickListener(this);
        adapter = new DianCanRecordAdapter(this);
        listView.setAdapter(adapter);
        getList();


        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber = 1;
                getList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


                if (dianCanOrderEntity != null && dianCanOrderEntity.getData() != null && dianCanOrderEntity.getData().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    pageNumber++;
                    getList();
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
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;

        }
    }


    private void getList() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", MyApplication.shopId);
        params.put("pageNumber", pageNumber);
        AsynClient.post(MyHttpConfing.diancanRecord, this, params, new GsonHttpResponseHandler() {
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

                if(dianCanOrderEntity.getCode() == 100){

                    if(dianCanOrderEntity.getData() == null) return;

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
