package com.xcjy.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcToken implements AuthenticationToken {

    private String username;

    private String password;

    public UpcToken() {
    }

    public UpcToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
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
}
