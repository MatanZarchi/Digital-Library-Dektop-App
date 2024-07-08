package com.hit.server;

public class Response<V> {
    public static final String ACK = "ACK";
    public static final String NACK = "NACK";
    private String status;

    private V body;

    public Response(String status, V body) {
        this.status = status;
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public V getBody() {
        return body;
    }

    public void setBody(V body) {
        this.body = body;
    }
}
