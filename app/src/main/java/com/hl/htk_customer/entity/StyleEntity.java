package com.hl.htk_customer.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class StyleEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"id":0,"categoryName":"全部","description":"二级分类－全部","categoryUrl":null,"mark":0,"parentId":1,"show":null},{"id":37,"categoryName":"美食简餐","mark":0},{"id":38,"categoryName":"简餐便当","mark":0},{"id":39,"categoryName":"面食粥点","mark":0},{"id":40,"categoryName":"汉堡披萨","mark":0},{"id":41,"categoryName":"香锅冒菜","mark":0}]
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
         * id : 0
         * categoryName : 全部
         * description : 二级分类－全部
         * categoryUrl : null
         * mark : 0
         * parentId : 1
         * show : null
         */

        private int id;
        private String categoryName;
        private int mark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }
    }
}
