package com.hl.htk_customer.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.CouponEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CouponAdapter extends BaseQuickAdapter<CouponEntity.DataBean, BaseViewHolder> {

    private int mSelectPos = -1;
    private int tag = 0;

    private double mGetProductPrice;

    public CouponAdapter(@LayoutRes int layoutResId, @Nullable List<CouponEntity.DataBean> data, int tag, double productPrice) {
        super(layoutResId, data);
        this.tag = tag;

        this.mGetProductPrice = productPrice;

        if (data != null) {
            for (CouponEntity.DataBean entity : data) {
                entity.setSelect(false);
            }
        }
    }

    @Override
    public void setNewData(@Nullable List<CouponEntity.DataBean> data) {
        if (data != null) {
            for (CouponEntity.DataBean entity : data) {
                entity.setSelect(false);
            }
        }
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponEntity.DataBean item) {
        String newMessageInfo = "<font color='red'>" + "<small>" + "￥" + "</small>"
                + "</font>" + "<font color= 'red'>" + "<big>" + "<big>" + item.getTMoney() + "</big>" + "</big>" + "</font>";

        helper.setText(R.id.tv_item_coupon_offer, Html.fromHtml(newMessageInfo))
                .setText(R.id.tv_item_coupon_full_amount, String.format(mContext.getString(R.string.join_full_amount_preferential), (int) item.getTUseMoney()))
                .setText(R.id.tv_item_coupon_name, item.getTName())
                .setText(R.id.quantity, "x"+item.getQuantity())
                .setText(R.id.tv_item_coupon_content, String.format(mContext.getString(R.string.join_coupon_content), item.getTExpiration(), item.getTUsePhone()));


        View view = helper.getView(R.id.tv_item_coupon_check);
        if (tag == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

        checkIfCanUse(helper,item,view);

    }


    /**
     * @modified by 马鹏昊
     * @date 2018.1.3
     * @desc 根据满减限制来把不可用的优惠券设置为灰色
     */
    private void checkIfCanUse(BaseViewHolder helper,CouponEntity.DataBean item,View view){
        View unUsedTip = helper.getView(R.id.unUsedTip);
        double tUseMoney = item.getTUseMoney();
        String expirationStr = item.getTExpiration();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expirationDate = null;
        try {
            expirationDate = dateFormat.parse(expirationStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        int i = expirationDate.compareTo(currentDate);//i<0表示前者在后者前边
        if (tag != 0) {//tag==0的时候是从我的页进去的所以不需要根据满减限制来显示
            boolean ifIllegalPrice = mGetProductPrice < tUseMoney;//是否符合满减条件
            boolean ifIllegalDate = (i<=0);//是否符合满减条件
            if (ifIllegalPrice||ifIllegalDate) {
                helper.getConvertView().setBackgroundColor(Color.parseColor("#dcdcdc"));
                view.setVisibility(View.GONE);
                unUsedTip.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
                unUsedTip.setVisibility(View.GONE);
                helper.getConvertView().setBackgroundColor(Color.WHITE);
            }
        }
    }

    public int getmSelectPos() {
        return mSelectPos;
    }

    public void setmSelectPos(int mSelectPos) {
        this.mSelectPos = mSelectPos;
    }
}
