package com.hit.digitallibrary;

public class Response<T> {
    public static final String ACK = "ACK";     // acknowledged
    public static final String NACK = "NACK";   // not acknowledged
    private String status;

    private T body;

    public Response(String status, T body) {
        this.status = status;
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
