package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.ProductEntity;
import com.hl.htk_customer.utils.ImageLoadManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19.
 * <p>
 * 产品分类
 */

public class ProductStyleAdapter extends BaseAdapter {

    Context context;
    List<ProductEntity.DataBean> list;


    public ProductStyleAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<ProductEntity.DataBean> list) {

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_style, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProductEntity.DataBean dataBean = list.get(position);
        try {
            ImageLoadManager.getInstance().setImage(dataBean.getCategoryUrl() , viewHolder.image);
//            viewHolder.image.setImageURI(Uri.parse());
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.tvStyleName.setText(dataBean.getCategoryName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.tv_styleName)
        TextView tvStyleName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
