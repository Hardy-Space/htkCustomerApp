package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.hldc.utils.ContactValues;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_passWord)
    EditText etPassWord;
    @Bind(R.id.btn_login)
    TextView btnLogin;
    @Bind(R.id.tv_forget_pass)
    TextView tvForgetPass;
    @Bind(R.id.iv_login_by_qq)
    TextView ivLoginByQQ;
    @Bind(R.id.iv_login_by_wx)
    TextView ivLoginByWX;
    @Bind(R.id.iv_login_by_yzm)
    TextView ivLoginByYZM;

    private static final int JPUSH_ONE = 10000;

    private String account = "";
    private String passWord = "";
    private UserInfoManager mUserInfoManager;

    private int tag; //绑定手机号时用于区分是qq还是微信 wx:0  QQ:1

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();

        UMShareConfig umShareConfig = new UMShareConfig();
        umShareConfig.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(umShareConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        UMShareAPI.get(this).release();
    }

    private void initWidget() {
        mUserInfoManager = new UserInfoManager(this);

        tvTitle.setText(getResources().getText(R.string.login));
        tvRight.setText(getResources().getText(R.string.register));
        llReturn.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgetPass.setOnClickListener(this);
        ivLoginByQQ.setOnClickListener(this);
        ivLoginByWX.setOnClickListener(this);
        ivLoginByYZM.setOnClickListener(this);
    }

    private UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            for (Map.Entry<String, String> entry : map.entrySet()) {
                Log.i(TAG, "onComplete: key = " + entry.getKey() + " , value = " + entry.getValue());
            }

            loginThirdParty(map , share_media);

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            showMessage(throwable.getMessage());
            Log.i(TAG, "onError: " + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            showMessage(getString(R.string.login_cancel));
        }
    };

    String token = "";
    private void loginThirdParty(Map<String, String> map , SHARE_MEDIA share_media) {
        final String name = map.get("name");
        final String head_url = map.get("iconurl");
        String url = "";
        RequestParams params = AsynClient.getRequestParams();
        if (share_media == SHARE_MEDIA.QQ){
            url = MyHttpConfing.QqLogin;
            tag = 1;
            token = map.get("openid");
            params.put("qqToken", token);
        }else if (share_media == SHARE_MEDIA.WEIXIN){
            url = MyHttpConfing.UmLogin;
            token = map.get("unionid");
            params.put("weChatToken", token);
            tag = 0;
        }

        AsynClient.post(url , mContext , params, new GsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                showChangeDialog("登录");
            }

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                if (commonMsg.getCode() == 100) {
                    if (commonMsg.getData() == null) {
                        //去绑定手机号
                        Intent intent = new Intent(mContext , BindPhoneActivity.class);
                        intent.putExtra("token", token);
                        intent.putExtra("headUrl", head_url);
                        intent.putExtra("name", name);
                        intent.putExtra("tag", tag);//0wx  1qq
                        startActivity(intent);
                    } else {
                        //登录完毕
                        initLoginData(commonMsg);
                    }
                    finish();
                } else {
                    showMessage(commonMsg.getMessage());
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode , resultCode , data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_forget_pass:
                startActivity( new Intent(LoginActivity.this , ForgetPassActivity.class));
                break;
            case R.id.iv_login_by_qq:
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this , SHARE_MEDIA.QQ , authListener);
                break;
            case R.id.iv_login_by_wx:
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this , SHARE_MEDIA.WEIXIN , authListener);
                break;
            case R.id.iv_login_by_yzm:
                startActivity(new Intent(this , LoginAuthActivity.class));
                break;
            default:
                break;

        }
    }


    private void login() {
        account = etAccount.getText().toString().trim();
        passWord = etPassWord.getText().toString().trim();
        if (account.length() != 11) {
            showMessage("请输入完整手机号");
            return;
        } else if (passWord.length() < 6 || passWord.length() > 12) {
            showMessage("请输入6-12位密码");
            return;
        }

        toLogin();
    }


    private void toLogin() {

        showLoadingDialog();

        RequestParams params = AsynClient.getRequestParams();

        params.put("userName", account);
        params.put("password", passWord);
        params.put("loginWay", 1);


        AsynClient.post(MyHttpConfing.login, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
                showMessage(getString(R.string.login_failed));
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                CommonMsg commonMsg = UiFormat.getCommonMsg(rawJsonResponse);
                if (commonMsg.getCode() == 100) {
                    initLoginData(commonMsg);
                    hideLoadingDialog();
                    finish();
                } else {
                    hideLoadingDialog();
                    showMessage(commonMsg.getMessage());
                }
            }
        });
    }

    private void initLoginData(CommonMsg commonMsg) {
        mUserInfoManager.setISLOGIN(true);
        mUserInfoManager.setToken(commonMsg.getData().toString());//.replaceAll("-", "")
        PreferencesUtils.putString(mContext, ContactValues.KEY_TOKEN, commonMsg.getData().toString());
        //发送信息，刷新我的界面数据
        EventBus.getDefault().post(new RefreshInfoEntity(true));

        //登录完毕,设置JPUSH的Alias识别
        String alias = commonMsg.getData().toString().replaceAll("-", "");
        alias = alias.trim();
        Log.i(TAG, "onSuccess: " + alias);
        JPushInterface.setAlias(MyApplication.getContext() , JPUSH_ONE  , alias);
    }
}
