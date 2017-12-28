package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.dialog.UpdateDialog;
import com.hl.htk_customer.entity.UpDataEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.MyUtils;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.utils.UpData;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * 版本更新
 */

public class VersionActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.version_toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_logo03)
    ImageView ivLogo03;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.tv_upData)
    TextView tvUpData;

    private UpdateDialog mUpdateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_version);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        setSupportActionBar(toolbar);
        title.setText(getResources().getText(R.string.version_title));
        tvVersion.setText(String.format(getString(R.string.join_version_code) , MyUtils.getVersion(VersionActivity.this)));
        tvUpData.setOnClickListener(this);
        back.setOnClickListener(this);
        mUpdateDialog   = new UpdateDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_upData:
                upData();
                break;
            default:
                break;
        }
    }


    private void upData() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("appId", "20170704");
        params.put("versionNumber", MyUtils.getVersion(this));
        AsynClient.post(MyHttpConfing.upDataUrl, this, params, new GsonHttpResponseHandler() {
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
                UpDataEntity upDataEntity = gson.fromJson(rawJsonResponse, UpDataEntity.class);

                if (upDataEntity.getCode() == 100) {

                    if (upDataEntity.getData() == null) {
                        showMessage(upDataEntity.getMessage());
                    }
                    else {
                        showDialog(upDataEntity.getData().getUploadLog(),upDataEntity.getData().getDownloadUrl());
                    }

                }


            }
        });

    }


    private void showDialog(String content, final String url) {

        mUpdateDialog.show();
        mUpdateDialog.setCancelable(false);
        TextView tv_content = (TextView) mUpdateDialog.findViewById(R.id.tv_content);
        TextView tv_cancel = (TextView) mUpdateDialog.findViewById(R.id.tv_cancel);
        TextView tv_ok = (TextView) mUpdateDialog.findViewById(R.id.tv_ok);

        tv_content.setText(content);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateDialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateDialog.dismiss();
                UpData upData = new UpData(VersionActivity.this, url);
                upData.checkUpdate();
            }
        });

    }


}
