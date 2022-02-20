package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

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
}
