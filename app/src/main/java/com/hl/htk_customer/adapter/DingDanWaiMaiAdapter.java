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
import com.hl.htk_customer.entity.WmOrderListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19.
 */

public class DingDanWaiMaiAdapter extends BaseAdapter {

    Context context;
    List<WmOrderListEntity.DataBean> list;
    LayoutInflater layoutInflater;


    public DingDanWaiMaiAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<WmOrderListEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<WmOrderListEntity.DataBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_dingdan_waimai, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateListener.evaluaterClick(position);
            }
        });

        viewHolder.tvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waiMaiAgainListener.againClick(position);
            }
        });

        viewHolder.bindData(list, position);
        return convertView;
    }


    /*评价接口*/

    public WaiMaiEvaluateListener evaluateListener;

    public void setWaiMaiEvaluateListener(WaiMaiEvaluateListener evaluateListener) {
        this.evaluateListener = evaluateListener;
    }

    public interface WaiMaiEvaluateListener {
        public void evaluaterClick(int position);

    }


    /*再来一单接口*/
    public WaiMaiAgainListener waiMaiAgainListener;

    public void setWaiMaiAgainListener(WaiMaiAgainListener waiMaiAgainListener) {
        this.waiMaiAgainListener = waiMaiAgainListener;
    }

    public interface WaiMaiAgainListener {

        public void againClick(int position);
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
        @Bind(R.id.tv_again)
        TextView tvAgain;
        @Bind(R.id.tv_evaluate)
        TextView tvEvaluate;


        private void bindData(List<WmOrderListEntity.DataBean> list, int position) {

            WmOrderListEntity.DataBean dataBean = list.get(position);
            //image.setImageURI(Uri.parse(dataBean.getLogoUrl()));
            image.setImageURI(Uri.parse(dataBean.getLogoUrl()));
            tvShopName.setText(dataBean.getShopName());
            tvTime.setText(dataBean.getOrderTime());
            tvPrice.setText("￥：" + dataBean.getOrderAmount());

            String state = "";
            switch (dataBean.getOrderState()) {

                case 1:
                    state = "等待商家接单";
                    tvEvaluate.setVisibility(View.GONE);
                    break;
                case 2:
                    state = "商家已接单";
                    tvEvaluate.setVisibility(View.GONE);
                    break;
                case 3:
                    state = "订单派送中";
                    tvEvaluate.setVisibility(View.GONE);
                    break;
                case 4:
                    state = "订单已完成";
                    tvEvaluate.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    state = "订单已取消";
                    tvEvaluate.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

            tvState.setText(state);

            int num = 0;
            for (int i = 0; i < dataBean.getProductList().size(); i++) {
                num = num + dataBean.getProductList().get(i).getQuantity();
            }

            tvOrderContent.setText(dataBean.getOneProductName() + "等" + num + "份商品");


        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
