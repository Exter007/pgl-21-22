package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pgl.models.extension3.Category;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**Class that represent a user of the client application
 *
 */
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="applicationClient")
    @JsonIgnore
    private List<Wallet> wallets = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationClient")
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    /** Default constructor
     * (persistent classes requirements)
     */
    public ApplicationClient() {
    }

    /** Class constructor
     *
     * @param nationalRegister a String object that contains the client's national register
     * @param firstName a String object that contains the client's first name
     * @param name a String object that contains the client's last name
     * @param password a String object that contains the client's password
     * @param email a String object that contains the client's email
     * @param token a String object that contains the client's token
     * @param active a boolean that contains the state
     */
    public ApplicationClient(String nationalRegister, String firstName, String name, String password, String email, String token, boolean active) {
        super(password, email, token, active, ROLE.APPLICATION_CLIENT);
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.name = name;
        this.setLogin(buildLogin());
    }

    /** Class constructor with all attributes
     *
     * @param nationalRegister a String object that contains the client's national register
     * @param firstName a String object that contains the client's first name
     * @param name a String object that contains the client's last name
     * @param password a String object that contains the client's password
     * @param email a String object that contains the client's email
     * @param token a String object that contains the client's token
     * @param active a boolean that contains the state
     * @param language a String object that contains the language
     * @param financialProductHolders a List that contains the financial product holders who are bound to this client
     * @param notifications a List that contains the notifications bound to this client
     * @param wallets a List that contains the wallets bound to this client
     */
    public ApplicationClient(String nationalRegister, String firstName, String name, String password, String email, boolean active, String language, String token, List<FinancialProductHolder> financialProductHolders, List<Notification> notifications, List<Wallet> wallets) {
        super(password, email, token, active, ROLE.APPLICATION_CLIENT, language);
        this.nationalRegister = nationalRegister;
        this.firstName = firstName;
        this.name = name;
        this.financialProductHolders = financialProductHolders;
        this.notifications = notifications;
        this.setLogin(buildLogin());
        this.wallets = wallets;
    }

    /** Get the national register
     *
     * @return the national register in the form of a String object
     */
    public String getNationalRegister() {
        return nationalRegister;
    }

    /** Set the national register
     *
     * @param nationalRegister a String object that contains the client's national register
     */
    public void setNationalRegister(String nationalRegister) {
        this.nationalRegister = nationalRegister;
    }

    /** Get the first name
     *
     * @return the first name in the form of a String object
     */
    public String getFirstName() {
        return firstName;
    }

    /** Set the first name
     *
     * @param firstName a String object that contains the client's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Get the last name
     *
     * @return the last name in the form of a String object
     */
    public String getName() {
        return name;
    }

    /** Set the last name
     *
     * @param name a String object that contains the client's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Get the list of financial product holders bound to this client
     *
     * @return the list of financial product holders in the form of a List
     */
    public List<FinancialProductHolder> getFinancialProductHolders() {
        return financialProductHolders;
    }

    /** Set the list of financial product holders who will be bound to this client
     *
     * @param financialProductHolders a List that contains the financial product holders
     */
    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    /** Get the list of notification of this client
     *
     * @return the list of notification in the form of a List
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     *  Get the list of client-owned wallet
     * @return the list of client-owned wallet
     */
    public List<Wallet> getWallets() {
        return wallets;
    }

    /**
     * Set the list of client-owned wallet
     * @param wallets a List that contains the wallets
     */
    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    /** Set the list of notification of this client
     *
     * @param notifications a List of notification
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /** Method that build the login of this client
     *
     * The login is the concatenation of the last name, the first name and the national register of this client
     * example:
     * Doe John 97060530075
     * login = DoeJohn97060530075
     *
     * @return  the login in the form of a String object
     */
    @JsonIgnore
    public String buildLogin() {
        return  StringUtils.deleteWhitespace((getName() != null ? getName() : "")
                .concat(getFirstName() != null ? getFirstName(): "")
                .concat(getNationalRegister() != null ? getNationalRegister(): ""));
    }
}
