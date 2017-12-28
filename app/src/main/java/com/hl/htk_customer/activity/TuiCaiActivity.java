package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.TuiCaiAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanOrderDetailEntity;
import com.hl.htk_customer.entity.SuccessEntity;
import com.hl.htk_customer.model.CommonMsg;
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
 * Created by Administrator on 2017/8/29.
 */

public class TuiCaiActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.listView)
    ListView listView;


    TuiCaiAdapter adapter;

    private String orderNumber;
    private List<DianCanOrderDetailEntity.DataBean.ProductListsBean> list;


    private List<Integer> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_tuicai);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvTitle.setText("退菜");
        tvRight.setText("确认");
        llReturn.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        adapter = new TuiCaiAdapter(this);
        listView.setAdapter(adapter);
        initWidget();
    }


    private void initWidget() {
        orderNumber = getIntent().getStringExtra("orderNumber");
        //  productList

        list = getIntent().getParcelableArrayListExtra("productList");


        adapter.setData(list);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_right:
                tuicai();
                break;
            default:
                break;

        }

    }


    private void tuicai() {

        productList = new ArrayList<>();

        List<DianCanOrderDetailEntity.DataBean.ProductListsBean> data = adapter.getData();

        for (int i = 0; i < data.size(); i++) {


            if (data.get(i).isSelected()) {
                productList.add(data.get(i).getId());
            }

        }

        final Gson gson = new Gson();
        String s = gson.toJson(productList);


        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("idList", s);
        AsynClient.post(MyHttpConfing.tuicai, this, params, new GsonHttpResponseHandler() {
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

                Gson gson1 = new Gson();

                CommonMsg commonMsg = gson1.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    finish();
                }

                showMessage(commonMsg.getMessage());
            }

        });


    }

}
