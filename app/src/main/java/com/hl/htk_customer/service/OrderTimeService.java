package com.hl.htk_customer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hl.htk_customer.model.TimeChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/10/26.
 *
 */

public class OrderTimeService extends Service {

    private static final String TAG = "OrderTimeService";
    private static final String TIME = "time";
    private static Intent intent;

    private Timer timer ;
    private long time;
    private long cutdownTime;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public static void startOrderTimeService(Context context, int time){
        Log.i(TAG, "startOrderTimeService: ");
        intent = new Intent(context, OrderTimeService.class);
        intent.putExtra(TIME , time);
        context.startService(intent);
    }

    public static void stop(Context context){
        context.stopService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        time = intent.getIntExtra(TIME , 0);
        cutdownTime = time;

        countDownTimer = new CountDownTimer(time , 999){

            @Override
            public void onTick(long millisUntilFinished) {
                EventBus.getDefault().post(new TimeChangeEvent(getTime(millisUntilFinished) , false));
                Log.i(TAG, "onTick: " + getTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new TimeChangeEvent(getTime(cutdownTime) , true));
                Log.i(TAG, "onFinish: ");
            }
        };
    }

    /**
     * 将时间转换为字符串
     * @param time 时间
     * @return 时间字符串
     */
    public String getTime(long time){
        long min = time /1000 / 60;
        long s = time /1000 % 60;

        if (min < 10){
            if (0 <= s && s <= 9)
                return String.format("0%1$d : 0%2$d" , min , s);
            return String.format("0%1$d : %2$d" , min , s);
        }else {
            if (0 <= s && s <= 9)
                return String.format("%1$d : 0%2$d" , min , s);
            return String.format("%1$d : %2$d" , min , s);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        countDownTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        Log.i(TAG, "onDestroy: ");
    }
}
