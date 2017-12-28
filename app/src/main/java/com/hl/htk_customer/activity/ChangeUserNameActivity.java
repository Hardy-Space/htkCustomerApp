package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.model.ChangeUserNickNameEvent;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ChangeUserNameActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.et_nickName)
    EditText etNickName;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private String nickName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_change_name);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.account));

        if (!"".equals(app.getUserInfoManager().getNickName())) {
            etNickName.setText(app.getUserInfoManager().getNickName());
        }

        llReturn.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.tv_ok:
                nickName = etNickName.getText().toString();
                if (TextUtils.isEmpty(nickName)) return;
                tvOk.setEnabled(false);
                changeNickName(nickName);
                break;
            default:
                break;

        }

    }


    private void changeNickName(final String nickName) {

        showChangeDialog("修改昵称");
        RequestParams params = AsynClient.getRequestParams();
        params.put("nickName", nickName);
        AsynClient.post(MyHttpConfing.changeNickName, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                tvOk.setEnabled(true);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                hideChangeDialog();
                CommonMsg commonMsg = UiFormat.getCommonMsg(rawJsonResponse);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100) {
                    new UserInfoManager(mContext).setNickName(nickName);
                    EventBus.getDefault().post(new ChangeUserNickNameEvent());
                    finish();
                } else {
                    if (!"".equals(app.getUserInfoManager().getNickName())) {
                        etNickName.setText(new UserInfoManager(mContext).getNickName());
                    }
                    tvOk.setEnabled(true);
                }

            }
        });

    }

}
