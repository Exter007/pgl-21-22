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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="applicationClient")
    @JsonIgnore
    private List<FinancialProductHolder> financialProductHolders = new ArrayList<>();

    public ApplicationClient() {
    }

    public ApplicationClient(String nationalRegister, String firstName, String email) {
        this();
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    @JsonIgnore
    public String getLogin() {
        return  (getFirstName() != null ? getFirstName() : "")
                .concat(getName() != null ? getName(): "")
                .concat(getNationalRegister() != null ? getNationalRegister(): "");
    }

}
