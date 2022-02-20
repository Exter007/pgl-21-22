package com.pgl.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@PropertySource("classpath:pgl-auth.properties")
@ConfigurationProperties(prefix="pgl.auth")
@Component
public class AuthServerProperties {
    /**
     * Use to enable auth server for authenticate and manage user
     */
    private boolean enable;

    /**
     * user name of and administrator of authenticate server
     */
    private String username;

    /**
     * password of and administrator of authenticate server
     */
    private String password;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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
