package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

@MappedSuperclass
public abstract class User extends PersistentWithoutId {

    @Column(name = "password")
    private String password;

    @Column(name = "language")
    private String language;

    @Column(name = "token")
    private String token;

    @Column(name = "active")
    private boolean active;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active = active;
    }


}
