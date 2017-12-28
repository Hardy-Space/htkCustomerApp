package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.entity.EvaluationEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EvaluationAdapter extends BaseQuickAdapter<EvaluationEntity, BaseViewHolder> {

    public EvaluationAdapter(@LayoutRes int layoutResId, @Nullable List<EvaluationEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluationEntity item) {

    }
}
