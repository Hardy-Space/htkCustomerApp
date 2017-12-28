package com.hl.htk_customer.fragment.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.ChangePassActivity;
import com.hl.htk_customer.activity.FeedBackActivity;
import com.hl.htk_customer.activity.HomeAddressActivity;
import com.hl.htk_customer.activity.LoginActivity;
import com.hl.htk_customer.activity.MessageActivity;
import com.hl.htk_customer.activity.MyCollectionActivity;
import com.hl.htk_customer.activity.PersonalInfoActivity;
import com.hl.htk_customer.activity.VersionActivity;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.entity.UserInfoNormalEntity;
import com.hl.htk_customer.model.ChangeUserNickNameEvent;
import com.hl.htk_customer.model.ChangeUserPhotoEvent;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 我的 fragment
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private SimpleDraweeView head;
    private TextView tvNickName;
    private TextView tvPhoneNumber;
    private TextView tvAddress;
    private TextView tvMyCollection;
    private TextView tvMessage;
    private TextView tvMyInfo;
    private TextView tvChangePass;
    private TextView tvVersion;
    private TextView tvFeedBack;
    private LinearLayout llMineUserInfo;

    private UserInfoManager mUserInfoManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        return view;
    }


    @Override
    public void lazyInitData() {
        if (isVisible && isFirst && isPrepared) {
            isFirst = false;
            initWidget();
        }
    }


    private void initView() {
        llMineUserInfo = view.findViewById(R.id.ll_mine_userInfo);
        head = view.findViewById(R.id.head);
        tvNickName = view.findViewById(R.id.tv_nickName);
        tvPhoneNumber = view.findViewById(R.id.tv_phoneNumber);
        tvAddress = view.findViewById(R.id.tv_address);
        tvMyCollection = view.findViewById(R.id.tv_mycollection);
        tvMessage = view.findViewById(R.id.tv_message);
        tvMyInfo = view.findViewById(R.id.tv_myInfo);
        tvChangePass = view.findViewById(R.id.tv_changePass);
        tvVersion = view.findViewById(R.id.tv_version);
        tvFeedBack = view.findViewById(R.id.tv_feedBack);

    }


    private void initWidget() {
        initView();
        initMyInfo();
        tvAddress.setOnClickListener(this);
        tvMyCollection.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvMyInfo.setOnClickListener(this);
        tvChangePass.setOnClickListener(this);
        tvVersion.setOnClickListener(this);
        tvFeedBack.setOnClickListener(this);
        llMineUserInfo.setOnClickListener(this);
    }


    @Subscribe
    public void onEventMainThread(RefreshInfoEntity event) {
        Log.i(TAG, "onEventMainThread: " + event.isRefresh());
        if (event.isRefresh())
            initMyInfo();
    }

    @Subscribe
    public void onEventMainThread(ChangeUserPhotoEvent event) {
        head.setImageURI(Uri.parse(mUserInfoManager.getAvaUrl()));
    }

    @Subscribe
    public void onEventMainThread(ChangeUserNickNameEvent event) {
        tvNickName.setText(mUserInfoManager.getNickName());
    }


    private void initMyInfo() {
        mUserInfoManager = new UserInfoManager(getContext());

        //已登录，获取个人信息
        if (mUserInfoManager.getISLOGIN())
            getMineInfo();
        else {
            tvNickName.setText("点击登录");
            tvPhoneNumber.setVisibility(View.GONE);
            head.setImageURI("");
        }


    }


    @Override
    public void onClick(View v) {
        if (!mUserInfoManager.getISLOGIN()){
            if (v.getId() == R.id.ll_mine_userInfo){
                startActivity(new Intent(getContext() , LoginActivity.class));
                return;
            }
            showMessage(getString(R.string.login_please));
            return;
        }
        switch (v.getId()) {
            case R.id.ll_mine_userInfo:
                startActivity(new Intent(getContext(), PersonalInfoActivity.class)) ;
                break;
            case R.id.tv_address:
                Intent intent = new Intent(getContext(), HomeAddressActivity.class);
                intent.putExtra("TAG", 1);
                startActivity(intent);
                break;
            case R.id.tv_mycollection:
                startActivity(new Intent(getContext(), MyCollectionActivity.class));
                break;
            case R.id.tv_message:
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case R.id.tv_myInfo:
                startActivity(new Intent(getContext(), PersonalInfoActivity.class));
                break;
            case R.id.tv_changePass:
                startActivity(new Intent(getContext(), ChangePassActivity.class));
                break;
            case R.id.tv_version:
                startActivity(new Intent(getContext(), VersionActivity.class));
                break;
            case R.id.tv_feedBack:
                startActivity(new Intent(getContext(), FeedBackActivity.class));
                break;
        }
    }

    private void getMineInfo() {

        RequestParams params = AsynClient.getRequestParams();
        Log.d(TAG,"mUserInfoManager.getToken() == >>>"+mUserInfoManager.getToken());
        Log.d(TAG,"mUserInfoManager.getPhone() == >>>"+mUserInfoManager.getPhone());
        params.put("token",mUserInfoManager.getToken());
//        params.put("phone",mUserInfoManager.getPhone());
        AsynClient.post(MyHttpConfing.userInfo, getContext() , params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                Log.d(TAG,"parseResponse==>>"+rawJsonData);
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                Log.d(TAG,"onFailure==>>>"+rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                UserInfoNormalEntity userInfoNormalEntity = gson.fromJson(rawJsonResponse, UserInfoNormalEntity.class);
                initMyInfo(userInfoNormalEntity);
            }
        });

    }

    private void initMyInfo(UserInfoNormalEntity userInfoNormalEntity) {
        UserInfoNormalEntity.DataBean data = userInfoNormalEntity.getData();

        if (data == null) return;

        //保存数据
        mUserInfoManager.setToken(data.getToken());
        mUserInfoManager.setNickName(data.getNickName());
        mUserInfoManager.setPhone(data.getPhone());
        mUserInfoManager.setPasswordStatus(data.getPasswordStatus());
        mUserInfoManager.setAvaUrl(data.getAvaUrl());
        mUserInfoManager.setLoginWay(data.getLoginWay());
        mUserInfoManager.setQqStatus(data.getQqStatus());
        mUserInfoManager.setWeChatStatus(data.getWeChatStatus());

        //读取数据
        head.setImageURI(Uri.parse(mUserInfoManager.getAvaUrl()));
        tvNickName.setText(mUserInfoManager.getNickName());
        tvPhoneNumber.setVisibility(View.VISIBLE);
        if (mUserInfoManager.getLoginWay() == 1) {
            MyUtils.subPhoneNumber(mUserInfoManager.getPhone() + "", tvPhoneNumber);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }


}
