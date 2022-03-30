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

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

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
     * @param name
     * @param financialInstitution
     * @param applicationClient
     */
    public Wallet(String name, FinancialInstitution financialInstitution, ApplicationClient applicationClient) {
        this.name = name;
        this.financialInstitution = financialInstitution;
        this.applicationClient = applicationClient;
    }

    /** Class constructor with all attributes
     *
     * @param name
     * @param financialInstitution
     * @param applicationClient
     * @param walletFinancialProducts
     */
    public Wallet(String name, FinancialInstitution financialInstitution, ApplicationClient applicationClient, List<WalletFinancialProduct> walletFinancialProducts) {
        this.name = name;
        this.financialInstitution = financialInstitution;
        this.applicationClient = applicationClient;
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

    /**
     * Get the application client who owns the wallet
     * @return
     */
    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    /**
     * Set the application client who owns the wallet
     * @param applicationClient
     */
    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
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
