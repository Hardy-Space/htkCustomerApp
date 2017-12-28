package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TgOrderDetailEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class TaoCanOrderDetailsAdapter extends BaseQuickAdapter<TgOrderDetailEntity.DataBean.BuyPackageContentListBean , BaseViewHolder> {

    public TaoCanOrderDetailsAdapter(@LayoutRes int layoutResId, @Nullable List<TgOrderDetailEntity.DataBean.BuyPackageContentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TgOrderDetailEntity.DataBean.BuyPackageContentListBean item) {
        helper.setText(R.id.name , item.getProductName())
                .setText(R.id.quantity , String.format(mContext.getString(R.string.join_num_of_copies) , item.getQuantity()))
                .setText(R.id.price , String.format(mContext.getString(R.string.join_order_money ) , item.getPrice()));
    }
}
