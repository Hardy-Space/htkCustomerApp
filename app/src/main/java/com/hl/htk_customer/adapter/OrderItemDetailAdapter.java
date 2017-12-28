package com.hl.htk_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.model.ShopProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 */

public class OrderItemDetailAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<ShopProduct> list;

    public OrderItemDetailAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<ShopProduct> list) {
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

            convertView = layoutInflater.inflate(R.layout.item_order_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_itemName)
        TextView tvItemName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_num)
        TextView tvNum;


        private void bindData(List<ShopProduct> list, int position) {
            tvItemName.setText(list.get(position).getGoods());
            tvPrice.setText("￥" + list.get(position).getPrice());
            tvNum.setText("x" + list.get(position).getNumber());
        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
