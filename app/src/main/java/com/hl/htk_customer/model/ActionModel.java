package com.hl.htk_customer.model;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ActionModel {

    private int style;
    private String content;

    public ActionModel(int style, String content) {
        this.style = style;
        this.content = content;
    }


    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
