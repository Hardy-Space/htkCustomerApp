package com.hl.htk_customer.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.MyPageAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.fragment.home.DingDanFragment;
import com.hl.htk_customer.fragment.home.MineFragment;
import com.hl.htk_customer.fragment.home.TuanGouFragment;
import com.hl.htk_customer.fragment.home.WaiMaiFragment;
import com.hl.htk_customer.model.ScrollTopEntity;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.PermissionUtils;
import com.hl.htk_customer.widget.MyViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 * <p>
 * 主页
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private long exitTime = 0; //返回键触发时间

    @Bind(R.id.myViewPager)
    MyViewPager myViewPager;
    @Bind(R.id.rb1_home)
    RadioButton rb1Home;
    @Bind(R.id.rb2_home)
    RadioButton rb2Home;
    @Bind(R.id.rb3_home)
    RadioButton rb3Home;
    @Bind(R.id.rb4_home)
    RadioButton rb4Home;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;


    WaiMaiFragment waiMaiFragment;
    TuanGouFragment tuanGouFragment;
    DingDanFragment dingDanFragment;
    MineFragment mineFragment;
    List<BaseFragment> fragmentList;
    MyPageAdapter myPageAdapter;

    private int mCurrentRadio = 0;//用户当前选择的界面，默认为外卖主页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        MyApplication.setScreenWidth(width);
        MyApplication.setScreenHeight(height);



        initWidget();
    }


    private void initWidget() {

        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
        }

        rb1Home.setOnClickListener(this);
        rb2Home.setOnClickListener(this);
        rb3Home.setOnClickListener(this);
        rb4Home.setOnClickListener(this);

        waiMaiFragment = new WaiMaiFragment();
        tuanGouFragment = new TuanGouFragment();
        dingDanFragment = new DingDanFragment();
        mineFragment = new MineFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(waiMaiFragment);
        fragmentList.add(tuanGouFragment);
        fragmentList.add(dingDanFragment);
        fragmentList.add(mineFragment);
        myPageAdapter = new MyPageAdapter(fragmentList, getSupportFragmentManager());
        myViewPager.setAdapter(myPageAdapter);
        myViewPager.setOffscreenPageLimit(0);

        myViewPager.setCurrentItem(0);
        myViewPager.setTag(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb1_home:
                mCurrentRadio = 0;
                break;
            case R.id.rb2_home:
                mCurrentRadio = 1;
                break;
            case R.id.rb3_home:
                mCurrentRadio = 2;
                break;
            case R.id.rb4_home:
                mCurrentRadio = 3;
                break;
        }
        if (Integer.valueOf(myViewPager.getTag().toString()) == mCurrentRadio){
            EventBus.getDefault().post(new ScrollTopEntity(mCurrentRadio));
        }
        myViewPager.setCurrentItem(mCurrentRadio);
        myViewPager.setTag(mCurrentRadio);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showMessage("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CALL_PHONE:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    //   Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

}
