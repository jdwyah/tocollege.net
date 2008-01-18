package com.apress.progwt.client.domain.dto;

import java.io.Serializable;

import com.apress.progwt.client.domain.User;

public class UserAndToken implements Serializable {

    private User user;
    private String token;

    public UserAndToken() {
    }

    public UserAndToken(User user, String token) {
        super();
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
