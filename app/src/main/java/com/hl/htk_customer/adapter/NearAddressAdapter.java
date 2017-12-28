package com.hl.htk_customer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.hl.htk_customer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21.
 */

public class NearAddressAdapter extends BaseAdapter {

    Context context;
    List<PoiItem> list;
    LayoutInflater layoutInflater;
    boolean visibility = true;


    public NearAddressAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public void setData(List<PoiItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public void addData(List<PoiItem> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (list!=null)
            return list.size();
        else
            return 0;
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

            convertView = layoutInflater.inflate(R.layout.item_near_address, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PoiItem poiItem = list.get(position);
        viewHolder.tvTitle.setText(poiItem.getTitle());
        viewHolder.tvTitleSub.setText(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
        if (visibility)
            viewHolder.tvTitleSub.setVisibility(View.VISIBLE);
        else
            viewHolder.tvTitleSub.setVisibility(View.GONE);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text_title)
        TextView tvTitle;
        @Bind(R.id.text_title_sub)
        TextView tvTitleSub;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setTvTitleSubVisibility(boolean is){
        this.visibility = is;
    }
}
