package com.hl.htk_customer.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hl.htk_customer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 作者 龙威陶
 * 说明
 * 创建时间 2017/3/23
 * 公司名称 百迅科技
 * 描述
 */
public class UpData {

    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 需要更新 */
    private static final int UPDATE = 3;
    /* 不需要更新 */
    private static final int NO_UPDATE = 4;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private TextView tv_cancel;
    private String uri = "";
    private String name = "";
   // private List<DownNewFileEntitiy> all = new ArrayList<>();
    private int fileflag;        ////文件类型1文件夹2文本3图片4doc5excle6pdf
   // private KqApplication app;
    private int fid;
    String paths = "";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:

                    installApk();
                    break;
                case UPDATE:
                    // 显示提示对话框
                    showNoticeDialog();
                    break;
                case NO_UPDATE:
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpData(Context mContext, String uri) {
        this.mContext = mContext;
        this.uri = uri;

    }

    //xiazia
    public void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(UPDATE);
            }
        }).start();
    }


    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {

        showDownloadDialog();

        // 构造对话框
     /*   View headerView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_plus_header, null);// 标题布局
        TextView titleview = (TextView) headerView// 显示标题
                .findViewById(R.id.fristTitle);
        titleview.setText("下载中");
        View convertView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_goods_continue, null);// 内容布局
        View footerView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_plus_footer_btn, null);// 按钮布局
        Button cancelBtn = (Button) footerView.findViewById(R.id.btn_dialog_cancel);
        cancelBtn.setVisibility(View.GONE);
        TextView text = (TextView) convertView
                .findViewById(R.id.goods_shortage);
        text.setText("下载中");
        // 稍后更新
      *//*  if (mHashMap != null) {
            String isForce = mHashMap.get("isForce");
            if (isForce != null && "0".equals(isForce)) {
                cancelBtn.setVisibility(View.VISIBLE);
            }
        }*//*
        DialogPlus plus = DialogPlus
                .newDialog(mContext)
                .setContentHolder(new ViewHolder(convertView))
                .setGravity(Gravity.CENTER)
                .setHeader(headerView)
                .setFooter(footerView)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.btn_dialog_ok:
                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                                // 显示下载对话框
                                showDownloadDialog();
                                break;
                            case R.id.btn_dialog_cancel:
                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).setCancelable(false).create();
        plus.show();*/

    }


    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        //  builder.setTitle("打开文件");

        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.updata, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
        builder.setView(v);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadDialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });


     /*   builder.setNegativeButton("取消下载",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 设置取消状态
                        cancelUpdate = true;
                    }
                });*/


        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }


    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }


    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(uri);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    //      String ss = uri.substring(uri.lastIndexOf(".") + 1).toLowerCase();
                    File apkFile = new File(mSavePath, "htk_customer.apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            //    String s = uri.substring(uri.lastIndexOf(".") + 1).toLowerCase();
                            //   name = name + "." + s;
                            paths = mSavePath + "/htk_customer.apk";
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);

                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    }

    ;


    /*
    * 安装下载的apk文件
    */
    private void installApk() {
        File file = new File(paths);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());

    }

}
