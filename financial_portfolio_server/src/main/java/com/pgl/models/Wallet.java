package com.pgl.models;

import javax.persistence.*;

@Entity
@Table(name="WALLET")
public class Wallet extends Persistent {

    @Column(name="name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    //TODO : Implementer les methodes relatives a Wallet.

    public Wallet() {
    }

    public Wallet(String name, FinancialInstitution financialInstitution) {
        this();
        this.name = name;
        this.financialInstitution = financialInstitution;
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
