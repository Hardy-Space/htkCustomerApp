package com.hl.htk_customer.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;


import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hl.htk_customer.R;

import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/6/19.
 */

public class MyUtils {

    //修改tabLayout 下划线的长度
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }

    //调用系统打电话
    public static void call(Context context, String phone) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);

    }


    //获取当前版本
    public static String getVersion(Context context) {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;

            //  return version;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }


    //截取手机号中间四位****
    public static void subPhoneNumber(String phoneNumber, TextView textView) {

        if (phoneNumber.length() == 11) {
            textView.setText(phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11));
        } else {
            textView.setText("***********");
        }

    }

    public static float getDistance(LatLng latLng1, LatLng latLng2) {

        float v = AMapUtils.calculateLineDistance(latLng1, latLng2);
        float distance = v / 1000;
        return distance;
    }


    //dp转px
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //px转dp
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }


    public static boolean pareTiem(String startTime, String endTime) {

        boolean result = false;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long ss = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60 * 60)) / (1000 * 60);
            // System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
            //  text.setText("请假天数:" + days + "天" + hours + "小时" + minutes + "分");

            if (days > 0 || hours > 1 || minutes > 15) {
                result = true;
            } else {
                result = false;
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;
    }


    public static String paraperMinute(String startTime, String endTime) {

        String result = "";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long sec = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) / (1000 * 60) - minutes * (1000 * 60)) / 1000;


            //          long sec  = (diff/1000-days*24*60*60-hours*60*60-minutes*60);

            // System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
            //  text.setText("请假天数:" + days + "天" + hours + "小时" + minutes + "分");

            if (minutes < 10 && sec < 10) {
                result = "0" + minutes + ":0" + sec;
            } else if (minutes < 10 && sec >= 10) {
                result = "0" + minutes + ":" + sec;
            } else if (minutes >= 10 && sec < 10) {
                result = minutes + ":0" + sec;
            } else {

                result = minutes + ":" + sec;
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;
    }



    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        TextView tv = (TextView) v.findViewById(R.id.text);
//        LinearLayout ll_layout = (LinearLayout) v.findViewById(R.id.ll_layout);
        tv.setText(msg);

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }


}