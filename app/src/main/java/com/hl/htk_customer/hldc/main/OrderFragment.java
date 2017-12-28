package com.hl.htk_customer.hldc.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.adapter.OrderAdapter;
import com.hl.htk_customer.hldc.bean.GvBean;
import com.hl.htk_customer.hldc.bean.OrderBean;
import com.hl.htk_customer.hldc.view.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/10/25.---订单页面fragment 2 -- 已弃用
 */

public class OrderFragment extends BaseFragment implements XListView.IXListViewListener{
    private View mView;
    private XListView listView;
    private int page = 0;
    private List<OrderBean> mList = new ArrayList<>();
    private List<GvBean> gvList = new ArrayList<>();
    private OrderAdapter adapter;
    private TextView tv_title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=View.inflate(getActivity(), R.layout.orderfragment_layout,null);
        initView();
        haha();
        initData();

        return mView;
    }

    private void haha() {
        gvList.clear();
        for (int i = 0;i<8;i++){
            String url = "00012138";
            String name = "红烧小萝莉";
            GvBean bean = new GvBean(url,name);
            gvList.add(bean);
        }
    }



    private void initView() {
        tv_title = (TextView) mView.findViewById(R.id.tv_common_title);
        listView = (XListView) mView.findViewById(R.id.lv_order);
        listView.setXListViewListener(this);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        tv_title.setText("订单");
    }

    @Override
    public void onRefresh(XListView list) {
        page = 0;
        mList.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    private void initData() {
        String zhuangtai;
        for (int i = 0;i<10;i++){
            String orderno = "00012138";
            String date = "2017-11-1";
            if (i==3||i==5){
                zhuangtai = "2";
            }else {
                zhuangtai = "1";
            }
            String time = "4";
            String num = "8";
            String price = "168";
            OrderBean bean = new OrderBean(orderno,date,zhuangtai,time,num,price);
            mList.add(bean);
        }
        onLoad(listView);
        if (adapter == null){
            adapter = new OrderAdapter(mList,getActivity(),gvList);
            listView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadMore(XListView list) {
        page+=10;
        initData();
    }

    private void onLoad(XListView xListView) {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

}
