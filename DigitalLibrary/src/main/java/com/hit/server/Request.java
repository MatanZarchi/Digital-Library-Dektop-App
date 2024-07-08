package com.hit.server;

public class Request <V> {
    private String action;
    private V body;

    public static final String ADD = "Add Book";
    public static final String GET = "Get Book";
    public static final String SHOW = "Show Books";

    public Request(String action, V body){
        this.action = action;
        this.body = body;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public V getBody() {
        return body;
    }

    public void setBody(V body) {
        this.body = body;
    }
}
