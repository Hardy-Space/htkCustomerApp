package com.hl.htk_customer.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.model.CommonMsg;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2017/6/27.
 */

public class PublicRequestUtil {


    public static void collection(int id, final int mark, final Context context, final ImageView imageView) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", id);
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.collection, context, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    if (mark == 1) {
                        imageView.setImageResource(R.mipmap.collection2);
                    } else {
                        imageView.setImageResource(R.mipmap.collection1);
                    }

                } else {

                }

            }
        });


    }

}
