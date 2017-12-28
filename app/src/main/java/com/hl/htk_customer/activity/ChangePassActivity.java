package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 修改密码
 */

public class ChangePassActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.et_oldPass)
    EditText etOldPass;
    @Bind(R.id.et_newPass)
    EditText etNewPass;
    @Bind(R.id.et_newPass2)
    EditText etNewPass2;
    @Bind(R.id.btn_change)
    TextView btnChange;
    @Bind(R.id.tv_mark)
    TextView tvMark;


    private String oldPass = "";
    private String newPass = "";
    private String newPass2 = "";
    private int tag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_changpass);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initWidget();
    }

    private void initWidget() {

        tag = getIntent().getIntExtra("tag", -1);

        if (tag == 1) {
            tvMark.setVisibility(View.VISIBLE);
        } else {
            tvMark.setVisibility(View.GONE);
        }

        tvTitle.setText(getResources().getText(R.string.change_pass));
        llReturn.setOnClickListener(this);
        btnChange.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_return:
                finish();
                break;
            case R.id.btn_change:
                oldPass = etOldPass.getText().toString().trim();
                newPass = etNewPass.getText().toString().trim();
                newPass2 = etNewPass2.getText().toString().trim();

                if (oldPass.length() < 6 || oldPass.length() > 12) {
                    showMessage("请输入6-12位旧密码");
                    return;
                } else if (newPass.length() < 6 || newPass.length() > 12) {
                    showMessage("请输入6-12位新密码");
                    return;
                } else if (!newPass.equals(newPass2)) {
                    showMessage("输入的两次新密码不一致");
                    return;
                } else if (oldPass.equals(newPass)) {
                    showMessage("新密码不能与旧密码一致");
                    return;
                }
                btnChange.setEnabled(false);
                changePass(oldPass, newPass);
                break;
        }
    }


    private void changePass(String pw1, String pw2) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("oldP", pw1);
        params.put("newP", pw2);


        AsynClient.post(MyHttpConfing.changePass, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                btnChange.setEnabled(true);
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {

                CommonMsg commonMsg = UiFormat.getCommonMsg(rawJsonResponse);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100) {
                    app.getLoginState().setPassWordStatus(true);
                    finish();
                } else {
                    btnChange.setEnabled(true);
                }

            }
        });

    }

}
