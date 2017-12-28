package com.hl.htk_customer.service.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.service.OrderService;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2017/10/25.
 *
 */

public class OrderServiceImpl implements OrderService {

    private Context mContext;

    public OrderServiceImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void cancelOrder(String orderNumber , int mark) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("content", "其他原因");
        params.put("mark", mark);
        AsynClient.post(MyHttpConfing.cancelOrder, mContext, params, new GsonHttpResponseHandler() {
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
                Toast.makeText(mContext , commonMsg.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void deleteOrder() {

    }

}
