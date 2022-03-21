package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

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

    public ApplicationClient() {
    }

    public ApplicationClient(String nationalRegister, String firstName, String name, String password, String email, String token, boolean active) {
        super(password, email, token, active, ROLE.APPLICATION_CLIENT);
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.name = name;
        this.setLogin(buildLogin());
    }

    /** Builder for all attributes  **/
    public ApplicationClient(String nationalRegister, String firstName, String name, String password, String email, boolean active, String language, String token, List<FinancialProductHolder> financialProductHolders, List<Notification> notifications) {
        super(password, email, token, active, ROLE.APPLICATION_CLIENT, language);
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.name = name;
        this.financialProductHolders = financialProductHolders;
        this.notifications = notifications;
        this.setLogin(buildLogin());
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
    public String buildLogin() {
        return  StringUtils.deleteWhitespace((getName() != null ? getName() : "")
                .concat(getFirstName() != null ? getFirstName(): "")
                .concat(getNationalRegister() != null ? getNationalRegister(): ""));
    }
}
