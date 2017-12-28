package com.hl.htk_customer.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;

import com.hl.htk_customer.utils.SpUtils;

/**
 * Created by Administrator on 2017/6/23.
 */

public class UserInfoManager {

    private final String ISLOGIN = "islogin";
    private final String TOKEN = "token";
    private final String LOGINWAY = "loginWay";
    private final String NICKNAME = "nickName";
    private final String PHONE = "phone";
    private final String PASSWORDSTATUS = "passwordStatus";
    private final String AVAURL = "avaUrl";
    private final String QQSTATUS = "qqStatus";
    private final String WECHATSTATUS = "weChatStatus";

    Context mContext;

    public UserInfoManager(Context mContext) {
        this.mContext = mContext;
    }

    public Boolean getISLOGIN() {
        return (Boolean) SpUtils.get(mContext , ISLOGIN , false);
    }

    public void setISLOGIN(boolean isLogin){
        SpUtils.put(mContext , ISLOGIN , isLogin );
    }

    public String getToken() {
        return (String) SpUtils.get(mContext , "token", "");
    }

    public void setToken(String token) {
        SpUtils.put(mContext , TOKEN , token);
    }

    public int getLoginWay() {
        return (int) SpUtils.get(mContext , LOGINWAY , 1);
    }

    public void setLoginWay(int loginWay) {
        SpUtils.put(mContext , LOGINWAY , loginWay);
    }

    public String getNickName() {
        return (String) SpUtils.get(mContext , NICKNAME , "");
    }

    public void setNickName(String nickName) {
        SpUtils.put(mContext , NICKNAME , nickName);
    }

    public Long getPhone() {
        return (Long) SpUtils.get(mContext , PHONE , 0L);
    }

    public void setPhone(long phone) {
        SpUtils.put(mContext , PHONE , phone);
    }

    public boolean isPasswordStatus() {
        return (boolean) SpUtils.get(mContext , PASSWORDSTATUS , false);
    }

    public void setPasswordStatus(boolean passwordStatus) {
        SpUtils.put(mContext , PASSWORDSTATUS , passwordStatus);
    }

    public String getAvaUrl() {
        return (String) SpUtils.get(mContext , AVAURL , "");
    }

    public void setAvaUrl(String avaUrl) {
        SpUtils.put(mContext , AVAURL , avaUrl);
    }

    public boolean isQqStatus() {
        return (boolean) SpUtils.get(mContext , QQSTATUS , false);
    }

    public void setQqStatus(boolean qqStatus) {
        SpUtils.put(mContext , QQSTATUS , qqStatus);
    }

    public boolean isWeChatStatus() {
        return (boolean) SpUtils.get(mContext , WECHATSTATUS , false);
    }

    public void setWeChatStatus(boolean weChatStatus) {
        SpUtils.put(mContext , WECHATSTATUS , weChatStatus);
    }


    /**
     * 清除所有数据
     */
    public void clear(){
        SpUtils.clear(mContext);
    }
}
