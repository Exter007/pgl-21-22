package com.pgl.models.extension3;

import com.pgl.models.ApplicationClient;
import com.pgl.models.Persistent;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
public class Category extends Persistent {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color;

    @ManyToOne()
    @JoinColumn(name = "applicationClient")
    private ApplicationClient applicationClient;

    /*
    @OneToOne()
    @JoinColumn(name = "app_transaction_transaction_number", nullable = false)
    private Transaction transaction;
    */

    /** Default constructor
     * (persistent classes requirements)
     */
    public Category() {
        this.color = "#FFFFFF";
    }

    /**
     * @param name the name of the category
     * @param color the color of the category
     */
    public Category(String name, String color, ApplicationClient applicationClient) {
        this.name = name;
        this.color = color;
        this.applicationClient = applicationClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
