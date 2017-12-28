package com.hl.htk_customer.hldc.http;

import android.util.Log;

import com.hl.htk_customer.hldc.utils.GsonUtils;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by asus on 2017/11/1.
 */

public abstract class JsonHandler<T> extends BaseJsonHttpResponseHandler {

    @Override
    public void onStart() {
        super.onStart();
        Log.d("JsonHandler","start Request:[" + getRequestURI() + "]");
    }

    protected T convertToEntry(String json) {
        // gson 使用不可使用接口传递,需要利用抽象类的方式
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            return GsonUtils.deserialiFromJson(json, type);
        } catch (Exception e) {
            Log.d("JsonHandler",e.getMessage());
            //打印
            e.printStackTrace();
            //上传
            //回调
            return null;
        }
    }

    @Override
    public void onCancel() {
        super.onCancel();
        Log.d("JsonHandler","onCancel:" + "[" + getRequestURI() + "]");
    }

    @Override
    public void onRetry(int retryNo) {
        Log.d("JsonHandler","Retry:" + "[" + getRequestURI() + "]");
    }
}
