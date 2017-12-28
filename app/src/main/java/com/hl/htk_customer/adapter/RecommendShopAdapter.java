package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.BestShopEntity;
import com.hl.htk_customer.entity.ShopListEntity;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.PublicRequestUtil;
import com.hl.htk_customer.widget.MyRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19.
 * <p>
 * 推荐商家
 */

public class RecommendShopAdapter extends BaseAdapter {

    List<BestShopEntity.DataBean> list;
    Context context;
    LayoutInflater layoutInflater;


    public RecommendShopAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }


    public void setData(List<BestShopEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public void addData(List<BestShopEntity.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_recommend_shop, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position, context);
        viewHolder.ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionListener.collectionClick(position, viewHolder.ivCollection);


            }
        });


        return convertView;
    }


    public CollectionListener collectionListener;

    public void setCollectionListener(CollectionListener collectionListener) {
        this.collectionListener = collectionListener;
    }

    public interface CollectionListener {

        void collectionClick(int position, ImageView collection);

    }


    public class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.tv_shopName)
        TextView tvShopName;
        @Bind(R.id.ratingBar)
        MyRatingBar ratingBar;
        @Bind(R.id.tv_score)
        TextView tvScore;
        @Bind(R.id.tv_sellNum)
        TextView tvSellNum;
        @Bind(R.id.iv_collection)
        ImageView ivCollection;
        @Bind(R.id.tv_distance)
        TextView tvDistance;


        private void bindData(List<BestShopEntity.DataBean> list, int position, Context context) {

            BestShopEntity.DataBean dataBean = list.get(position);
            image.setImageURI(Uri.parse(dataBean.getLogoUrl()));
            tvShopName.setText(dataBean.getShopName());
            tvSellNum.setText("月售" + dataBean.getMonthlySalesVolume() + "单");
            ratingBar.setCountSelected((int)dataBean.getScore());
            tvScore.setText(dataBean.getScore() + "");
            if (dataBean.isCollection()) {
                ivCollection.setImageResource(R.mipmap.collection2);
            } else {
                ivCollection.setImageResource(R.mipmap.collection1);
            }

            if (!TextUtils.isEmpty(MyApplication.get(context).getLoginState().getLatitude())) {
                double v = Double.parseDouble(MyApplication.get(context).getLoginState().getLatitude());
                double v1 = Double.parseDouble(MyApplication.get(context).getLoginState().getLongitude());
                float distance = MyUtils.getDistance(new LatLng(dataBean.getLatitude(), dataBean.getLongitude()), new LatLng(v, v1));
                distance = (float) (Math.round(distance * 100)) / 100;
                tvDistance.setText(distance + "km");
            } else {
                tvDistance.setText("未知");
            }


        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
