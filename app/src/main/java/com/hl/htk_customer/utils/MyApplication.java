package com.hl.htk_customer.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.entity.LoginStateEntity;
import com.hl.htk_customer.model.DefaultAddress;
import com.hl.htk_customer.model.UserInfoManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import greendao.GreenDaoManager;

/**
 * Created by Administrator on 2017/6/13.
 */

public class MyApplication extends Application {

    private static Context mContext;

//    RefWatcher mRefWatcher;

    private static AMapLocation mAMapLocation;//保存定位信息

    private LoginStateEntity loginStateEntity;
    private UserInfoManager userInfoManager;
    private DefaultAddress defaultAddress;

    public static List<DianCanFenLeiListEntity.DataBean> diancanList = new ArrayList<>();


    public   static   int  shopId  = -1;

    public static MyApplication get(Context context) {

        return (MyApplication) context.getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //初始化Fresco
        Fresco.initialize(getApplicationContext());

      /*  //初始化LeakCanary内存泄漏检测工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);*/

        loginStateEntity = new LoginStateEntity(this.getApplicationContext());
        userInfoManager = new UserInfoManager(this.getApplicationContext());
        defaultAddress = new DefaultAddress(this.getApplicationContext());

        PlatformConfig.setWeixin(getString(R.string.key_wx_appid),getString(R.string.key_wx_appSecret));
        PlatformConfig.setQQZone(getString(R.string.key_qq_appid), getString(R.string.key_qq_appkey));

        UMShareAPI.get(this);

//        Config.DEBUG = true;
        //greenDao初始化
        GreenDaoManager.getInstance();

        //二维码扫描初始化
        ZXingLibrary.initDisplayOpinion(this);

    }

    public LoginStateEntity getLoginState() {
        return loginStateEntity;
    }

    public UserInfoManager getUserInfoManager() {
        return userInfoManager;
    }

    public DefaultAddress getDefaultAddress() {
        return defaultAddress;
    }


    //将 上面的状态栏隐掉
    public static void settopbar(Activity activity, View view) {
        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            view.setVisibility(View.GONE);
        }

    }


    public static List<Activity> activities = new ArrayList<>();

    public static List<Activity> get() {
        return activities;
    }

    //添加
    public static void add(Activity activity) {
        activities.add(activity);
    }

    //移除
    public static void remove(Activity activity) {
        for (int i = 0; i < activities.size(); i++) {
            Activity activity1 = activities.get(i);
            if (activity1.getLocalClassName().equals(activity.getLocalClassName())) {
                activities.remove(i);
                break;
            }
        }
    }

    /**
     * 保存定位信息
     * @return
     */
    public static AMapLocation getmAMapLocation() {
        return mAMapLocation;
    }

    /**
     * 获取定位信息
     * @param location 定位成功后拿到的定位信息
     */
    public static void setmAMapLocation(AMapLocation location) {
        mAMapLocation = location;
    }

    /**
     * SmartRefreshLayout全局设置头布局和尾布局
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }


}
