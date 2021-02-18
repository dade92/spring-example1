package com.springexample.webapp.data;

public class RetrieveUserResponse {
    private long id;
    private String username;
    private String address;

    public RetrieveUserResponse(long id, String username, String address) {
        this.id = id;
        this.username = username;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
