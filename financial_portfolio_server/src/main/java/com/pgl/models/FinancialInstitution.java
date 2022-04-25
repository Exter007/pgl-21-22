package com.pgl.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Class that represent a financial institution
 *
 */
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
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="financialInstitution")
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    /** Default constructor
     * (persistent classes requirements)
     */
    public FinancialInstitution() {
    }

    /** Class constructor
     *
     * @param BIC a String object that contains the BIC
     * @param name a String object that contains the name of the institution
     * @param password a String object that contains the password
     * @param email a String object that contains the email
     * @param address an Address object that contains the address
     * @param active a boolean that contains the state
     * @param token a String object that contains the token
     */
    public FinancialInstitution(String BIC, String name, String password, String email, Address address, boolean active, String token) {
        super(password, email, token, active, ROLE.FINANCIAL_INSTITUTION);
        this.BIC = BIC;
        this.name = name;
        this.address = address;
        this.setLogin(buildLogin());
    }

    /** Class constructor with all attributes
     *
     * @param BIC a String object that contains the BIC
     * @param name a String object that contains the name of the institution
     * @param password a String object that contains the password
     * @param email a String object that contains the email
     * @param address an Address object that contains the address
     * @param active a boolean that contains the state
     * @param token a String object that contains the token
     * @param language a String object that contains the language
     * @param phone a String object that contains the phone
     * @param financialProductHolders a List that contains the financial product holders who are bound to this client
     * @param notifications a List that contains the notifications bound to this client
     */
    public FinancialInstitution(String BIC, String name, String password, String email, Address address, boolean active, String token, String language, String phone, List<FinancialProductHolder> financialProductHolders, List<Notification> notifications) {
        super(password, email, token, active, ROLE.FINANCIAL_INSTITUTION, language);
        this.BIC = BIC;
        this.name = name;
        this.phone = phone;
        this.financialProductHolders = financialProductHolders;
        this.address = address;
        this.notifications = notifications;
        this.setLogin(buildLogin());
    }

    /** Get the BIC
     *
     * @return the BIC in the form of a String object
     */
    public String getBIC() {
        return BIC;
    }

    /** Set the BIC
     *
     * @param BIC a String object that contains the BIC
     */
    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    /** Get the name
     *
     * @return the name in the form of a String object
     */
    public String getName() {
        return name;
    }

    /** Set the name
     *
     * @param name a String object that contains the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Get the phone number of this institution
     *
     * @return the phone number in the form of a String object
     */
    public String getPhone() {
        return phone;
    }

    /** Set the phone number of this institution
     *
     * @param phone a String object that contains the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Get the address where this institution is located
     *
     * @return the address in the form of an Address object
     */
    public Address getAddress() {
        return address;
    }

    /** Set the address where this institution will be located
     *
     * @param address an Address object that contains the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /** Get the list of financial product holders of this institution
     *
     * @return the list of financial product holders in the form of a List
     */
    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    /** Set the list of financial product holders of this institution
     *
     * @param financialProductHolders a List that contains the financial product holders who are bound to this client
     */
    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    /** Get the list of notification of this institution
     *
     * @return the list of notification in the form of a List
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /** Set the list of notification of this institution
     *
     * @param notifications a List that contains the notifications bound to this client
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /** Method that build the login of this institution
     *
     * The login is the concatenation of the name and the BIC of this institution
     * example:
     * ING BBRUBEBB
     * login = INGBBRUBEBB
     * @return the login in the form of a String object
     */
    @JsonIgnore
    public String buildLogin() {
        return  StringUtils.deleteWhitespace((getName() != null ? getName() : "")
                .concat(getBIC() != null ? getBIC(): ""));
    }
}
