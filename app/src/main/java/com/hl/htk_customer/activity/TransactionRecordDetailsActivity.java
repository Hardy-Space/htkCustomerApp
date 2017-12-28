package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/3.
 */

public class TransactionRecordDetailsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_transaction_record_details_price)
    TextView tvTransactionRecordDetailsPrice;
    @Bind(R.id.tv_transaction_record_details_pay_style)
    TextView tvTransactionRecordDetailsPayStyle;
    @Bind(R.id.tv_transaction_record_details_time)
    TextView tvTransactionRecordDetailsTime;
    @Bind(R.id.tv_transaction_record_details_order_number)
    TextView tvTransactionRecordDetailsOrderNumber;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_transaction_record_details_order_type)
    TextView tvTransactionRecordDetailsOrderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_record_details);

        ButterKnife.bind(this);
        initBar();
        init();
    }

    private void initBar() {
        title.setText("详情");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        Intent intent = getIntent();
        double amount = intent.getDoubleExtra("amount" , 0);
        int payMethod = intent.getIntExtra("payMethod" , 0);
        String createTime = intent.getStringExtra("createTime");
        String orderNumber = intent.getStringExtra("orderNumber");
        int orderType = intent.getIntExtra("orderType" , 0);

        tvTransactionRecordDetailsPrice.setText(String.valueOf(amount));
        tvTransactionRecordDetailsTime.setText(createTime);
        tvTransactionRecordDetailsOrderNumber.setText(orderNumber);
        switch (payMethod){
            case 0:
                tvTransactionRecordDetailsPayStyle.setText("微信");
                break;
            case 1:
                tvTransactionRecordDetailsPayStyle.setText("支付宝");
                break;
        }
        switch (orderType){
            case 0:
                tvTransactionRecordDetailsOrderType.setText("外卖订单");
                break;
            case 1:
                tvTransactionRecordDetailsOrderType.setText("团购订单");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
