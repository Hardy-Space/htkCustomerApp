package com.hl.htk_customer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.DialogOnClickListener;
import com.hl.htk_customer.dialog.PromptDialog;
import com.hl.htk_customer.entity.RefreshInfoEntity;
import com.hl.htk_customer.model.ChangeUserNickNameEvent;
import com.hl.htk_customer.model.ChangeUserPhotoEvent;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * <p>
 * 个人信息
 */

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener
            ,TakePhoto.TakeResultListener , InvokeListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.iv_jt1)
    ImageView ivJt1;
    @Bind(R.id.rl_head)
    RelativeLayout rlHead;
    @Bind(R.id.iv_jt2)
    ImageView ivJt2;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.rl_nickName)
    RelativeLayout rlNickName;
    @Bind(R.id.iv_jt3)
    ImageView ivJt3;
    @Bind(R.id.rl_phoneNumber)
    RelativeLayout rlPhoneNumber;
    @Bind(R.id.iv_jt4)
    ImageView ivJt4;
    @Bind(R.id.rl_wx)
    RelativeLayout rlWx;
    @Bind(R.id.iv_jt5)
    ImageView ivJt5;
    @Bind(R.id.rl_qq)
    RelativeLayout rlQq;
    @Bind(R.id.tv_exit)
    TextView tvExit;
    @Bind(R.id.iv_head)
    SimpleDraweeView iv_head;
    @Bind(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_qq)
    TextView tvQq;
    @Bind(R.id.tv_wx)
    TextView tvWx;

    private File file;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private UserInfoManager userInfoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initWidget();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.personal_info));
        initView();

        llReturn.setOnClickListener(this);
        rlHead.setOnClickListener(this);
        rlNickName.setOnClickListener(this);
        rlPhoneNumber.setOnClickListener(this);
        rlWx.setOnClickListener(this);
        rlQq.setOnClickListener(this);
        tvExit.setOnClickListener(this);
    }

    private void initView() {

        userInfoManager = new UserInfoManager(mContext);
        iv_head.setImageURI(Uri.parse(userInfoManager.getAvaUrl()));
        tvAccount.setText(userInfoManager.getNickName());

        MyUtils.subPhoneNumber(userInfoManager.getPhone() + "", tvPhoneNumber);


        setBindData();

    }

    /**
     * 设置绑定状态文字显示
     */
    private void setBindData() {
        if (userInfoManager.isQqStatus()) {
            tvQq.setText(getResources().getText(R.string.has_bind));
        }
        else {
            tvQq.setText(getResources().getText(R.string.unbind));
        }


        if (userInfoManager.isWeChatStatus()) {
            tvWx.setText(getResources().getText(R.string.has_bind));
        }
        else {
            tvWx.setText(getResources().getText(R.string.unbind));
        }
    }

    @Subscribe
    public void onEventMainThread(RefreshInfoEntity event) {
        Log.i(TAG, "onEventMainThread: " + event.isRefresh());
        if (event.isRefresh()){
            setBindData();
        }

    }

    @Subscribe
    public void onEventMainThread(ChangeUserNickNameEvent event) {
        tvAccount.setText(userInfoManager.getNickName());
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

            RequestParams params = new RequestParams();
            switch (share_media){
                case QQ:
                    params.put("qqToken", map.get("accessToken"));
                    params.put("avatarUrl", map.get("profile_image_url"));
                    bindQQ(params);
                    break;
                case WEIXIN:
                    params.put("weChatToken", map.get("unionid"));
                    params.put("avatarUrl", map.get("profile_image_url"));
                    bindWeChat(params);
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            showMessage(throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            showMessage(getString(R.string.login_cancel));
        }
    };

    /**
     * 绑定
     */
    private void bindQQ(RequestParams params) {
        showChangeDialog("绑定");
        AsynClient.post(MyHttpConfing.bindQq, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    showMessage(commonMsg.getMessage());
                    userInfoManager.setQqStatus(true);
                    setBindData();
                } else {
                    showMessage(commonMsg.getMessage());
                }

            }
        });


    }

    /**
     * 绑定
     */
    private void bindWeChat(RequestParams params) {
        showChangeDialog("绑定");
        AsynClient.post(MyHttpConfing.bindPhoneNumber, this, params, new GsonHttpResponseHandler() {
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
                Log.i(TAG, rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    showMessage(commonMsg.getMessage());
                    userInfoManager.setWeChatStatus(true);
                    setBindData();
                } else {
                    showMessage(commonMsg.getMessage());
                }

            }
        });


    }

    /**
     * 解绑
     * @param tag 0 QQ  ， 1 WeChat
     */
    private void unBind(final int tag){
//        showChangeDialog("解绑");
        new PromptDialog(this).create("确定解绑？")
                .setListener(new DialogOnClickListener() {
                    @Override
                    public void onPositive() {
                        RequestParams params = new RequestParams();
                        params.put("flag" , tag);
                        AsynClient.post(MyHttpConfing.unBind, mContext, params, new GsonHttpResponseHandler() {
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
                                if (tag == 0){
                                    userInfoManager.setQqStatus(false);
                                }else {
                                    userInfoManager.setWeChatStatus(false);
                                }

                                setBindData();
                            }
                        });
                    }
                    @Override
                    public void onNegative() {

                    }
                });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_head:
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);

                takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());
                break;
            case R.id.rl_nickName:
                startActivity(new Intent(PersonalInfoActivity.this, ChangeUserNameActivity.class));
                break;
            case R.id.rl_phoneNumber:

                break;
            case R.id.rl_wx:
                if (!new UserInfoManager(mContext).isWeChatStatus()){
                    UMShareConfig config = new UMShareConfig();
                    config.isNeedAuthOnGetUserInfo(true);
                    UMShareAPI.get(PersonalInfoActivity.this).setShareConfig(config);
                    UMShareAPI.get(this).getPlatformInfo(PersonalInfoActivity.this , SHARE_MEDIA.WEIXIN , authListener);
                }else {
                    //解绑
                    unBind(1);
                }
                break;
            case R.id.rl_qq:
                if (!new UserInfoManager(mContext).isQqStatus()){
                    UMShareConfig config = new UMShareConfig();
                    config.isNeedAuthOnGetUserInfo(true);
                    UMShareAPI.get(PersonalInfoActivity.this).setShareConfig(config);
                    UMShareAPI.get(this).getPlatformInfo(PersonalInfoActivity.this , SHARE_MEDIA.QQ , authListener);
                }else {
                    //解绑
                    unBind(0);
                }
                break;
            case R.id.tv_exit:
                new PromptDialog(this).create(getString(R.string.login_out_prompt))
                        .setListener(new DialogOnClickListener() {
                    @Override
                    public void onPositive() {
                        new UserInfoManager(PersonalInfoActivity.this).clear();
                        EventBus.getDefault().post(new RefreshInfoEntity(true));
                        finish();
                    }

                    @Override
                    public void onNegative() {

                    }
                });
                break;
        }

    }

    private void changeHead(String oneselect) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("role", "C");  // 区分是顾客端
        params.put("token", new UserInfoManager(mContext).getToken());
        file = new File(oneselect);

        try {
            params.put("avaImgFile", file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20 * 1000);
        client.post(MyHttpConfing.changeHeadUrl, params, uploadResponseHander());

    }

    /**
     * 上传回调
     *
     * @return
     */
    private AsyncHttpResponseHandler uploadResponseHander() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                CommonMsg commonMsg = UiFormat.getCommonMsg(content);

                if (commonMsg.getCode() == 100) {
                    if (commonMsg.getData() == null) return;
                    iv_head.setImageURI(Uri.parse(commonMsg.getData().toString()));
                    new UserInfoManager(mContext).setAvaUrl(commonMsg.getData().toString());
                    EventBus.getDefault().post(new ChangeUserPhotoEvent());
                } else {
                    showMessage(commonMsg.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                showMessage("上传失败");
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess: " + result.getImage());
        Log.i(TAG, "takeSuccess: " + result.getImage().getOriginalPath());
        changeHead(result.getImage().getOriginalPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail: " + msg);
        showMessage(msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, "takeCancel: "+getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    private CropOptions getCropOptions(){

        int height= 200;
        int width= 200;

        CropOptions.Builder builder=new CropOptions.Builder();

        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

}
