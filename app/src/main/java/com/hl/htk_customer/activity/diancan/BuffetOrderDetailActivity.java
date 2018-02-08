package com.hl.htk_customer.activity.diancan;

import android.os.Bundle;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 马鹏昊
 * @desc 自助点餐订单详情页
 * @date 2018-2-8
 */
public class BuffetOrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffet_order_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_return)
    public void onViewClicked() {
        finish();
    }
}
