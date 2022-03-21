package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Class that represent a wallet
 *
 */
@Entity
@Table(name="WALLET")
public class Wallet extends Persistent {

    @Column(name="name", nullable=false)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="wallet")
    @JsonIgnore
    private List<WalletFinancialProduct> walletFinancialProducts = new ArrayList<>();

    /** Default constructor
     * (persistent classes requirements)
     */
    public Wallet() {
    }

    /** Class constructor
     *
     * @param name a String object
     * @param financialInstitution a FinancialInstitution object
     */
    public Wallet(String name, FinancialInstitution financialInstitution) {
        this.name = name;
        this.financialInstitution = financialInstitution;
    }

    /** Class constructor with all attributes
     *
     * @param name a String object
     * @param financialInstitution a FinancialInstitution object
     * @param walletFinancialProducts a List
     */
    public Wallet(String name, FinancialInstitution financialInstitution, List<WalletFinancialProduct> walletFinancialProducts) {
        this.name = name;
        this.financialInstitution = financialInstitution;
        this.walletFinancialProducts = walletFinancialProducts;
    }

    /** Get the financial institution
     *
     * @return the financial institution in the form of a FinancialInstitution object
     */
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    /** Set the financial institution
     *
     * @param financialInstitution a FinancialInstitution object
     */
    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    /** Get the name
     *
     * @return the name in the form of a String object
     */
    public String getName() {
        return name;
    }

    /** Set the name
     *
     * @param name a String object
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Get the list of FinancialProducts of this wallet
     *
     * @return the list in the form of a List
     */
    public List<WalletFinancialProduct> getWalletFinancialProducts() {
        return walletFinancialProducts;
    }

    /** Set the list of FinancialProducts of this wallet
     *
     * @param walletFinancialProducts a List
     */
    public void setWalletFinancialProducts(List<WalletFinancialProduct> walletFinancialProducts) {
        this.walletFinancialProducts = walletFinancialProducts;
    }
}
