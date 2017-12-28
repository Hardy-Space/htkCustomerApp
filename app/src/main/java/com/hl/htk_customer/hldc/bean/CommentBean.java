package com.hl.htk_customer.hldc.bean;

/**
 * Created by asus on 2017/11/7.
 */

public class CommentBean {
    int commentStar; //评论星级
    String commentContent; //评论内容
    String accountUserName; //用户名
    String commentTime; //评论时间

    public int getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(int commentStar) {
        this.commentStar = commentStar;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
