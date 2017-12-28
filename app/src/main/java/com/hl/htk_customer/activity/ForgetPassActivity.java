package com.hl.htk_customer.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.receiver.SMSReceiver;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.account_et)
    EditText accountEt;
    @Bind(R.id.authCode_et)
    EditText authCodeEt;
    @Bind(R.id.getAuthCode_text)
    TextView getAuthCodeText;
    @Bind(R.id.newPass_text)
    EditText newPassText;
    @Bind(R.id.passWord2_et)
    EditText passWord2Et;
    @Bind(R.id.over_text)
    TextView overText;

    private TimeCount time;
    private String phoneNumber = ""; //手机号
    private String authCode = "";  //验证码
    private String pass1 = "";
    private String pass2 = "";

    private SMSReceiver smeReceiver;
    private IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_forget_pass);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {

        smeReceiver = new SMSReceiver(authCodeEt);
        filter = new IntentFilter(SMSReceiver.SMS_RECEIVED_ACTION);
        this.registerReceiver(smeReceiver, filter);
        tvTitle.setText(getResources().getText(R.string.forget_pass));
        llReturn.setOnClickListener(this);
        getAuthCodeText.setOnClickListener(this);
        overText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.getAuthCode_text:

                phoneNumber = accountEt.getText().toString().trim();

                if (phoneNumber.length() != 11) {
                    showMessage("请输入正确格式的手机号");
                    return;
                }
                authCodeEt.requestFocus(); //获取焦点
                getAuthCode(phoneNumber);
                startTimeCount();
                break;
            case R.id.over_text:
                phoneNumber = accountEt.getText().toString().trim();
                authCode = authCodeEt.getText().toString().trim();
                pass1 = newPassText.getText().toString().trim();
                pass2 = passWord2Et.getText().toString().trim();
                if (phoneNumber.length() != 11) {
                    showMessage("请输入正确格式的手机号");
                    return;
                } else if (TextUtils.isEmpty(authCode)) {
                    showMessage("请输入验证码");
                    return;
                } else if (pass1.length() < 6 || pass1.length() > 12) {
                    showMessage("请输入6-12位密码");
                    return;
                } else if (!pass1.equals(pass2)) {
                    showMessage("两次密码不一致");
                    return;
                }
                findPass(phoneNumber, authCode, pass1);
                break;
            default:
                break;

        }
    }


    private void getAuthCode(String phoneNumber) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("phone", Long.parseLong(phoneNumber));
        AsynClient.get(MyHttpConfing.getAuth + phoneNumber, this, params, new GsonHttpResponseHandler() {
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

                CommonMsg cErrorMsg = UiFormat.getCommonMsg(rawJsonResponse);
                showMessage(cErrorMsg.getMessage());

            }
        });

    }

    private void startTimeCount() {
        //获取验证码
        getAuthCodeText.setEnabled(false);
        long endTime = 60 * 1000;
        time = new TimeCount(endTime, 1000);
        time.start();
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            getAuthCodeText.setEnabled(true);
            getAuthCodeText.setText("重新验证");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            getAuthCodeText.setEnabled(false);
            getAuthCodeText.setText(millisUntilFinished / 1000 + "s");
        }

    }

    private void findPass(String phoneNumber, String authCode, String passWord) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("phone", phoneNumber);
        params.put("code", authCode);
        params.put("password", passWord);
        AsynClient.post(MyHttpConfing.findPass, this, params, new GsonHttpResponseHandler() {
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
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                showMessage(commonMsg.getMessage());

                if (commonMsg.getCode() == 100) {
                    finish();
                }

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (smeReceiver != null) {
            this.unregisterReceiver(smeReceiver);
        }
    }


}
