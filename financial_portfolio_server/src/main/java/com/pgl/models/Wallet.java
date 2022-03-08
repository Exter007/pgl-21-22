package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="WALLET")
public class Wallet extends Persistent {

    @Column(name="name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="financialInstitution")
    @JsonIgnore
    private List<FinancialProduct> financialProducts = new ArrayList<>();

    private List<Wallet> wallets;

    //TODO : Implementer les methodes relatives a Wallet.

    public Wallet() {
    }

    public Wallet(String name, FinancialInstitution financialInstitution, BankAccount bankAccount) {
        this();
        this.name = name;
        this.financialInstitution = financialInstitution;
        this.financialProducts.add(bankAccount);
    }

    public void create() {

    }

    public void modify() {

    }

    public void delete(int id) {
        // DELETE FROM WALLETS where id = id;
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
}
