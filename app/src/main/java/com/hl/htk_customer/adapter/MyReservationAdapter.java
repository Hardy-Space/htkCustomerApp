package com.hl.htk_customer.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.MyReservationEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class MyReservationAdapter extends BaseQuickAdapter<MyReservationEntity.DataBean, BaseViewHolder> {

    public MyReservationAdapter(@LayoutRes int layoutResId, @Nullable List<MyReservationEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyReservationEntity.DataBean item) {
        helper.setText(R.id.tv_reservation_seat_order_number , String.format(mContext.getString(R.string.join_reservation_seat_order_number) , item.getOrderNumber()))
                .setText(R.id.tv_reservation_seat_order_time , String.format(mContext.getString(R.string.join_reservation_seat_order_time) , item.getOrderTime()))
                .setText(R.id.tv_reservation_user , String.format(mContext.getString(R.string.join_reservation_user) , item.getScheduledName()))
                .setText(R.id.tv_reservation_phone , String.format(mContext.getString(R.string.join_reservation_phone) , item.getSeatPhone()))
                .setText(R.id.tv_reservation_time , String.format(mContext.getString(R.string.join_reservation_time) , item.getScheduledTime()))
                .setText(R.id.tv_reservation_number_of_people , String.format(mContext.getString(R.string.join_reservation_number_of_people) , item.getSeatCount()))
                .setText(R.id.tv_reservation_seat_number , String.format(mContext.getString(R.string.join_reservation_seat_number) , item.getSeatName() != null?item.getSeatName():""));

        switch (item.getStatus()){
            case 0://等待商户接单分配座位
                helper.setText(R.id.tv_reservation_seat_order_state , "等待接单")
                        .setTextColor(R.id.tv_reservation_seat_order_state , mContext.getResources().getColor(R.color.color_orange_dark));
                break;
            case 1://商户已接单分配座位
                helper.setText(R.id.tv_reservation_seat_order_state , "订座成功")
                        .setTextColor(R.id.tv_reservation_seat_order_state , Color.GREEN);
                break;
            case 2://座位已满
                helper.setText(R.id.tv_reservation_seat_order_state , "座位已满，订座失败")
                        .setTextColor(R.id.tv_reservation_seat_order_state , Color.RED);
                break;
        }
    }
}
