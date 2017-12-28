package com.hl.htk_customer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.utils.StateBarCompat;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 闪屏
 */

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStateBar();
        setContentView(R.layout.activity_splash);
        go();
    }

    private void go() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(StartActivity.this, HomeActivity.class));
                finish();
            }
        }).start();

    }


}
