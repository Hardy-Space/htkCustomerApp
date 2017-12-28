package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.JoinMembersQRCodeEntity;
import com.hl.htk_customer.entity.NormalEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.ImageLoadManager;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/30.
 */

public class JoinMembersActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_download_app_QR_code)
    ImageView ivDownloadAppQRCode;
    @Bind(R.id.iv_join_members_QR_code)
    ImageView ivJoinMembersQRCode;
    @Bind(R.id.btn_join_members_submit)
    TextView btnJoinMembersSubmit;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_members);
        ButterKnife.bind(this);

        initBar();
        init();
    }

    private void initBar() {
        title.setText("推荐好友扫码加入");
        imgBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        shopId = getIntent().getIntExtra("shopId" , 0);
        btnJoinMembersSubmit.setOnClickListener(this);
        getQRCode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_join_members_submit:
                PromptDialog.builder(this).create("确认加入")
                        .setListener(new DialogOnClickListener() {
                            @Override
                            public void onPositive() {
                                joinMembers();
                            }

                            @Override
                            public void onNegative() {

                            }
                        });
                break;
        }
    }

    private void getQRCode(){
        showLoadingDialog();
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        AsynClient.post(MyHttpConfing.getQrImgData, mContext, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideLoadingDialog();
                JoinMembersQRCodeEntity joinMembersQRCodeEntity = new Gson().fromJson(rawJsonResponse, JoinMembersQRCodeEntity.class);
                if (joinMembersQRCodeEntity.getCode() == 100){
                    ImageLoadManager.getInstance().setImage(joinMembersQRCodeEntity.getData().getQrImgUrl() , ivJoinMembersQRCode);
                    ImageLoadManager.getInstance().setImage(joinMembersQRCodeEntity.getData().getDownloadQrImgUrl() , ivDownloadAppQRCode);
                    if (joinMembersQRCodeEntity.getData().getIsCollect() == 1){
                        btnJoinMembersSubmit.setText("已加入");
                        btnJoinMembersSubmit.setBackgroundColor(getResources().getColor(R.color.black_light));
                        btnJoinMembersSubmit.setClickable(false);
                    }
                }else {
                    showMessage(joinMembersQRCodeEntity.getMessage());
                }
            }
        });
    }

    /**
     * 加入会员
     */
    private void joinMembers() {
        showChangeDialog("加入中");
        RequestParams params = new RequestParams();
        params.put("shopId" , shopId);
        AsynClient.post(MyHttpConfing.addMember, mContext, params, new GsonHttpResponseHandler() {
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
                    btnJoinMembersSubmit.setBackgroundColor(getResources().getColor(R.color.black_light));
                    btnJoinMembersSubmit.setClickable(false);
                    btnJoinMembersSubmit.setText("已加入");
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
