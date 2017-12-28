package com.hl.htk_customer.entity;

/**
 * Created by Administrator on 2017/6/28.
 */

public class AlPayEntity {


    /**
     * code : 100
     * message : 成功
     * data : {"orderNumber":"a","aliPayResponseBody":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017052607358206&biz_content=%7B%22body%22%3A%22%E8%AF%B7%E6%B1%82%E6%94%AF%E4%BB%98%E6%97%B6%E9%97%B4%3A2017-06-29+14%3A20%3A20+%2C%E9%87%91%E9%A2%9D%3A0.01%22%2C%22out_trade_no%22%3A%221706295162010237%22%2C%22product_code%22%3A%22QUICK_TAKEOUT_PAY%22%2C%22subject%22%3A%22%E5%A4%96%E5%8D%96%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=AQ%2BFSsm%2FEoCOCulJWMiAFwDQ0JH9%2B1FKa834RDy%2FfJhPiQnQt45iBN98S%2B0d%2BdHarX7zT3j8Awdv6Ss25mv6qQIZ1qFlyLXskhv9RorpzJb0p1TTxhLG2IfFNBiDeRKBii7VPnE0hvNq%2BGKDl7CQI6hv6%2BmbiMbEnDIVgKvREx5FHSK43wyUU9qyy5b5JxqJoVR99pfjPe6ZThpX8DMwtF0CkmfsoJBGRnEb7D063Lt2MKSiJGiYJ%2BqqveYQeRQc0BOYXBG6SSZAwgiAek9LY3aH1PF8dMDFga7VON0VidLksqvgmTwj7DuHOonP4Bf6ValXWXtM9wHKvjgwAe9FjQ%3D%3D&sign_type=RSA&timestamp=2017-06-29+14%3A20%3A20&version=1.0","orderBody":"请求支付时间:2017-06-29 14:20:20 ,金额:0.01"}
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
         * orderNumber : a
         * aliPayResponseBody : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017052607358206&biz_content=%7B%22body%22%3A%22%E8%AF%B7%E6%B1%82%E6%94%AF%E4%BB%98%E6%97%B6%E9%97%B4%3A2017-06-29+14%3A20%3A20+%2C%E9%87%91%E9%A2%9D%3A0.01%22%2C%22out_trade_no%22%3A%221706295162010237%22%2C%22product_code%22%3A%22QUICK_TAKEOUT_PAY%22%2C%22subject%22%3A%22%E5%A4%96%E5%8D%96%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=AQ%2BFSsm%2FEoCOCulJWMiAFwDQ0JH9%2B1FKa834RDy%2FfJhPiQnQt45iBN98S%2B0d%2BdHarX7zT3j8Awdv6Ss25mv6qQIZ1qFlyLXskhv9RorpzJb0p1TTxhLG2IfFNBiDeRKBii7VPnE0hvNq%2BGKDl7CQI6hv6%2BmbiMbEnDIVgKvREx5FHSK43wyUU9qyy5b5JxqJoVR99pfjPe6ZThpX8DMwtF0CkmfsoJBGRnEb7D063Lt2MKSiJGiYJ%2BqqveYQeRQc0BOYXBG6SSZAwgiAek9LY3aH1PF8dMDFga7VON0VidLksqvgmTwj7DuHOonP4Bf6ValXWXtM9wHKvjgwAe9FjQ%3D%3D&sign_type=RSA&timestamp=2017-06-29+14%3A20%3A20&version=1.0
         * orderBody : 请求支付时间:2017-06-29 14:20:20 ,金额:0.01
         */

        private String orderNumber;
        private String aliPayResponseBody;
        private String orderBody;
        private int orderId;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getAliPayResponseBody() {
            return aliPayResponseBody;
        }

        public void setAliPayResponseBody(String aliPayResponseBody) {
            this.aliPayResponseBody = aliPayResponseBody;
        }

        public String getOrderBody() {
            return orderBody;
        }

        public void setOrderBody(String orderBody) {
            this.orderBody = orderBody;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
