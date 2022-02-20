package com.pgl.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pgl.db.server")
@Component
public class DataBaseProperties {
    public final static String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
    public final static String TRANSACTION_MANAGER = "transactionManager";
    public final static String PGL_DATA_SOURCE = "dataSource";

    private String username;
    private String password;
    private String url;

    private String shutDownUrl;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShutDownUrl() {
        return shutDownUrl;
    }

    public void setShutDownUrl(String shutDownUrl) {
        this.shutDownUrl = shutDownUrl;
    }
}
