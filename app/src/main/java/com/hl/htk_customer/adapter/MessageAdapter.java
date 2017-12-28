package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.MessageEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class MessageAdapter extends BaseQuickAdapter<MessageEntity.DataBean , BaseViewHolder> {

    public MessageAdapter(@LayoutRes int layoutResId, @Nullable List<MessageEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageEntity.DataBean item) {

        helper.setText(R.id.tv_title , item.getNoticeTitle())
                .setText(R.id.tv_content , item.getNoticeContent())
                .setText(R.id.tv_time , item.getNoticeTime());

    }
}
