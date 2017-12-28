package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/11/6.
 */

public class JoinMembersQRCodeEntity {

    /**
     * code : 100
     * message : 成功
     * data : {"qrImgUrl":"http://120.27.5.36:8080/htkApp/upload/shop/QRCode/shop_1.png","downloadQrImgUrl":""}
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
         * qrImgUrl : http://120.27.5.36:8080/htkApp/upload/shop/QRCode/shop_1.png
         * downloadQrImgUrl :
         * isCollect:0 没有加入  1已经加入
         */

        private String qrImgUrl;
        private String downloadQrImgUrl;
        private int isCollect;

        public String getQrImgUrl() {
            return qrImgUrl;
        }

        public void setQrImgUrl(String qrImgUrl) {
            this.qrImgUrl = qrImgUrl;
        }

        public String getDownloadQrImgUrl() {
            return downloadQrImgUrl;
        }

        public void setDownloadQrImgUrl(String downloadQrImgUrl) {
            this.downloadQrImgUrl = downloadQrImgUrl;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }
    }
}
