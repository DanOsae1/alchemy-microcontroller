package com.osaebros.server;

public class AlchemyRequest {
    String id;

    public AlchemyRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AlchemyRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
