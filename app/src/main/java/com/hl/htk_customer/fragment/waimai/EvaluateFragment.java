package com.hl.htk_customer.fragment.waimai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.WmEvaluateActivity;
import com.hl.htk_customer.adapter.EvluateAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DividerItemDecoration;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyListView;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/22.
 * 评价
 */

public class EvaluateFragment extends BaseFragment implements View.OnClickListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private TextView tvMark;
    private TextView tvEvaluateNum;
    private RecyclerView listView;
    private TextView tvAll;
    private ScrollView scrollView;
    private RelativeLayout rlEvaluate;

    EvluateAdapter evluateAdapter;
    private int shopId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_evaluate, null);
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

        if (isFirst && isPrepared) {

            isFirst = false;
            initWidget();

        }

    }


    private void initView() {

        tvMark = (TextView) view.findViewById(R.id.tv_mark);
        tvEvaluateNum = (TextView) view.findViewById(R.id.tv_evaluate_num);
        listView = view.findViewById(R.id.listView);
        tvAll = (TextView) view.findViewById(R.id.tv_all);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        rlEvaluate = (RelativeLayout) view.findViewById(R.id.rl_evaluate);
    }


    private void initWidget() {
        shopId = getActivity().getIntent().getIntExtra("shopId", -1);
        initView();

        tvAll.setOnClickListener(this);
        rlEvaluate.setOnClickListener(this);

        listView.setLayoutManager(new LinearLayoutManager(mContext));
        listView.addItemDecoration(new DividerItemDecoration(mContext , DividerItemDecoration.VERTICAL_LIST));
        evluateAdapter = new EvluateAdapter(R.layout.item_evaluate , null);
        listView.setAdapter(evluateAdapter);
        disableAutoScrollToBottom();
        getEvlaute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_all:
                Intent intent = new Intent(getActivity(), WmEvaluateActivity.class);
                intent.putExtra("shopId", shopId);
                startActivity(intent);
                break;
            case R.id.rl_evaluate:
                Intent intent1 = new Intent(getActivity(), WmEvaluateActivity.class);
                intent1.putExtra("shopId", shopId);
                startActivity(intent1);
                break;

            default:
                break;

        }
    }


    //禁止ScrollView自动滑动到最底部
    private void disableAutoScrollToBottom() {

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }


    private void getEvlaute() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        params.put("mark", 0);
        AsynClient.post(MyHttpConfing.shopEvaluate, getActivity(), params, new GsonHttpResponseHandler() {
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

                Log.i(TAG , rawJsonResponse);

                Gson gson = new Gson();
                WmEvaluateEntity wmEvaluateEntity = gson.fromJson(rawJsonResponse, WmEvaluateEntity.class);
                if (wmEvaluateEntity.getCode() == 100) {


                    try {


                        List<WmEvaluateEntity.DataBean.ListBean> list = new ArrayList<WmEvaluateEntity.DataBean.ListBean>();

                        int size = wmEvaluateEntity.getData().getList().size();

                        if (size > 5) {
                            size = 5;
                        }

                        for (int i = 0; i < size; i++) {

                            list.add(wmEvaluateEntity.getData().getList().get(i));
                        }

                        evluateAdapter.setNewData(list);
                        tvEvaluateNum.setText("(" + wmEvaluateEntity.getData().getCount() + ")");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
        });

    }


}
