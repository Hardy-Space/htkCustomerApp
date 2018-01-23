package com.hl.htk_customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.RedeemEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */

public class RedeemAdapter extends BaseQuickAdapter<RedeemEntity.DataBean , BaseViewHolder> {

    public RedeemAdapter(@LayoutRes int layoutResId, @Nullable List<RedeemEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedeemEntity.DataBean item) {
        helper.addOnClickListener(R.id.tv_redeem_convert)
                .setText(R.id.tv_redeem_name , String.format(mContext.getString(R.string.join_redeem_title) , item.getTMoney()))
                .setText(R.id.tv_redeem_integration , String.format(mContext.getString(R.string.join_redeem_integration) , item.getIntegralValue()) )
                .setText(R.id.tv_redeem_limit , String.format(mContext.getString(R.string.join_redeem_limit) , item.getTUseMoney()));

        //兑换按钮
        View btn = helper.getView(R.id.tv_redeem_convert);
        View outOfDateTip = helper.getView(R.id.outOfDateTip);


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
        boolean ifIllegalDate = (i <= 0);//是否在有效期内
        if (ifIllegalDate){
            btn.setEnabled(false);
            outOfDateTip.setVisibility(View.VISIBLE);
        }else{
            btn.setEnabled(true);
            outOfDateTip.setVisibility(View.GONE);
        }

    }
}
