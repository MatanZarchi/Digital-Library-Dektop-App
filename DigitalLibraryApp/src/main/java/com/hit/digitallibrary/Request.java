package com.hit.digitallibrary;

public class Request <T> {
    private String action;
    private T body;

    public static final String ADD = "Add Book";
    public static final String GET = "Get Book";
    public static final String SHOW = "Show Books";

    public Request() {}
    public Request(String action, T body){
        this.action = action;
        this.body = body;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
