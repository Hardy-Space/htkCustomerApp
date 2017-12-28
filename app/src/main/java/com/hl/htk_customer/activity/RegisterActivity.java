package com.hl.htk_customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.model.CErrorMsg;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.receiver.SMSReceiver;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.hl.htk_customer.R.string.account;

/**
 * Created by Administrator on 2017/6/19.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.tv_getAuthCode)
    TextView tvGetAuthCode;
    @Bind(R.id.ll_phoneNumber)
    LinearLayout llPhoneNumber;
    @Bind(R.id.view_line1)
    View viewLine1;
    @Bind(R.id.et_authCode)
    EditText etAuthCode;
    @Bind(R.id.ll_authCode)
    LinearLayout llAuthCode;
    @Bind(R.id.view_line2)
    View viewLine2;
    @Bind(R.id.et_passWord1)
    EditText etPassWord1;
    @Bind(R.id.ll_passWord1)
    LinearLayout llPassWord1;
    @Bind(R.id.view_line3)
    View viewLine3;
    @Bind(R.id.et_passWord2)
    EditText etPassWord2;
    @Bind(R.id.ll_passWord2)
    LinearLayout llPassWord2;
    @Bind(R.id.view_line4)
    View viewLine4;
    @Bind(R.id.tv_mark)
    TextView tvMark;
    @Bind(R.id.btn_register)
    Button btnRegister;
    private TimeCount time;
    private String phoneNumber = ""; //手机号
    private String authCode = "";  //验证码
    private String pass1 = "";
    private String pass2 = "";

    private SMSReceiver smeReceiver;
    private IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();

    }

    private void initWidget() {

        smeReceiver = new SMSReceiver(etAuthCode);
        filter = new IntentFilter(SMSReceiver.SMS_RECEIVED_ACTION);
        this.registerReceiver(smeReceiver, filter);

        tvTitle.setText(getResources().getText(R.string.register));
        llReturn.setOnClickListener(this);
        tvGetAuthCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_getAuthCode:
                //获取验证码
                //  startTimeCount();
                phoneNumber = etPhoneNumber.getText().toString().trim();

                if (phoneNumber.length() != 11) {
                    showMessage("请输入正确格式的手机号");
                    return;
                }
                etAuthCode.requestFocus(); //获取焦点
                etAuthCode.setText("");
                etAuthCode.setHint("等待自动填充");
                getAuthCode(phoneNumber);
                startTimeCount();

                break;
            case R.id.btn_register:
                //注册

                phoneNumber = etPhoneNumber.getText().toString().trim();
                authCode = etAuthCode.getText().toString().trim();
                pass1 = etPassWord1.getText().toString().trim();
                pass2 = etPassWord2.getText().toString().trim();

                if (phoneNumber.length() != 11) {
                    showMessage("请输入正确格式的手机号");
                    return;
                } else if (TextUtils.isEmpty(authCode)) {
                    showMessage("请输入验证码");
                    return;
                }
                if (pass1.length() < 6 || pass1.length() > 12) {
                    showMessage("请输入6-12位密码");
                    return;
                } else if (!pass1.equals(pass2)) {
                    showMessage("两次密码不一致");
                    return;
                }

                register(phoneNumber, authCode);
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
        tvGetAuthCode.setEnabled(false);
        long endTime = 60 * 1000;
        time = new TimeCount(endTime, 1000);
        time.start();
    }

    private void register(final String phoneNumber, String authCode) {
        //注册
        RequestParams params = AsynClient.getRequestParams();
        params.put("phone", Long.parseLong(phoneNumber));
        params.put("valCode", authCode);
        params.put("password", pass1);
        AsynClient.post(MyHttpConfing.register, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                Log.i(TAG, "onFailure: " + rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                CommonMsg commonMsg = UiFormat.getCommonMsg(rawJsonResponse);
                Log.i(TAG, "onSuccess: " + rawJsonResponse);

                if (commonMsg.getCode() == 100) {
                    initLoginData(commonMsg);
                    finishLogin();
                } else {
                    showMessage(commonMsg.getMessage());
                }
            }
        });

    }

    private static final int JPUSH_ONE = 10000;

    private void initLoginData(CommonMsg commonMsg) {
        UserInfoManager userInfoManager = new UserInfoManager(mContext);
        userInfoManager.setISLOGIN(true);
        userInfoManager.setToken(commonMsg.getData().toString());

        //发送信息，刷新我的界面数据
        EventBus.getDefault().post(new RefreshInfoEntity(true));

        //登录完毕,设置JPUSH的Alias识别
        String alias = commonMsg.getData().toString().replaceAll("-", "");
        alias = alias.trim();
        Log.i(TAG, "onSuccess: " + alias);
        JPushInterface.setAlias(MyApplication.getContext() , JPUSH_ONE  , alias);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tvGetAuthCode.setEnabled(true);
            tvGetAuthCode.setText("重新验证");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tvGetAuthCode.setEnabled(false);
            tvGetAuthCode.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smeReceiver != null) {
            this.unregisterReceiver(smeReceiver);
        }
    }

    private void finishLogin() {

        List<Activity> activities = MyApplication.get();
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity instanceof LoginActivity || activity instanceof RegisterActivity) {
                if (activity != null) {
                    MyApplication.activities.remove(i);
                    activity.finish();
                    break;
                }
            }
        }

    }

}
