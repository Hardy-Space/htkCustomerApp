package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/7/4.
 */

public class UpDataEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"id":2,"appId":"20170704","appName":"回头客用户端","clientVersion":"2.0","downloadUrl":"htkApp/upload/app/apk/1.0/htkAccount.apk","uploadLog":"\n    修复了头条新界面中无法返回的问题。\n    优化了app中的缓存信息。\n    开通了新民晚报俱乐部会员卡及电子订阅卡激活通道。\n","updateInstall":false}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * appId : 20170704
         * appName : 回头客用户端
         * clientVersion : 2.0
         * downloadUrl : htkApp/upload/app/apk/1.0/htkAccount.apk
         * uploadLog :
         修复了头条新界面中无法返回的问题。
         优化了app中的缓存信息。
         开通了新民晚报俱乐部会员卡及电子订阅卡激活通道。

         * updateInstall : false
         */

        private int id;
        private String appId;
        private String appName;
        private String clientVersion;
        private String downloadUrl;
        private String uploadLog;
        private boolean updateInstall;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getClientVersion() {
            return clientVersion;
        }

        public void setClientVersion(String clientVersion) {
            this.clientVersion = clientVersion;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getUploadLog() {
            return uploadLog;
        }

        public void setUploadLog(String uploadLog) {
            this.uploadLog = uploadLog;
        }

        public boolean isUpdateInstall() {
            return updateInstall;
        }

        public void setUpdateInstall(boolean updateInstall) {
            this.updateInstall = updateInstall;
        }
    }
}
