package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Class that represent a financial product
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="FINANCIAL_PRODUCT")
public class FinancialProduct extends Persistent {

    @Column(name = "wording")
    private String wording;

    @Column(name = "product_type")
    private PRODUCT_TYPE productType;

    @Column(name = "state", nullable = false)
    private PRODUCT_STATE state;

    @Column(name="amount")
    private float amount;

    @Column(name = "tranfer_access")
    private TRANSFER_ACCESS transferAccess;

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
        this.state = PRODUCT_STATE.UNARCHIVED;
    }

    /** Class constructor
     *
     * @param productType
     */
    public FinancialProduct(PRODUCT_TYPE productType) {
        this.productType = productType;
        this.state = PRODUCT_STATE.UNARCHIVED;
    }

    /** Class constructor
     *
     * @param productType
     * @param transferAccess
     */
    public FinancialProduct(PRODUCT_TYPE productType, TRANSFER_ACCESS transferAccess) {
        this.productType = productType;
        this.transferAccess = transferAccess;
        this.state = PRODUCT_STATE.UNARCHIVED;
    }

    /** Class constructor
     *
     * @param productType
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this product
     */
    public FinancialProduct(PRODUCT_TYPE productType, PRODUCT_STATE state, TRANSFER_ACCESS transferAccess, FinancialInstitution financialInstitution) {
        this.state = state;
        this.transferAccess = transferAccess;
        this.financialInstitution = financialInstitution;
        this.productType = productType;
        this.state = PRODUCT_STATE.UNARCHIVED;
        this.amount = 0;
    }

    /** Class constructor with all attributes
     *
     * @param wording a String object
     * @param productType
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param transferAccess a FinancialProduct.TRANSFER_ACCESS enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this product
     * @param financialProductHolders a List that contains the financial product holders who have this product
     */
    public FinancialProduct(String wording, PRODUCT_TYPE productType, PRODUCT_STATE state, TRANSFER_ACCESS transferAccess, FinancialInstitution financialInstitution, List<FinancialProductHolder> financialProductHolders) {
        this.wording = wording;
        this.state = state;
        this.transferAccess = transferAccess;
        this.financialProductHolders = financialProductHolders;
        this.financialInstitution = financialInstitution;
        this.productType = productType;
        this.state = PRODUCT_STATE.UNARCHIVED;
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

    /**
     * Set the type of financial product
     * @return
     */
    public PRODUCT_TYPE getProductType() {
        return productType;
    }

    /**
     *  Get the type of financial product
     * @param productType a FinancialProduct.PRODUCT_TYPE enum
     */
    public void setProductType(PRODUCT_TYPE productType) {
        this.productType = productType;
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

    /** Get the amount available
     *
     * @return the amount available in this account in the form of a float
     */
    public float getAmount() {
        return amount;
    }

    /** Set the amount available
     *
     * @param amount a float
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /** Get the transfert access of this product
     *
     * @return the state in the form of a FinancialProduct.TRANSFER_ACCESS enum
     */
    public TRANSFER_ACCESS getTransferAccess() {
        return transferAccess;
    }

    /** Set the transfert access of this product
     *
     * @param transferAccess a FinancialProduct.TRANSFER_ACCESS enum
     */
    public void setTransferAccess(TRANSFER_ACCESS transferAccess) {
        this.transferAccess = transferAccess;
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
     * @return the list of financial product holders in the form of a List
     */
    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    /** Set the list of financial product holders of this product
     *
     * @param financialProductHolders a List
     */
    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    /** Represent the product type
     */
    public enum PRODUCT_TYPE{
        BANK_ACCOUNT,
        INSURANCE_CONTRACT,
        CARD,
    }

    /** Represent the product state
     */
    public enum PRODUCT_STATE{
        UNARCHIVED,
        ARCHIVED,
    }

    /** Represent the authorization to access the transfer functionality
     */
    public enum TRANSFER_ACCESS{
        UNAVAILABLE,
        DENIED,
        AUTHORIZED,
    }
}
