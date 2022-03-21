package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_PRODUCT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class FinancialProduct extends Persistent {

    @Column(name = "wording")
    private String wording;

    @Column(name = "state", nullable = false)
    private PRODUCT_STATE state;

    @ManyToMany()
    @JoinTable(name = "FinancialProductHolder_Product", joinColumns = @JoinColumn(name = "financial_product_id"),
            inverseJoinColumns = @JoinColumn(name = "financial_product_holder_id"))
    private List<FinancialProductHolder> financialProductHolders = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable = false)
    private FinancialInstitution financialInstitution;

    public FinancialProduct(PRODUCT_STATE state, FinancialInstitution financialInstitution) {
        this.state = state;
        this.financialInstitution = financialInstitution;
    }

    /**
     * Default constructor (persistent classes requirements)
     */
    public FinancialProduct() {
    }

    /** Builder for all attributes  **/
    public FinancialProduct(String wording, PRODUCT_STATE state, FinancialInstitution financialInstitution, List<FinancialProductHolder> financialProductHolders) {
        this.wording = wording;
        this.state = state;
        this.financialProductHolders = financialProductHolders;
        this.financialInstitution = financialInstitution;
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

    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }


    public enum PRODUCT_STATE{
        UNARCHIVED,
        ARCHIVED,
    }
}
