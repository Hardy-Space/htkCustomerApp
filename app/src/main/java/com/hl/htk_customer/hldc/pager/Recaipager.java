package com.hl.htk_customer.hldc.pager;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.adapter.LvAdapter;
import com.hl.htk_customer.hldc.base.BaseViewpager;
import com.hl.htk_customer.hldc.bean.GoodBean;
import com.hl.htk_customer.hldc.view.XListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/26.author：卢俊杰
 */

public class Recaipager extends BaseViewpager implements XListView.IXListViewListener{
    private XListView listView;
    private int page = 0;
    private View view;
    private LvAdapter adapter;
    private FragmentActivity context;
    private List<GoodBean> mList = new ArrayList<>();


    public Recaipager(FragmentActivity context) {
        super((FragmentActivity) context);
        this.context = context;
    }

    @Override
    public View initView() {
        view=View.inflate(mactivity, R.layout.pager_layout,null);
        init(); //初始化
        return view;
    }


    private void init() {
        listView = (XListView) view.findViewById(R.id.lv_pager);
        listView.setXListViewListener(this);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
    }
    private void onLoad(XListView xListView) {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }


    @Override
    public void initData() {
        //请求数据
        for (int i = 0;i<10;i++){
//            String imgurl = "123";
//            String title = "猪肉炖粉条子";
//            String issc = "已收藏";
//            String pingfen = "4";
//            String xiaoliang = "月销:200单";
//            String price = "168元";
//            GoodBean bean = new GoodBean(imgurl,title,issc,pingfen,xiaoliang,price);
//            mList.add(bean);
        }
        onLoad(listView);
        //设置适配器
        if (adapter == null){
            adapter = new LvAdapter(context,mList);
            listView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh(XListView lxist) {
        //下拉刷新
        page = 0;
        mList.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    @Override
    public void onLoadMore(XListView list) {
        //上拉加载
        page+=10;
        initData();
    }
}
