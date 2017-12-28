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
import com.hl.htk_customer.entity.ShopAndRecommendEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/23.
 */

public class LikeGridAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<ShopAndRecommendEntity.DataBean.RecommendShopListBean> list;
    Context context;


    public LikeGridAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<ShopAndRecommendEntity.DataBean.RecommendShopListBean> list) {
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

            convertView = layoutInflater.inflate(R.layout.item_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        try {
            viewHolder.image.setImageURI(Uri.parse(list.get(position).getLogoUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        viewHolder.tvName.setText(list.get(position).getShopName());
        viewHolder.monthNum.setText("月售" + list.get(position).getMonthlySalesVolume() + "份");

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.monthNum)
        TextView monthNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
