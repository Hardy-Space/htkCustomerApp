package com.hl.htk_customer.utils;


import android.content.Context;
import android.util.Log;

import com.hl.htk_customer.model.LocationInfoManager;
import com.hl.htk_customer.model.UserInfoManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by w.chen on 2014/10/11.
 */
public class AsynClient {

    private static final String TAG = "AsynClient";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, Context context, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //  addHeader();
        params.put("role", "C");  // 区分是顾客端
        params.put("token",  new UserInfoManager(context).getToken());
        client.get(url, params, responseHandler);
    }

    public static void post(String url, Context context, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.put("role", "C");  // 区分是顾客端
        params.put("token", new UserInfoManager(context).getToken());
        addHeader();
        client.setTimeout(20 * 1000);
        client.post(url, params, responseHandler);

        Log.e("syl","url==>"+url);
        Log.e("syl","params==>"+params);
    }




    public static RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        return params;
    }

    private static void addHeader() {
//        Staff user = DataManager.getInstance().getDefaultStaff();
//        if (user != null && !TextUtils.isEmpty(user.getLast_session_id())) {
//            // Log.e(TAG, user.lastSessionId);
//            client.addHeader("Session-ID", user.getLast_session_id());
//            CLog.i(TAG, "Session-ID = " + user.getLast_session_id());
//        }
//        params.put("Request-Time", new Date().getTime());
//        params.put("Auth-Key", "p!I5G8xTD?");
        client.addHeader("Request-From", "SaApp");
    }

    /*private static String getAbsoluteUrl(String relativeUrl) {
        return Const.BASE_URL + relativeUrl;
    }*/
}
