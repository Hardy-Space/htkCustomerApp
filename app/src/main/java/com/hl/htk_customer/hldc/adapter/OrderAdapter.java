package com.hl.htk_customer.hldc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.GvBean;
import com.hl.htk_customer.hldc.bean.OrderBean;
import com.hl.htk_customer.hldc.view.MyGridView;

import java.util.List;


/**
 * Created by Administrator on 2017/10/31.
 */

public class OrderAdapter extends BaseAdapter {
    private List<OrderBean> mList;
    private Context mContext;
    private List<GvBean> gvList;
    private GvAdapter mAdapter;
    public OrderAdapter(List<OrderBean> list, Context context,List<GvBean> mlist) {
        mList = list;
        mContext = context;
        gvList = mlist;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolderod viewHolderod;
        if (view == null){
            viewHolderod = new ViewHolderod();
            view = LayoutInflater.from(mContext).inflate(R.layout.lvorder_item_layout,null);
            viewHolderod.tv_orderno = (TextView) view.findViewById(R.id.tv_orderno);
            viewHolderod.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewHolderod.tv_zhuangtai = (TextView) view.findViewById(R.id.tv_zhuangtai);
            viewHolderod.tv_time = (TextView) view.findViewById(R.id.tv_time);
            viewHolderod.tv_num = (TextView) view.findViewById(R.id.tv_num);
            viewHolderod.tv_price = (TextView) view.findViewById(R.id.tv_price);
            viewHolderod.tv_qxdd = (TextView) view.findViewById(R.id.tv_qxdd);
            viewHolderod.tv_ddxq = (TextView) view.findViewById(R.id.tv_ddxq);
            viewHolderod.tv_shanchu = (TextView) view.findViewById(R.id.tv_shanchu);
            viewHolderod.tv_fapiao = (TextView) view.findViewById(R.id.tv_fapiao);
            viewHolderod.tv_bddxq = (TextView) view.findViewById(R.id.tv_bddxq);
            viewHolderod.tv_dpzjf = (TextView) view.findViewById(R.id.tv_dpzjf);
            viewHolderod.re_a = (RelativeLayout) view.findViewById(R.id.re_a);
            viewHolderod.re_b = (RelativeLayout) view.findViewById(R.id.re_b);
            viewHolderod.lin_cuidan = (LinearLayout) view.findViewById(R.id.lin_cuidan);
            viewHolderod.lin_fankui = (LinearLayout) view.findViewById(R.id.lin_fankui);
            viewHolderod.mGridView = (MyGridView) view.findViewById(R.id.gridview);
            view.setTag(viewHolderod);
        }else {
            viewHolderod = (ViewHolderod) view.getTag();
        }
            viewHolderod.tv_orderno.setText(mList.get(i).getOrderno());
            viewHolderod.tv_date.setText(mList.get(i).getDate());
            if (mList.get(i).getZhuangtai().equals("1")){
                viewHolderod.tv_zhuangtai.setText("正在制作中");
                viewHolderod.re_b.setVisibility(View.GONE);
                viewHolderod.re_a.setVisibility(View.VISIBLE);
            }else {
                viewHolderod.tv_zhuangtai.setText("已完成");
                viewHolderod.re_a.setVisibility(View.GONE);
                viewHolderod.re_b.setVisibility(View.VISIBLE);
            }

            viewHolderod.tv_time.setText("提交订单已:"+mList.get(i).getTime()+"分钟");
            viewHolderod.tv_num.setText("共"+mList.get(i).getNum()+"件");
            viewHolderod.tv_price.setText(mList.get(i).getPrice()+"元");

            mAdapter = new GvAdapter(mContext,gvList);
            viewHolderod.mGridView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            viewHolderod.lin_fankui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"已反馈",Toast.LENGTH_SHORT).show();
                }
            });
        viewHolderod.lin_cuidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"已催单",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderod.tv_qxdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"订单已取消",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderod.tv_ddxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"查看订单详情",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderod.tv_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"删除该订单",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderod.tv_fapiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"发票",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderod.tv_bddxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"查看订单详情",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolderod.tv_dpzjf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"已点评",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    static class ViewHolderod{
        TextView tv_orderno,tv_date,tv_zhuangtai,tv_time,tv_num,tv_price
                ,tv_qxdd,tv_ddxq,tv_shanchu,tv_fapiao,tv_bddxq,tv_dpzjf;
        RelativeLayout re_a,re_b;
        LinearLayout lin_fankui,lin_cuidan;
        MyGridView mGridView;
    }
}
