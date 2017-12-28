package com.hl.htk_customer.adapter;

import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.MyCollectionEntity;
import com.hl.htk_customer.widget.MyRatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class CollectionAdapter extends BaseQuickAdapter<MyCollectionEntity.DataBean , BaseViewHolder> {

    public CollectionAdapter(@LayoutRes int layoutResId, @Nullable List<MyCollectionEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionEntity.DataBean item) {
        helper.setText(R.id.tv_shopName , item.getShopName())
                .setText(R.id.tv_score , String.valueOf(item.getScore()))
                .setText(R.id.tv_sellNum , String.format("月售%1$s单" , item.getMonthlySalesVolume()))
                .setText(R.id.tv_distance , String.valueOf(item.getWithDistance()))
                .addOnClickListener(R.id.iv_collection);

        ImageView collection = helper.getView(R.id.iv_collection);
        if (item.isCollection())
            collection.setImageResource(R.mipmap.collection2);
        else collection.setImageResource(R.mipmap.collection1);

        MyRatingBar ratingBar = helper.getView(R.id.ratingBar);
        ratingBar.setCountSelected((int)item.getScore());

        SimpleDraweeView icon = helper.getView(R.id.image);
        icon.setImageURI(Uri.parse(item.getLogoUrl()));
    }

}
