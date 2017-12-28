package com.hl.htk_customer.entity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/6/13.
 */

public class LoginStateEntity {


    private int userId;
    private String address = "";

    private String latitude;
    private String longitude;

    private String token = "";

    private boolean passWordStatus = false;


    Context mContext;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    public LoginStateEntity(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("saveinfo", Activity.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    public int getUserId() {
        userId = mSharedPreferences.getInt("userId", -1);
        return userId;
    }

    public void setUserId(int userId) {
        mEditor.putInt("userId", userId);
        mEditor.commit();
    }

    public String getAddress() {
        address = mSharedPreferences.getString("address", "");
        return address;
    }

    public void setAddress(String address) {
        // this.address = address;
        mEditor.putString("address", address);
        mEditor.commit();
    }


    public String getLatitude() {
        latitude = mSharedPreferences.getString("latitude", "");
        return latitude;
    }

    public void setLatitude(String latitude) {
        //this.latitude = latitude;
        mEditor.putString("latitude", latitude);
        mEditor.commit();
    }

    public String getLongitude() {
        longitude = mSharedPreferences.getString("longitude", "");
        return longitude;
    }

    public void setLongitude(String longitude) {
        //this.longitude = longitude;
        mEditor.putString("longitude", longitude);
        mEditor.commit();
    }


    public String getToken() {
        token = mSharedPreferences.getString("token", "");
        return token;
    }

    public void setToken(String token) {
        //   this.token = token;
        mEditor.putString("token", token);
        mEditor.commit();
    }


    public boolean isPassWordStatus() {
        passWordStatus = mSharedPreferences.getBoolean("passWordStatus", false);
        return passWordStatus;
    }

    public void setPassWordStatus(boolean passWordStatus) {
        // this.passWordStatus = passWordStatus;
        mEditor.putBoolean("passWordStatus", passWordStatus);
        mEditor.commit();
    }


}
