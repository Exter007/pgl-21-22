package com.pgl.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address extends Persistent{

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "street_number")
    private int streetNumber;//Arsène: Devrait être un string (voir https://www.techno-science.net/definition/7104.html)

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private int postalCode;//Arsène: Devrait être un string vu que les codes postales ne contiennent pas forcément que des chiffres selon le pays (voir pays-bas et royaume-uni)

    @Column(name = "country", nullable = false)
    private String country;

    @OneToOne(mappedBy = "address")
    private FinancialInstitution financialInstitution;


    public Address(String street, String city, int postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    /** Builder for all attributes  **///Arsène: Pas de sens vu que les institutions financières s'instancient avec une adresse
    public Address(String street, int streetNumber, String city, int postalCode, String country, FinancialInstitution financialInstitution) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.financialInstitution = financialInstitution;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }
}
