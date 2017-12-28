package com.hl.htk_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanFenLeiEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/19.
 *
 */

public class HorizontalAdopter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<DianCanFenLeiEntity.DataBean> list;


    public HorizontalAdopter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<DianCanFenLeiEntity.DataBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_horizontal, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(list.get(position).getCategoryName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv)
        TextView tv;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
