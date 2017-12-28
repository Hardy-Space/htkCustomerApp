package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CommonMsg {


    /**
     * code : 100
     * message : 短信已发送，请注意查收！==成功
     * data : null
     */

    private int code;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
