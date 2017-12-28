package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.NormalEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EvaluationActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.edt_evaluation_suggest)
    EditText edtEvaluationSuggest;
    @Bind(R.id.tv_evaluation_submit)
    TextView tvEvaluationSubmit;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("建议评价");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId" , 0);
        tvEvaluationSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_evaluation_submit:
                if (TextUtils.isEmpty(edtEvaluationSuggest.getText().toString())){
                    showMessage("请输入建议后再提交");
                    return;
                }
                PromptDialog.builder(this).create("确认提交？")
                        .setListener(new DialogOnClickListener() {
                            @Override
                            public void onPositive() {
                                submit();
                            }

                            @Override
                            public void onNegative() {

                            }
                        });
                break;
        }
    }

    /**
     * 提交建议
     */
    private void submit() {
        showChangeDialog("提交中");
        String suggest = edtEvaluationSuggest.getText().toString();
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        params.put("content" , suggest);
        AsynClient.post(MyHttpConfing.accountSuggestRequest, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideChangeDialog();
                NormalEntity entity = new Gson().fromJson(rawJsonResponse, NormalEntity.class);
                if (entity.getCode() == 100){
                    showMessage(entity.getMessage());
                    finish();
                }else {
                    showMessage(entity.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
