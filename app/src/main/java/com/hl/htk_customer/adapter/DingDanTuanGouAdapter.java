package com.hl.htk_customer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.TgOrderListEntity;
import com.hl.htk_customer.entity.WmOrderListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19.
 */

public class DingDanTuanGouAdapter extends BaseAdapter {


    public TuanGouEvaluateListener evaluateListener;

    public void setTuanGouEvaluateListener(TuanGouEvaluateListener evaluateListener) {
        this.evaluateListener = evaluateListener;
    }

    public interface TuanGouEvaluateListener {

        public void evaluaterClick(int position);


    }


    /*再来一单接口*/
    public TuanGouAgainListener tuanGouAgainListener;

    public void setTuanGouAgainListener(TuanGouAgainListener tuanGouAgainListener) {
        this.tuanGouAgainListener = tuanGouAgainListener;
    }

    public interface TuanGouAgainListener {

        public void againClick(int position);
    }


    Context context;
    List<TgOrderListEntity.DataBean> list;
    LayoutInflater layoutInflater;


    public DingDanTuanGouAdapter(Context context) {

        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<TgOrderListEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<TgOrderListEntity.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_dingdan_tuangou, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindData(list, position);

        viewHolder.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateListener.evaluaterClick(position);
            }
        });

        viewHolder.tvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuanGouAgainListener.againClick(position);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.tv_shopName)
        TextView tvShopName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_order_content)
        TextView tvOrderContent;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_evaluate)
        TextView tvEvaluate;
        @Bind(R.id.tv_again)
        TextView tvAgain;


        private void bindData(List<TgOrderListEntity.DataBean> list, int position) {

            TgOrderListEntity.DataBean dataBean = list.get(position);
            //image.setImageURI(Uri.parse(dataBean.getLogoUrl()));
            image.setImageURI(Uri.parse(dataBean.getLogoUrl()));
            tvShopName.setText(dataBean.getShopName());
            tvTime.setText(dataBean.getOrderTime());
            tvPrice.setText("￥：" + dataBean.getOrderAmount());

            String state = "";
            switch (dataBean.getOrderState()) {

                case 10:
                    state = "订单未使用";
                    tvEvaluate.setVisibility(View.GONE);
                    break;
                case 11:
                    state = "订单已使用";
                    tvEvaluate.setVisibility(View.VISIBLE);
                    break;
                case 12:
                    state = "订单已取消";
                    tvEvaluate.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            tvState.setText(state);
            tvOrderContent.setText(dataBean.getPackageName());

        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
