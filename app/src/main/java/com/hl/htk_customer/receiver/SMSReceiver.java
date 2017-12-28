package com.hl.htk_customer.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公司：百迅
 * 创建者： 王金壮
 * 时间： 2017/2/8.
 * 修改：
 * 描述：读取接收到的短信的广播接收器
 **/
public class SMSReceiver extends BroadcastReceiver {
    private final EditText etv;
    private String verifyCode = "";
    public static final String TAG = "SMSReceiver";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String SMS_HEAD = "【回头客】";

    public SMSReceiver(EditText etv) {
        this.etv = etv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
            SmsMessage[] messages = getMessagesFromIntent(intent);
            for (SmsMessage message : messages) {
                Log.i(TAG, message.getOriginatingAddress() + " : " +
                        message.getDisplayOriginatingAddress() + " : " +
                        message.getDisplayMessageBody() + " : " +
                        message.getTimestampMillis());
                String smsContent = message.getDisplayMessageBody();
                Log.i(TAG + "1", smsContent);
                Log.i(TAG + "2", StringSplitNumber(smsContent));
                etv.setText(StringSplitNumber(smsContent));

                //    writeFile(smsContent);//将短信内容写入SD卡
            }
        }
    }

    private String StringSplitNumber(String smsContent) {
        String numberStr = "";
        if (!smsContent.startsWith(SMS_HEAD))
            return "";
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(smsContent);
        String result = m.replaceAll("");
        for (int i = 0; i < result.length(); i++) {
            if (numberStr.length() < 6)
                numberStr += result.substring(i, i + 1);
        }
        return numberStr;
    }

    public final SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];
        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }

    //将短信内容写到SD卡上的文件里，便于将文件pull到PC，这样可方便其它如WWW/WAP平台的自动化
    @SuppressLint("SdCardPath")
    public void writeFile(String str) {
        String filePath = "/mnt/sdcard/verifyCode.txt";
        byte[] bytes = str.getBytes();
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
