package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.etFeedBack)
    EditText etFeedBack;
    @Bind(R.id.text_num)
    TextView textNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_feedback);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.feed_back));
        tvRight.setText(getResources().getText(R.string.ok));
        llReturn.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_right:
                String s = etFeedBack.getText().toString();

                if ("".equals(s)) {
                    showMessage("请输入您要反馈的内容");
                    return;
                }
                showMessage("反馈成功");
                finish();

                break;
        }
    }
}
