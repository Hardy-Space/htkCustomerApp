package com.hl.htk_customer.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.base_lib.dialog.BaseNiceDialog;
import com.example.base_lib.dialog.NiceDialog;
import com.example.base_lib.dialog.ViewConvertListener;
import com.example.base_lib.dialog.ViewHolder;
import com.hl.htk_customer.R;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyUtils;


/**
 * Created by Administrator on 2017/6/13.
 */

public   abstract class BaseFragment  extends android.support.v4.app.Fragment {

    public static final String TAG = "BaseFragment";

    public Context mContext;

    protected boolean isVisible = false;
    protected MyApplication app;
    private BaseNiceDialog mLoadingDialog;
    private BaseNiceDialog mChangingDialog;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        app = MyApplication.get(BaseFragment.this.getActivity());
        super.onCreate(savedInstanceState);
        mContext = MyApplication.getContext();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyInitData();
        } else {
            isVisible = false;
        }
    }


    public abstract void lazyInitData();

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    /**
     * 显示加载动画
     */
    public void showLoading(){
        mLoadingDialog = NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0.3f)
                .setOutCancel(false)
                .show(getFragmentManager());
    }

    /**
     * 隐藏加载动画
     */
    public void hideLoading(){
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
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
               .show(getFragmentManager());
    }

    public void hideChangeDialog(){
        if (mChangingDialog != null)
            mChangingDialog.dismiss();
    }

}
