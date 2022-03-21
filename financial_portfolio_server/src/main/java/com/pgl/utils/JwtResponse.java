package com.pgl.utils;


import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String login;
    private List<String> roles;
    private Object user;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String login, List<String> roles) {
        this.token = accessToken;
        this.login = login;
        this.roles = roles;
    }

    public JwtResponse(String accessToken, String login, List<String> roles, Object user) {
        this.token = accessToken;
        this.login = login;
        this.roles = roles;
        this.user = user;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
