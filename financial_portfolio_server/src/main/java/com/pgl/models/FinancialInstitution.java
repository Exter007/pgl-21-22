package com.pgl.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_INSTITUTION")
public class FinancialInstitution extends User{

    @Id
    @Column(name = "BIC", unique = true, nullable = false)
    private String BIC;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="financialInstitution")
    @JsonIgnore
    private List<FinancialProductHolder> financialProductHolders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="financialInstitution")
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    public FinancialInstitution( String BIC, String name, String password, String email, boolean active, String language, String token, Address address) {
        super(password, language, token, email, active);
        this.BIC = BIC;
        this.name = name;
        this.address = address;
    }

    /** Builder for all attributes  **/
    public FinancialInstitution(String BIC, String name, String password, String email, String language, String token, boolean active, String phone, List<FinancialProductHolder> financialProductHolders, Address address, List<Notification> notifications) {
        super(password, language, token, email, active);
        this.BIC = BIC;
        this.name = name;
        this.phone = phone;
        this.financialProductHolders = financialProductHolders;
        this.address = address;
        this.notifications = notifications;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
