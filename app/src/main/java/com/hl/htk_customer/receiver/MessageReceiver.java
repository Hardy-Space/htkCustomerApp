package com.hl.htk_customer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hl.htk_customer.activity.OrderDetailActivity;
import com.hl.htk_customer.activity.TgOrderDetailActivity;
import com.hl.htk_customer.entity.CustomizeMsgEntity;
import com.hl.htk_customer.model.OrderStateChangeEvent;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/11/10.
 *
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.i(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            EventBus.getDefault().post(new OrderStateChangeEvent(bundle.getString(JPushInterface.EXTRA_MESSAGE)));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "用户点击打开了通知" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 在这里可以自己写代码去定义用户点击后的行为
            CustomizeMsgEntity msgEntity = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), CustomizeMsgEntity.class);
            switch (msgEntity.getFlag()){
                case 0: //外卖订单
                    Intent intentWm = new Intent(context, OrderDetailActivity.class);
                    intentWm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentWm.putExtra("orderId", msgEntity.getOrderId());
                    context.startActivity(intentWm);
                    break;
                case 1: // 团购订单
                    Intent intentTg = new Intent(context, TgOrderDetailActivity.class);
                    intentTg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentTg.putExtra("orderId", msgEntity.getOrderId());
                    context.startActivity(intentTg);
                    break;
                case 2: //自助点餐订单
                    Intent intentDc = new Intent(context, OrderDetailActivity.class);
                    intentDc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentDc.putExtra("orderId", msgEntity.getOrderId());
                    context.startActivity(intentDc);
                    break;
            }

        } else {
            Log.i(TAG, "Unhandled intent - " + intent.getAction());
        }

    }


}
