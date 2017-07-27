package com.xcjy.auth.model;

import java.io.Serializable;

/**
 * Created by tupeng on 2017/7/17.
 */
public class UpcUser implements Serializable {

    private String username;

    private String password;

    private String salt;

    public UpcUser() {
    }

    public UpcUser(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
