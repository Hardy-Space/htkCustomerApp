package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TransactionRecordEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 *
 */

public class TransactionRecordAdapter extends BaseQuickAdapter<TransactionRecordEntity.DataBean, BaseViewHolder> {

    public TransactionRecordAdapter(@LayoutRes int layoutResId, @Nullable List<TransactionRecordEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionRecordEntity.DataBean item) {
        helper.setText(R.id.tv_transaction_record_time , item.getCreateTime())
                .setText(R.id.tv_transaction_record_price , String.valueOf(item.getPayAmount()));

        switch (item.getOrderType()){
            case 0:
                helper.setText(R.id.tv_transaction_record_style , "外卖订单");
                break;
            case 1:
                helper.setText(R.id.tv_transaction_record_style , "团购订单");
                break;
        }

    }
}
