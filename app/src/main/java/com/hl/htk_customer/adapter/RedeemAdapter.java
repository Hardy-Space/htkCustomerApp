package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.RedeemEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */

public class RedeemAdapter extends BaseQuickAdapter<RedeemEntity.DataBean , BaseViewHolder> {

    public RedeemAdapter(@LayoutRes int layoutResId, @Nullable List<RedeemEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedeemEntity.DataBean item) {
        helper.addOnClickListener(R.id.tv_redeem_convert)
                .setText(R.id.tv_redeem_name , String.format(mContext.getString(R.string.join_redeem_title) , item.getTMoney()))
                .setText(R.id.tv_redeem_integration , String.format(mContext.getString(R.string.join_redeem_integration) , item.getIntegralValue()) )
                .setText(R.id.tv_redeem_limit , String.format(mContext.getString(R.string.join_redeem_limit) , item.getTUseMoney()));
    }
}
