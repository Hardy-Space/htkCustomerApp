package com.hl.htk_customer.hldc.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by asus on 2017/10/25.
 */

public class BaseFragment extends Fragment {

    protected Activity mActivity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String type;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        /*getActivity().overridePendingTransition(
                R.anim.umeng_fb_slide_in_from_right,
                R.anim.umeng_fb_slide_out_from_left);*/
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        /*getActivity().overridePendingTransition(
                R.anim.umeng_fb_slide_in_from_right,
                R.anim.umeng_fb_slide_out_from_left);*/
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), clz);
            if (ex != null)
                intent.putExtras(ex);
            startActivity(intent);
            if (isCloseCurrentActivity) {
                getActivity().finish();
            }
        }
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    /**
     * 打开一个Activity 默认 不关闭当前activity
     *
     * @param clz
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

}
