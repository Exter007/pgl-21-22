package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Class that represent a financial product holder
 *
 */
@Entity
@Table(name = "FINANCIAL_PRODUCT_HOLDER")
public class FinancialProductHolder extends Persistent {

    @Column(name = "national_register", nullable = false)
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

    /** Default constructor
     * (persistent classes requirements)
     */
    public FinancialProductHolder() {
    }

    /** Class constructor
     *
     * @param nationalRegister a String object
     * @param name a String object that contains the holder's last name
     * @param firstName a String object
     * @param birthDate a Date object
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide the product of this holder
     * @param currentAccount a CurrentAccount object that represent the banking current account of this holder
     */
    public FinancialProductHolder(String nationalRegister, String name, String firstName, Date birthDate, FinancialInstitution financialInstitution, CurrentAccount currentAccount) {
        this.nationalRegister = nationalRegister;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.financialInstitution = financialInstitution;
        this.financialProducts.add(currentAccount);
    }

    /** Class constructor with all attributes
     *
     * @param nationalRegister a String object
     * @param name a String object that contains the holder's last name
     * @param firstName a String object
     * @param birthDate a Date object
     * @param sex a String object
     * @param phone a String object
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide the product of this holder
     * @param applicationClient an ApplicationClient object that represent the user of the client application bound to this holder
     * @param financialProducts a List that contains the financial products held by this holder
     */
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

    /** Get the last name of this holder
     *
     * @return the last name in the form of a String object
     */
    public String getName() {
        return name;
    }

    /** Set the last name of this holder
     *
     * @param name a String object
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Get the first name of this holder
     *
     * @return the first name in the form of a String object
     */
    public String getFirstName() {
        return firstName;
    }

    /** Set the first name of this holder
     *
     * @param firstName a String object
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Get the birthdate of this holder
     *
     * @return the birthdate in the form of a Date object
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /** Set the birthdate of this holder
     *
     * @param birthDate a Date object
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /** Get the sex of this holder
     *
     * @return the sex of this holder in the form of a String object
     */
    public String getSex() {
        return sex;
    }

    /** Set the sex of this holder
     *
     * @param sex a String object
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /** Get the phone number of this holder
     *
     * @return the phone number in the form of a String object
     */
    public String getPhone() {
        return phone;
    }

    /** Set the phone number of this holder
     *
     * @param phone a String object
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Get the application client user bound to this holder
     *
     * @return the application client user in the form of an ApplicationClient object
     */
    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    /** Set the application client user bound to this holder
     *
     * @param applicationClient an ApplicationClient object
     */
    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    /** Get the national register of this holder
     *
     * @return the national register in the form of a String object
     */
    public String getNationalRegister() {
        return nationalRegister;
    }

    /** Set the national register of this holder
     *
     * @param nationalRegister a String object
     */
    public void setNationalRegister(String nationalRegister) {
        this.nationalRegister = nationalRegister;
    }

    /** Get the financial institution bound to this holder
     *
     * @return the financial institution in the form of a FinancialInstitution object
     */
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    /** Set the financial institution that will be bound to this holder
     *
     * @param financialInstitution a FinancialInstitution object
     */
    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    /** Get the list of financial products held by this holder
     *
     * @return the list of financial products in the form of a List
     */
    public List<FinancialProduct> getFinancialProducts() {
        return financialProducts;
    }

    /** Set the list of financial products held by this holder
     *
     * @param financialProducts a List
     */
    public void setFinancialProducts(List<FinancialProduct> financialProducts) {
        this.financialProducts = financialProducts;
    }
}
