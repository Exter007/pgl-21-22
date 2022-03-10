package com.pgl.models;

import javax.persistence.*;


@MappedSuperclass
public abstract class User extends PersistentWithoutId {

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "language")
    private String language;

    @Column(name = "token")
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "active", nullable = false)
    private boolean active;


    public User(String password, String email, boolean active) {
        this.password = password;
        this.email = email;
        this.active = active;
    }

    public User(String password, String language, String token, String email, boolean active) {
        this.password = password;
        this.language = language;
        this.token = token;
        this.email = email;
        this.active = active;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
