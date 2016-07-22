package com.lhd.autocode.multithreads;

/**
 * Created by lvhaodong on 2016/7/20.
 */
public class Message {

    private boolean isSuccess;

    private String message;

    private Object body;

    public static Message success(String message){
        return new Message(true, message, null);
    }

    public static Message fail(String message, Object cause){
        return new Message(false, message, cause);
    }

    public Message(boolean isSuccess, String message, Object body) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.body = body;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
