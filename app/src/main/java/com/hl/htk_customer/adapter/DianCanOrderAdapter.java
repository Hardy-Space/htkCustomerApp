package com.hl.htk_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.entity.DianCanOrderDetailEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DianCanOrderAdapter extends BaseAdapter {


    Context context;
    LayoutInflater layoutInflater;
    List<DianCanOrderDetailEntity.DataBean.ProductListsBean> list;


    public DianCanOrderAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }


    public void setData(List<DianCanOrderDetailEntity.DataBean.ProductListsBean> list) {
        this.list = list;
        notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_yidian_confige, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.bindData(list, position);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_num)
        TextView tvNum;


        private void bindData(List<DianCanOrderDetailEntity.DataBean.ProductListsBean> list, int position) {

            DianCanOrderDetailEntity.DataBean.ProductListsBean dataBean = list.get(position);

            tvName.setText(dataBean.getProductName());
            tvPrice.setText(dataBean.getPrice() + "元");
            tvNum.setText("x" + dataBean.getQuantity());
        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
