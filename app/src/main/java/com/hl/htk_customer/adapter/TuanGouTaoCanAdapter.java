package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TuanGouShopInfoEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TuanGouTaoCanAdapter extends BaseQuickAdapter<TuanGouShopInfoEntity.DataBean.BuyPackageListBean , BaseViewHolder> {


    public TuanGouTaoCanAdapter(@LayoutRes int layoutResId, @Nullable List<TuanGouShopInfoEntity.DataBean.BuyPackageListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuanGouShopInfoEntity.DataBean.BuyPackageListBean item) {
        String order = "";
        if (item.isReservation()) {
            order = "需要预约";
        } else {
            order = "免预约";
        }

        helper.setText(R.id.tv_time , item.getUsageTime() + order)
                .setText(R.id.tv_name , item.getPackageName())
                .setText(R.id.tv_sell_num , "已售" + item.getSoldQuantity() + "单")
                .setText(R.id.tv_price , "￥" + item.getPrice())
                .setText(R.id.tv_price2 , "门市价 ￥" + item.getRetailPrice() );

        SimpleDraweeView ivLogo = helper.getView(R.id.iv_logo);
        ivLogo.setImageURI(Uri.parse(item.getImgUrl()));
    }

}
