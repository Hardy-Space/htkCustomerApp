package com.hl.htk_customer.adapter;

import android.content.Context;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanOrderDetailEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/29.
 */

public class TuiCaiAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<DianCanOrderDetailEntity.DataBean.ProductListsBean> list;

    public TuiCaiAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<DianCanOrderDetailEntity.DataBean.ProductListsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public List<DianCanOrderDetailEntity.DataBean.ProductListsBean> getData() {

        return list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.item_tuican, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(list.get(position).getProductName());
        viewHolder.tvPrice.setText("￥" + list.get(position).getPrice());
        viewHolder.tvNum.setText("x" + list.get(position).getQuantity());


        viewHolder.ivBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!list.get(position).isSelected()) {
                    viewHolder.ivBianji.setImageResource(R.mipmap.bianji2);
                    list.get(position).setSelected(true);
                } else {
                    viewHolder.ivBianji.setImageResource(R.mipmap.bianji1);
                    list.get(position).setSelected(false);
                }

            }
        });


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_bianji)
        ImageView ivBianji;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_num)
        TextView tvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
