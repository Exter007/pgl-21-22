package com.pgl.utils;

public class Fichier {
    /**
     * The name of the file
     */
    private String name;
    /**
     * The content of the file
     */
    private String value;

    /**
     * The type of the file
     * take it in org.springframework.http.MediaType
     */
    private String type;

    public Fichier(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
