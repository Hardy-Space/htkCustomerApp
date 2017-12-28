package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class MemberPlatformEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":1,"title":"@用人单位，这些职工需要体验","imgUrl":"htkApp/upload/shop/member/articleInfo/201711031031.jpg","describe_":null,"htmlContent":null,"shopId":1,"createTime":null,"detailRequestUrl":"http://192.168.0.7:8080/htkApp/API/appMemberAPI/articleDetails/{1}"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : @用人单位，这些职工需要体验
         * imgUrl : htkApp/upload/shop/member/articleInfo/201711031031.jpg
         * describe_ : null
         * htmlContent : null
         * shopId : 1
         * createTime : null
         * detailRequestUrl : http://192.168.0.7:8080/htkApp/API/appMemberAPI/articleDetails/{1}
         */

        private int id;
        private String title;
        private String imgUrl;
        private String describe_;
        private String htmlContent;
        private int shopId;
        private String createTime;
        private String detailRequestUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getDescribe_() {
            return describe_;
        }

        public void setDescribe_(String describe_) {
            this.describe_ = describe_;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDetailRequestUrl() {
            return detailRequestUrl;
        }

        public void setDetailRequestUrl(String detailRequestUrl) {
            this.detailRequestUrl = detailRequestUrl;
        }
    }
}
