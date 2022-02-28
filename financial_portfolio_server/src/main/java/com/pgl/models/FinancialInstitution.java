package com.pgl.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_INSTITUTION")
public class FinancialInstitution extends User{

    @Id
    @Column(name = "BIC", unique = true, nullable = false)
    private String BIC;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="financialInstitution")
    @JsonIgnore
    private List<FinancialProductHolder> financialProductHolders = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="financialInstitution")
    @JsonIgnore
    private List<Wallet> wallets = new ArrayList<>();

    public FinancialInstitution() {
    }

    public FinancialInstitution(String BIC, String name, String email, String phone) {
        this();
        this.BIC = BIC;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    public List<Wallet> getWallets() { return wallets; }

    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }
}
