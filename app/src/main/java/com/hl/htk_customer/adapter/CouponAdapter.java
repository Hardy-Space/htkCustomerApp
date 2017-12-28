package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.CouponEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CouponAdapter extends BaseQuickAdapter<CouponEntity.DataBean, BaseViewHolder> {

    private int mSelectPos = -1;
    private int tag = 0;

    public CouponAdapter(@LayoutRes int layoutResId, @Nullable List<CouponEntity.DataBean> data , int tag) {
        super(layoutResId, data);
        this.tag = tag;
        if (data != null){
            for (CouponEntity.DataBean entity : data) {
                entity.setSelect(false);
            }
        }
    }

    @Override
    public void setNewData(@Nullable List<CouponEntity.DataBean> data) {
        if (data != null){
            for (CouponEntity.DataBean entity : data) {
                entity.setSelect(false);
            }
        }
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponEntity.DataBean item) {
        String newMessageInfo = "<font color='red'>" + "<small>" +"￥" + "</small>"
                + "</font>" + "<font color= 'red'>" + "<big>" + "<big>" + item.getTMoney() + "</big>" + "</big>" + "</font>";

        helper.setText(R.id.tv_item_coupon_offer , Html.fromHtml(newMessageInfo))
                .setText(R.id.tv_item_coupon_full_amount , String.format(mContext.getString(R.string.join_full_amount_preferential) , (int)item.getTUseMoney()))
                .setText(R.id.tv_item_coupon_name , item.getTName())
                .setText(R.id.tv_item_coupon_content , String.format(mContext.getString(R.string.join_coupon_content) , item.getTExpiration() , item.getTUsePhone()));

        View view = helper.getView(R.id.tv_item_coupon_check);
        if (tag == 0){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
        }

    }

    public int getmSelectPos() {
        return mSelectPos;
    }

    public void setmSelectPos(int mSelectPos) {
        this.mSelectPos = mSelectPos;
    }
}
