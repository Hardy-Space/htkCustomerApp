package com.hl.htk_customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.hldc.utils.ContactValues;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/7/4.
 */

public class ChooseLoginStyleActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_qq_login)
    TextView tvQqLogin;
    @Bind(R.id.tv_wx_login)
    TextView tvWxLogin;
    @Bind(R.id.tv_phone_login)
    TextView tvPhoneLogin;
    @Bind(R.id.tv_auth_login)
    TextView tvAuthLogin;

    private int tag = -1; //判断用户是微信登录还是qq登录    1qq  2 wx
    UMShareAPI umShareAPI;
    String token = "";
    String headUrl = "";
    String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_choose_login);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        umShareAPI = UMShareAPI.get(this);

        tvAuthLogin.setOnClickListener(this);
        tvQqLogin.setOnClickListener(this);
        tvWxLogin.setOnClickListener(this);
        tvPhoneLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qq_login:
                tag = 1;
                umShareAPI.getPlatformInfo(ChooseLoginStyleActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.tv_wx_login:
                tag = 2;
                umShareAPI.getPlatformInfo(ChooseLoginStyleActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.tv_phone_login:
                finish();
                startActivity(new Intent(ChooseLoginStyleActivity.this, LoginActivity.class));
                break;
            case R.id.tv_auth_login:
                finish();
                startActivity(new Intent(ChooseLoginStyleActivity.this, LoginAuthActivity.class));
                break;
        }

    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            Log.i(MyHttpConfing.tag ,platform.toString());
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //     Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            Log.i("TAG", data.toString());
            if (tag == 2) {
                //wx
                token = data.get("unionid");
                headUrl = data.get("profile_image_url");
                name = data.get("screen_name");

                loginUm();

            } else if (tag == 1) {
                //qq
                token = data.get("accessToken");
                headUrl = data.get("profile_image_url");
                name = data.get("screen_name");

                loginQq();
            }


        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                  Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
                   Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }


    private void loginUm() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("weChatToken", token);
        params.put("nickName", name);
        params.put("avatarUrl", headUrl);
        AsynClient.post(MyHttpConfing.UmLogin, this, params, new GsonHttpResponseHandler() {

            @Override
            public void onStart() {
                //   super.onStart();
                showLoadingDialog();
            }

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                hideLoadingDialog();
                Gson gson = new Gson();

                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {

                    if (commonMsg.getData() == null) {
                        //去绑定手机号
                        finish();
                        Intent intent = new Intent(ChooseLoginStyleActivity.this, BindPhoneActivity.class);
                        intent.putExtra("token", token);
                        intent.putExtra("headUrl", headUrl);
                        intent.putExtra("name", name);
                        intent.putExtra("tag", 0);//0wx  1qq
                        startActivity(intent);

                    } else {

                        //登录完毕
                        String alias = commonMsg.getData().toString().replaceAll("-", "");
                        alias = alias.trim();
                        JPushInterface.setAlias(getApplicationContext(), alias, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                int gg = 0;
                            }
                        });

                        String token = commonMsg.getData().toString();
                        app.getLoginState().setToken(token);
                        PreferencesUtils.putString(mContext,ContactValues.KEY_TOKEN, token);

                        finishHome();
                        startActivity(new Intent(ChooseLoginStyleActivity.this, HomeActivity.class));
                        finish();

                    }

                } else {
                    showMessage(commonMsg.getMessage());
                }

            }
        });

    }


    private void loginQq() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("qqToken", token);
        //   params.put("nickName", name);
        //  params.put("avatarUrl", headUrl);
        AsynClient.post(MyHttpConfing.QqLogin, this, params, new GsonHttpResponseHandler() {

            @Override
            public void onStart() {
                //   super.onStart();
                showLoadingDialog();
            }

            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                hideLoadingDialog();
                Gson gson = new Gson();

                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {

                    if (commonMsg.getData() == null) {
                        //去绑定手机号
                        finish();
                        Intent intent = new Intent(ChooseLoginStyleActivity.this, BindPhoneActivity.class);
                        intent.putExtra("token", token);
                        intent.putExtra("headUrl", headUrl);
                        intent.putExtra("name", name);
                        intent.putExtra("tag", 1);//0wx  1qq
                        startActivity(intent);

                    } else {

                        //登录完毕
                        String alias = commonMsg.getData().toString().replaceAll("-", "");
                        alias = alias.trim();
                        JPushInterface.setAlias(getApplicationContext(), alias, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                int gg = 0;
                            }
                        });

                        String token = commonMsg.getData().toString();
                        app.getLoginState().setToken(token);
                        PreferencesUtils.putString(mContext,ContactValues.KEY_TOKEN, token);
                        finishHome();
                        startActivity(new Intent(ChooseLoginStyleActivity.this, HomeActivity.class));
                        finish();

                    }

                } else {
                    showMessage(commonMsg.getMessage());
                }

            }
        });


    }


    private void finishHome() {

        List<Activity> activities = MyApplication.get();
        for (int i = 0; i < activities.size(); i++) {

            Activity activity = activities.get(i);
            if (activity instanceof HomeActivity) {
                if (activity != null) {
                    MyApplication.activities.remove(i);
                    activity.finish();
                    break;
                }
            }
        }

    }


}
