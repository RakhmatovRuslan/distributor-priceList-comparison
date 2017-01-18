package com.ruslan.pricelist.beans;

/**
 * Created by Ruslan on 1/14/2017.
 */
public class Message {
    private String content;
    private boolean isSuccess;

    public Message(String content, boolean isSuccess) {
        this.content = content;
        this.isSuccess = isSuccess;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
