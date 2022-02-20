package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_PRODUCT_HOLDER")
public class FinancialProductHolder extends PersistentWithoutId {

//    @EmbeddedId
//    FinancialProductHolderKey financialProductHolderKey;

    @Id
    @Column(name = "national_register", unique = true, nullable = false)
    private String nationalRegister;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToMany(mappedBy = "financialProductHolders")
    @JsonIgnore
    private List<FinancialProduct> financialProducts = new ArrayList<>();

//    @Embeddable
//    class FinancialProductHolderKey implements Serializable {
//        @Column(name = "national_register", unique = true, nullable = false)
//        private String nationalRegister;
//
//        @Column(name = "financial_institution_BIC", nullable=false)
//        private String financialInstitutionBIC;
//    }
//
//    public FinancialProductHolderKey getFinancialProductHolderKey() {
//        return financialProductHolderKey;
//    }
//
//    public void setFinancialProductHolderKey(FinancialProductHolderKey financialProductHolderKey) {
//        this.financialProductHolderKey = financialProductHolderKey;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    public String getNationalRegister() {
        return nationalRegister;
    }

    public void setNationalRegister(String nationalRegister) {
        this.nationalRegister = nationalRegister;
    }

    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public List<FinancialProduct> getFinancialProducts() {
        return financialProducts;
    }

    public void setFinancialProducts(List<FinancialProduct> financialProducts) {
        this.financialProducts = financialProducts;
    }
}
