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
    private String nationalRegister;//devrait être une constante

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
        this.nationalRegister = nationalRegister;//TODO manque une vérification de validité (11 chiffres)
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

    //TODO remplacer ce setter par une methode addFinancialProductHolder et une methode removeFinancialProductHolder
    public void setFinancialProductHolders(List<FinancialProductHolder> financialProductHolders) {
        this.financialProductHolders = financialProductHolders;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    //TODO remplacer ce setter par une methode addNotification
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @JsonIgnore
    public String buildLogin() {
        return  (getFirstName() != null ? getFirstName() : "")
                .concat(getName() != null ? getName(): "")
                .concat(getNationalRegister() != null ? getNationalRegister(): "");
    }/*Arsène: incohérent car pour être un client de l'application, il faut avoir fourni son nom complet
       et son registre national donc ils ne peuvent pas être null
       le login se base sur le nom complet et l’identifiant unique (numéro de registre national) de l’utilisateur
       pas forcément une simple concaténation sinon on aura de très long login
       un exemple serait les initiales concaténées au numéro de registre national
    */

}
