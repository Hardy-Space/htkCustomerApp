package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TaoCanEvaluateEntity;
import com.hl.htk_customer.entity.WmEvaluateEntity;
import com.hl.htk_customer.widget.MyRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/1.
 */

public class TaoCanEvaluateAdapter extends BaseAdapter {

    List<TaoCanEvaluateEntity.DataBean> list;
    Context context;
    LayoutInflater layoutInflater;

    public TaoCanEvaluateAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<TaoCanEvaluateEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<TaoCanEvaluateEntity.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_evaluate, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindData(list, position);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.tv_nickName)
        TextView tvNickName;
        @Bind(R.id.ratingBar)
        MyRatingBar ratingBar;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;

        private void bindData(List<TaoCanEvaluateEntity.DataBean> list, int postion) {

            TaoCanEvaluateEntity.DataBean dataBean = list.get(postion);

            head.setImageURI(Uri.parse(dataBean.getAvaUrl()));
            tvNickName.setText(dataBean.getNickName());
            ratingBar.setCountSelected((int) dataBean.getCommentsStars());
            tvTime.setText(dataBean.getCommentTime());
            tvContent.setText(dataBean.getContent());
        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

