package com.pgl.models;

import javax.persistence.*;

/** Class that represent a user
 *
 */
@MappedSuperclass
public class User extends PersistentWithoutId {

    @Column(name = "login")
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "language")
    private String language;

    @Column(name = "token")
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "role")
    private ROLE role;

    /** Default constructor
     * (persistent classes requirements)
     */
    public User() {
    }

    /** Class constructor
     *
     * @param password a String object
     * @param email a String object
     * @param token a String object
     * @param active a boolean
     * @param role a User.ROLE enum
     */
    public User(String password, String email, String token, boolean active, ROLE role) {
        this.password = password;
        this.email = email;
        this.active = active;
        this.role = role;
        this.token = token;
    }

    /** Class constructor with all attributes
     *
     * @param password a String object
     * @param email a String object
     * @param token a String object
     * @param active a boolean
     * @param role a User.ROLE enum
     * @param language a String object
     */
    public User( String password, String email, String token, boolean active, ROLE role, String language) {
        this.password = password;
        this.language = language;
        this.token = token;
        this.email = email;
        this.active = active;
        this.role = role;
    }

    /** Get the login
     *
     * @return the login in the form of a String object
     */
    public String getLogin() {
        return login;
    }

    /** Set the login
     *
     * @param login a String object
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /** Get the password
     *
     * @return the password in the form of a String object
     */
    public String getPassword() {
        return password;
    }

    /** Set the password
     *
     * @param password a String object
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Get the favorite language of this user
     *
     * @return the favorite language in the form of a String object
     */
    public String getLanguage() {
        return language;
    }

    /** Set the favorite language of this user
     *
     * @param language a String object
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /** Get the token of this user
     *
     * @return the token in the form of a String object
     */
    public String getToken() {
        return token;
    }

    /** Set the token of this user
     *
     * @param token a String object
     */
    public void setToken(String token) {
        this.token = token;
    }

    /** Setter of the attribute active
     *
     * @param active a boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /** Get if the user is active or not
     *
     * @return a boolean
     */
    public boolean getActive() {
        return active;
    }

    /** Get the email of this user
     *
     * @return the email in the form of a String object
     */
    public String getEmail() {
        return email;
    }

    /** Set the email of this user
     *
     * @param email a String object
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Get the role of this user
     *
     * @return the role in the form of a User.ROLE enum
     */
    public ROLE getRole() {
        return role;
    }

    /** Set the role of this user
     *
     * @param role a User.ROLE enum
     */
    public void setRole(ROLE role) {
        this.role = role;
    }

    /** Represent the role of this user
     *
     */
    public enum ROLE{
        APPLICATION_CLIENT,
        FINANCIAL_INSTITUTION,
    }
}
