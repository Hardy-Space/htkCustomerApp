package com.hl.htk_customer.adapter;

import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.OrderListEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 *
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderListEntity.DataBean , BaseViewHolder> {


    public OrderListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListEntity.DataBean item) {

        //如果时间格式多出毫秒数则去掉
        String orderTime = "";
        if (item.getOrderTime().length()>19) {
           orderTime = item.getOrderTime().substring(0,19);
        }else {
            orderTime = item.getOrderTime();
        }

        helper.setText(R.id.item_order_title , item.getShopName())
                .setText(R.id.item_order_time , orderTime)
                .setText(R.id.item_order_money , String.format("￥%.2f" , item.getOrderAmount()))
                .addOnClickListener(R.id.item_order_come_again);

        ImageView icon = helper.getView(R.id.item_order_icon);
        icon.setImageURI(Uri.parse(item.getLogoUrl()));

        TextView again = helper.getView(R.id.item_order_come_again);

        String orderType = "";
        switch (item.getMark()){
            case 0: //外卖订单
                orderType = "外卖";
                setWM(helper , item);
                again.setVisibility(View.VISIBLE);
                break;
            case 1://团购订单
                orderType = "团购";
                setTG(helper , item);
                again.setVisibility(View.GONE);
                break;
            case 2://订坐订单
                orderType = "订座";
                setDZ(helper , item);
                again.setVisibility(View.GONE);
                break;
            case 3://自助点餐订单
                orderType = "自助点餐";
                setZZDC(helper , item);
                again.setVisibility(View.GONE);
                break;
        }
        helper.setText(R.id.item_order_number , String.format("%1$s订单：%2$s" , orderType , item.getOrderNumber()));
    }

    private void setWM(BaseViewHolder helper, OrderListEntity.DataBean item) {
        String orderState = "";
        switch (item.getOrderState()){
            case 1: //下单成功
                orderState = "等待接单";
                break;
            case 2://接单，未配送
                orderState = "等待配送";
                break;
            case 3://正在配送中
                orderState = "配送中";
                break;
            case 4://订单完成，已收货
                orderState = "订单完成";
                break;
            case 5://取消
                orderState = "订单已取消";
                break;
            case 6://催单
                orderState = "催单中";
                break;
        }
        helper.setText(R.id.item_order_state , orderState );

        if (item.getProductList() != null && item.getProductList().size() > 0){
            helper.setText(R.id.item_order_details ,
                    String.format("%1$s等%2$d件商品" , item.getProductList().get(0).getProductName() , item.getProductList().size()));
        }

        helper.setGone(R.id.item_order_details , true);
        helper.setGone(R.id.item_order_money , true);
    }

    private void setTG(BaseViewHolder helper, OrderListEntity.DataBean item) {
        String orderState = "";
        switch (item.getOrderState()){
            case 10://下单成功
                orderState = "待消费";
                break;
            case 11://已消费
                orderState = "已消费";
                break;
            case 12://已取消
                orderState = "订单已取消";
                break;
        }
        helper.setText(R.id.item_order_state , orderState );

        helper.setText(R.id.item_order_details , item.getPackageName());
        helper.setGone(R.id.item_order_details , true);
        helper.setGone(R.id.item_order_money , true);
    }

    private void setDZ(BaseViewHolder helper, OrderListEntity.DataBean item) {
        String orderState = "";
        switch (item.getStatus()){
            case 0://等待接单
                orderState = "等待接单";
                break;
            case 1://已接单
                orderState = "订座成功";
                break;
            case 2://商家拒绝接单
                orderState = "座位已满，订座失败";
                break;
        }
        helper.setText(R.id.item_order_state , orderState );
        helper.setGone(R.id.item_order_details , false);
        helper.setGone(R.id.item_order_money , false);
    }

    private void setZZDC(BaseViewHolder helper, OrderListEntity.DataBean item) {
        String orderState = "";
        switch (item.getOrderState()){
            case 2:
                orderState = "已结单";
                break;
            default:
                orderState = "未结单";
                break;
        }
        helper.setText(R.id.item_order_state , orderState );

        if (item.getProductList() != null && item.getProductList().size() > 0){
            helper.setText(R.id.item_order_details ,
                    String.format("%1$s等%2$d件商品" , item.getProductList().get(0).getProductName() , item.getProductList().size()));
        }else {
            helper.setText(R.id.item_order_details , "");
        }

    }
}
