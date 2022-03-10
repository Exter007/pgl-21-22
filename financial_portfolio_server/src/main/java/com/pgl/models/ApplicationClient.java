package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APPLICATION_CLIENT")
public class ApplicationClient extends User{

    @Id
    @Column(name = "national_register",unique = true, nullable = false)
    private String nationalRegister;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="applicationClient")
    @JsonIgnore
    private List<FinancialProductHolder> financialProductHolders = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="applicationClient")
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    public ApplicationClient(String nationalRegister, String firstName, String name, String password, String email, boolean active, String language, String token) {
        super(password, language, token, email, active);
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.name = name;
    }

    /** Builder for all attributes  **/
    public ApplicationClient(String nationalRegister, String firstName, String name, String password, String email, boolean active, String language, String token, List<FinancialProductHolder> financialProductHolders, List<Notification> notifications) {
        super(password, language, token, email, active);
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.name = name;
        this.financialProductHolders = financialProductHolders;
        this.notifications = notifications;
    }

    public String getNationalRegister() {
        return nationalRegister;
    }

    public void setNationalRegister(String nationalRegister) {
        this.nationalRegister = nationalRegister;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @JsonIgnore
    public String getLogin() {
        return  (getFirstName() != null ? getFirstName() : "")
                .concat(getName() != null ? getName(): "")
                .concat(getNationalRegister() != null ? getNationalRegister(): "");
    }

}
