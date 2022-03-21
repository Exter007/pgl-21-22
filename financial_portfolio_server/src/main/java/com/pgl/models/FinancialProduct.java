package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Class that represent a financial product
 *
 */
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

    /** Default constructor
     * (persistent classes requirements)
     */
    public FinancialProduct() {
    }

    /** Class constructor
     *
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this product
     */
    public FinancialProduct(PRODUCT_STATE state, FinancialInstitution financialInstitution) {
        this.state = state;
        this.financialInstitution = financialInstitution;
    }

    /** Class constructor with all attributes
     *
     * @param wording a String object
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this product
     * @param financialProductHolders a List<FinancialProductHolder> that contains the financial product holders who have this product
     */
    public FinancialProduct(String wording, PRODUCT_STATE state, FinancialInstitution financialInstitution, List<FinancialProductHolder> financialProductHolders) {
        this.wording = wording;
        this.state = state;
        this.financialProductHolders = financialProductHolders;
        this.financialInstitution = financialInstitution;
    }

    /** Get the wording of this product
     *
     * @return the wording in the form of a String object
     */
    public String getWording() {
        return wording;
    }

    /** Set the wording of this product
     *
     * @param wording a String object
     */
    public void setWording(String wording) {
        this.wording = wording;
    }

    /** Get the state of this product
     *
     * @return the state in the form of a FinancialProduct.PRODUCT_STATE enum
     */
    public PRODUCT_STATE getState() {
        return state;
    }

    /** Set the state of this product
     *
     * @param state a FinancialProduct.PRODUCT_STATE enum
     */
    public void setState(PRODUCT_STATE state) {
        this.state = state;
    }

    /** Get the financial institution of this product
     *
     * @return the financial institution in the form of a FinancialInstitution object
     */
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    /** Set the financial institution of this product
     *
     * @param financialInstitution a FinancialInstitution object
     */
    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    /** Get the list of financial product holders of this product
     *
     * @return the list of financial product holders in the form of a List<FinancialProductHolder>
     */
    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    /** Set the list of financial product holders of this product
     *
     * @param financialProductHolders a List<FinancialProductHolder>
     */
    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    /** Represent the product state
     */
    public enum PRODUCT_STATE{
        UNARCHIVED,
        ARCHIVED,
    }
}
