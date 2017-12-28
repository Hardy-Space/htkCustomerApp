package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TaoCanDetailEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class TaoCanDetailsAdapter extends BaseQuickAdapter<TaoCanDetailEntity.DataBean.BuyPackageContentListBean , BaseViewHolder> {

    public TaoCanDetailsAdapter(@LayoutRes int layoutResId, @Nullable List<TaoCanDetailEntity.DataBean.BuyPackageContentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaoCanDetailEntity.DataBean.BuyPackageContentListBean item) {
        helper.setText(R.id.name , item.getProductName())
                .setText(R.id.quantity , String.format(mContext.getString(R.string.join_num_of_copies) , item.getQuantity()))
                .setText(R.id.price , String.format(mContext.getString(R.string.join_order_money ) , item.getPrice()));
    }
}
