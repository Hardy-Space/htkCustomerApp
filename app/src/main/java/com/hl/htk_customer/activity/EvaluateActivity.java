package com.hl.htk_customer.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.EvaluateDetialEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.MyRatingBar;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * <p>
 * 我要评价
 */

public class EvaluateActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.ratingBar)
    MyRatingBar ratingBar;
    @Bind(R.id.tv_evaluate_state)
    TextView tvEvaluateState;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;

    private int orderId = -1;
    private String logo = "";
    private String shopName = "";

    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_evaluate);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
        getEvaluateInfo();
    }

    private void initWidget() {

        orderId = getIntent().getIntExtra("orderId", -1);
        logo = getIntent().getStringExtra("logo");
        shopName = getIntent().getStringExtra("shopName");

        if (!"".equals(logo)) {
            image.setImageURI(Uri.parse(logo));
        }

        tvShopName.setText(shopName);

        tvTitle.setText(getResources().getText(R.string.evaluate));
        llReturn.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        ratingBar.setCountSelected(5);//默认五星好评

        ratingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                Log.i(TAG, "onChange: " + countSelected);
                ratingBar.setCountSelected(countSelected);
                evaluateDescription(countSelected);
            }
        });


    }

    /**
     * 根据评分设置具体的文字描述
     * @param countSelected 分数
     */
    private void evaluateDescription(int countSelected) {
        switch (countSelected){
            case 1:
                tvEvaluateState.setText("不满意");
                break;
            case 2:
                tvEvaluateState.setText("一般");
                break;
            case 3:
                tvEvaluateState.setText("满意");
                break;
            case 4:
                tvEvaluateState.setText("很满意");
                break;
            case 5:
                tvEvaluateState.setText("非常满意");
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_submit:

                submit_evaluate();

                break;

        }

    }


    private void getEvaluateInfo() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderId", orderId);
        AsynClient.post(MyHttpConfing.evaluateDetail, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                EvaluateDetialEntity evaluateDetialEntity = gson.fromJson(rawJsonResponse, EvaluateDetialEntity.class);

                if (evaluateDetialEntity.getCode() == 100) {
                    if (evaluateDetialEntity.getData().isComment()) {
                        initEvaluateData(evaluateDetialEntity);
                    }
                }

            }
        });

    }


    private void initEvaluateData(EvaluateDetialEntity evaluateDetialEntity) {
        EvaluateDetialEntity.DataBean data = evaluateDetialEntity.getData();
        ratingBar.setCountSelected((int)data.getCommentsStars());
        evaluateDescription((int)data.getCommentsStars());

        etContent.setText(data.getContent());
        setFalseEnable();

    }


    private void submit_evaluate() {
        showChangeDialog("加载中");
        RequestParams params = AsynClient.getRequestParams();
        params.put("commentsStars", ratingBar.getCountSelected());
        params.put("orderId", orderId);
        params.put("content", etContent.getText().toString());
        AsynClient.post(MyHttpConfing.evaluate, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
                showMessage(getString(R.string.error_change));
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100) {
                    setFalseEnable();
                    setResult(203);
                    finish();
                }else {
                    showMessage(getString(R.string.error_change));
                }

            }
        });

    }


    private void setFalseEnable() {
        etContent.setEnabled(false);
        tvSubmit.setEnabled(false);
        ratingBar.setEnabled(false);

    }


}
