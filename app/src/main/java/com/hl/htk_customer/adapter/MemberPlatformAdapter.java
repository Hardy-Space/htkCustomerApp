package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.MemberPlatformEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MemberPlatformAdapter extends BaseQuickAdapter<MemberPlatformEntity.DataBean, BaseViewHolder> {

    public MemberPlatformAdapter(@LayoutRes int layoutResId, @Nullable List<MemberPlatformEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberPlatformEntity.DataBean item) {

        helper.setText(R.id.tv_member_platform_title , item.getTitle())
                .setText(R.id.tv_member_platform_time , item.getCreateTime())
                .setText(R.id.tv_member_platform_content , item.getDescribe_());

        SimpleDraweeView icon = helper.getView(R.id.iv_member_platform_icon);
        icon.setAspectRatio(2);
        icon.setImageURI(item.getImgUrl());

    }
}
