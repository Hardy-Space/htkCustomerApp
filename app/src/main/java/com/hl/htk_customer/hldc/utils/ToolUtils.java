package com.hl.htk_customer.hldc.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asus on 2017/11/1.
 */

public class ToolUtils {

    private static String mToken = "";
    private static String mOrderNumber;

    public static String getToken() {
        return mToken;
    }

    public static void setToken(String token) {
        mToken = token;
    }

    public static String getOrderNumber(){
        return mOrderNumber;
    }

    public static void setOrderNumber(String orderNumber){
        mOrderNumber = orderNumber;
    }

    public static String getJsonParseResult(String result){
        Log.d("ToolUtils","getJsonParseResult()" + result);
        //JSON 格式
        //解析最外层JSON
        JSONObject object = null;
        int state = 0;
        String result1 = "";
        String msg = "";
        try {
            object = new JSONObject(result);
            state = object.optInt("code");
            result1 = object.optString("data");
            msg = object.optString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result1;
    }

    public static int getNetBackCode(String responseString){
        JSONObject obj = null;
        int state = 0;
        try {
            obj = new JSONObject(responseString);
            state = obj.getInt("code");
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }
}
