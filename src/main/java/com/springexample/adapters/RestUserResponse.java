package com.springexample.adapters;

public class RestUserResponse {
    private long id;
    private String username;

    public RestUserResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
