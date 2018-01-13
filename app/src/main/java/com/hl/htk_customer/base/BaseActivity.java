package com.hl.htk_customer.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.base_lib.dialog.BaseNiceDialog;
import com.example.base_lib.dialog.NiceDialog;
import com.example.base_lib.dialog.ViewConvertListener;
import com.example.base_lib.dialog.ViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.StateBarCompat;


/**
 * Created by Administrator on 2017/6/13.
 *
 */

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    public Context mContext;

    protected MyApplication app;
    private BaseNiceDialog mLoadingDialog;
    private BaseNiceDialog mChangingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MyApplication.getContext();
        app = MyApplication.get(this);
        MyApplication.add(this);
        StateBarCompat.compat(this, getResources().getColor(R.color.colorBlue));
    }

    /**
     * 初次进入界面的加载动画
     */
    public void showLoadingDialog() {
        mLoadingDialog = NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0.3f)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 更新数据的加载动画
     */
    public void showChangeDialog(final String msg){
        mChangingDialog = NiceDialog.init()
                .setLayoutId(R.layout.dialog_change_state)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setText(R.id.dialog_change_tv , msg );
                    }
                })
                .setWidth(300)
                .setHeight(50)
                .setDimAmount(.3f)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    public void hideChangeDialog(){
        if (mChangingDialog != null)
            mChangingDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.remove(this);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏通知栏（透明通知栏）
     */
    public void hideStateBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    /**
     * 状态栏导航栏全透明
     */
    public void setTransparentBar(boolean is){
        ActionBar actionBar = getSupportActionBar();
        if (is){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            if (actionBar != null)
                actionBar.hide();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBlue));
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlue));
            }
            if (actionBar != null)
                actionBar.show();
        }
    }

    public void goToActivity(Class cls){
        Intent mIntent = new Intent(this,cls);
        startActivity(mIntent);
    }
}
