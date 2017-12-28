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
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.widget.MyRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 */

public class EvluateAdapter extends BaseQuickAdapter<WmEvaluateEntity.DataBean.ListBean , BaseViewHolder> {


    public EvluateAdapter(@LayoutRes int layoutResId, @Nullable List<WmEvaluateEntity.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WmEvaluateEntity.DataBean.ListBean item) {

        helper.setText(R.id.tv_nickName , item.getNickName())
                .setText(R.id.tv_time , item.getCommentTime())
                .setText(R.id.tv_content , item.getContent());

        SimpleDraweeView head = helper.getView(R.id.head);
        head.setImageURI(Uri.parse(item.getAvaUrl()));

        MyRatingBar ratingBar = helper.getView(R.id.ratingBar);
        ratingBar.setCountSelected((int)item.getCommentsStars());

    }

}
