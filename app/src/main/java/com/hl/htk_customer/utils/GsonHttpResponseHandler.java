package com.hl.htk_customer.utils;

import com.google.gson.Gson;
import com.hl.htk_customer.model.CErrorMsg;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpStatus;


public abstract class GsonHttpResponseHandler extends BaseJsonHttpResponseHandler {

    static final String TAG = "HTTP";

    protected abstract Object parseResponse(String rawJsonData) throws Throwable;

    public abstract void onFailure(int statusCode, String rawJsonData, Object errorResponse);

    public abstract void onSuccess(int statusCode, String rawJsonResponse, Object response);

    @Override
    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
        onSuccess(statusCode, rawJsonResponse, response);
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
        onFailure(statusCode, rawJsonData, errorResponse);

        boolean doLogin = false;
        if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
            // 401, do login
//            LoginManager.getInstance().login();
            doLogin = true;
        }
        if (errorResponse != null && errorResponse instanceof CErrorMsg) {
            CErrorMsg error = (CErrorMsg) errorResponse;
            if (error.message != null) {
//                if (!doLogin && error.data.code == HttpStatus.SC_UNAUTHORIZED)
//                    LoginManager.getInstance().login();
                CLog.e(TAG, "error code = " + error.code + " msg = " + (error.message != null ? error.message : ""));
            }
        }
        if (throwable != null) {
            CLog.e(TAG, "status code = " + statusCode + "\t" + throwable.getMessage());
        } else {
            CLog.e(TAG, rawJsonData);
        }
    }

    @Override
    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
        if (isFailure) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(rawJsonData, CErrorMsg.class);
            } catch (Exception e) {
                CLog.d(TAG, rawJsonData);
               CLog.e(TAG, e.toString());
            }
        } else {
            Object result = null;
            try {
                result = parseResponse(rawJsonData);
            } catch (Exception e) {
                CLog.d(TAG, rawJsonData);
                CLog.e(TAG, e.toString());
            }
            return result;
        }
        return null;
    }

}
