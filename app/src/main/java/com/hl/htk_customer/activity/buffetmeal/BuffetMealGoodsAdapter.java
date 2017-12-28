package com.hl.htk_customer.activity.buffetmeal;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 *
 */

public class BuffetMealGoodsAdapter extends BaseQuickAdapter<BuffetMealGoodsInfo.DataBean, BaseViewHolder> {

    private Context context;

    public BuffetMealGoodsAdapter(@LayoutRes int layoutResId, @Nullable List<BuffetMealGoodsInfo.DataBean> data , Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BuffetMealGoodsInfo.DataBean item) {
        helper.setText(R.id.tv_buffet_meal_goods_title , item.getProductName())
                .setText(R.id.tv_buffet_meal_goods_sales , String.format(context.getString(R.string.join_sales_of_month) , item.getMonthlySalesVolume()))
                .setText(R.id.tv_buffet_meal_goods_price , String.format(context.getString(R.string.join_order_money) , item.getPrice()))
                .addOnClickListener(R.id.tv_buffet_meal_goods_cut)
                .addOnClickListener(R.id.tv_buffet_meal_goods_add);

        SimpleDraweeView icon = helper.getView(R.id.iv_buffet_meal_goods_icon);
        icon.setImageURI(Uri.parse(item.getImgUrl()));
    }

}
