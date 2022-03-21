package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_PRODUCT_HOLDER")
public class FinancialProductHolder extends PersistentWithoutId {

    @Id
    @Column(name = "national_register", unique = true, nullable = false)
    private String nationalRegister;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    @ManyToOne()
    @JoinColumn(name = "application_client_id")
    private ApplicationClient applicationClient;

    @ManyToMany(mappedBy = "financialProductHolders", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FinancialProduct> financialProducts = new ArrayList<>();

    /**
     * Default constructor (persistent classes requirements)
     */
    public FinancialProductHolder() {
    }

    public FinancialProductHolder(String nationalRegister, String name, String firstName, Date birthDate, FinancialInstitution financialInstitution, CurrentAccount currentAccount) {
        this.nationalRegister = nationalRegister;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.financialInstitution = financialInstitution;
        this.financialProducts.add(currentAccount);
    }

    /** Builder for all attributes  **/
    public FinancialProductHolder(String nationalRegister, String name, String firstName, Date birthDate, String sex, String phone, FinancialInstitution financialInstitution, ApplicationClient applicationClient, List<FinancialProduct> financialProducts) {
        this.nationalRegister = nationalRegister;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.phone = phone;
        this.financialInstitution = financialInstitution;
        this.applicationClient = applicationClient;
        this.financialProducts = financialProducts;
        //add this FinancialProductHolder in the list financialProductHolders of this.applicationClient
        List<FinancialProductHolder> list = applicationClient.getFinancialProductHolders();
        list.add(this);
        this.applicationClient.setFinancialProductHolders(list);
    }

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
