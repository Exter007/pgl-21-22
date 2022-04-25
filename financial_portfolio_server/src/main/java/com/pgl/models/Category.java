package com.pgl.models;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
public class Category extends Persistent{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color;

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
    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
