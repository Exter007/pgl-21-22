package com.pgl.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_PRODUCT")
public class FinancialProduct extends Persistent {

    @Column(name = "wording")
    private String wording;

    @Column(name = "state")
    private PRODUCT_STATE state;

    @ManyToMany()
    @JoinTable(name = "FinancialProductHolder_Product", joinColumns = @JoinColumn(name = "financial_product_id"),
            inverseJoinColumns = @JoinColumn(name = "financial_product_holder_id"))
    private List<FinancialProductHolder> financialProductHolders = new ArrayList<>();


    public enum PRODUCT_STATE{
        UNARCHIVED,
        ARCHIVED,
    }

    public FinancialProduct() {
    }

    public FinancialProduct(String wording, PRODUCT_STATE state, String phone) {
        this();
        this.wording = wording;
        this.state = state;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public PRODUCT_STATE getState() {
        return state;
    }

    public void setState(PRODUCT_STATE state) {
        this.state = state;
    }


    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }
}
