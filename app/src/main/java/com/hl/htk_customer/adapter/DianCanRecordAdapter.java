package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanOrderEntity;
import com.hl.htk_customer.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/9.
 */

public class DianCanRecordAdapter extends BaseAdapter {

    List<DianCanOrderEntity.DataBean> list;
    Context context;
    LayoutInflater layoutInflater;

    public DianCanRecordAdapter(Context context) {

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public void setData(List<DianCanOrderEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<DianCanOrderEntity.DataBean> list) {
        this.list.addAll(list);
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

            convertView = layoutInflater.inflate(R.layout.item_diancan_record, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        try {
            viewHolder.head.setImageURI(Uri.parse(list.get(position).getLogoUrl()));
        } catch (Exception e) {

        }

        viewHolder.tvShopName.setText(list.get(position).getShopName());
        viewHolder.tvSeat.setText("座位号：" + list.get(position).getSeatName());
        viewHolder.tvTime.setText("" + list.get(position).getOrderTime());

        if (list.get(position).getPayState() == 1) {
            viewHolder.tvState.setText("已支付");
        } else {
            viewHolder.tvState.setText("未支付");
        }


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.tv_seat)
        TextView tvSeat;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_shopName)
        TextView tvShopName;
        @Bind(R.id.tv_state)
        TextView tvState;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
