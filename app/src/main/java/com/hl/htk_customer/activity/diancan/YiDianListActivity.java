package com.hl.htk_customer.activity.diancan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.YiDianAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.DianCanEntity;
import com.hl.htk_customer.utils.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/7/20.
 */

public class YiDianListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.rl_detail)
    RelativeLayout rlDetail;
    @Bind(R.id.btn_jiacai)
    Button btnJiacai;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.btn_ok)
    Button btnOk;

    YiDianAdapter adapter;

    private double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_yidian);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        EventBus.getDefault().register(this);

        tvTitle.setText(getResources().getText(R.string.yidian_list));
        llReturn.setOnClickListener(this);
        btnJiacai.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        adapter = new YiDianAdapter(this);
        listView.setAdapter(adapter);
        adapter.setData(MyApplication.diancanList);

        setPrice();

    }


    private void setPrice() {

        price = 0;
        for (int i = 0; i < MyApplication.diancanList.size(); i++) {
            price = price + (MyApplication.diancanList.get(i).getPrice() * MyApplication.diancanList.get(i).getChooseNum());

        }
        tvPrice.setText(price + "元");

    }


    @Subscribe
    public void onEventMainThread(DianCanEntity event) {

        if (!event.isChanged()) return;
        setPrice();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.btn_jiacai:
                finish();
                break;
            case R.id.btn_ok:
                // startActivity(new Intent(YiDianListActivity.this, ConfirmZiZhuOrderActivity.class));
                Intent intent = new Intent(YiDianListActivity.this, ConfirmZiZhuOrderActivity.class);
                intent.putExtra("price", price);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
