package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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


    public Wallet() {
    }

    public Wallet(String name, FinancialInstitution financialInstitution) {
        this.name = name;
        this.financialInstitution = financialInstitution;
    }

    public Wallet(String name, FinancialInstitution financialInstitution, List<WalletFinancialProduct> walletFinancialProducts) {
        this.name = name;
        this.financialInstitution = financialInstitution;
        this.walletFinancialProducts = walletFinancialProducts;
    }

    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WalletFinancialProduct> getWalletFinancialProducts() {
        return walletFinancialProducts;
    }

    public void setWalletFinancialProducts(List<WalletFinancialProduct> walletFinancialProducts) {
        this.walletFinancialProducts = walletFinancialProducts;
    }
}
