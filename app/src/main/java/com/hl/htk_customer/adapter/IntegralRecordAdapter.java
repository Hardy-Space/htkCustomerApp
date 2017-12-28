package com.hl.htk_customer.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.IntegralRecordEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class IntegralRecordAdapter extends BaseQuickAdapter<IntegralRecordEntity.DataBean, BaseViewHolder> {

    public IntegralRecordAdapter(@LayoutRes int layoutResId, @Nullable List<IntegralRecordEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralRecordEntity.DataBean item) {
        helper.setText(R.id.tv_integral_record_title , item.getTitle())
                .setText(R.id.tv_integral_record_time , item.getRecordTime());

        switch (item.getRecordType()){
            case 0://消费
                helper.setText(R.id.tv_integral_record_quality , String.format(mContext.getString(R.string.join_num_cut) , item.getIntegralValue()));
                helper.setTextColor(R.id.tv_integral_record_quality , Color.GRAY);
                break;
            case 1://增加
                helper.setText(R.id.tv_integral_record_quality , String.format(mContext.getString(R.string.join_num_add) , item.getIntegralValue()));
                helper.setTextColor(R.id.tv_integral_record_quality , Color.RED);
                break;
        }
    }
}
