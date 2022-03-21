package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String streetNumber;//There is street number with letter within (example: 17A)

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;//the postal code in the UK and Nederland contains letter
    @Column(name = "country", nullable = false)
    private String country;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private FinancialInstitution financialInstitution;

    public Address() {
    }

    /***
     * Class constructor
     *
     * @param street a String
     * @param streetNumber a String
     * @param city a String
     * @param postalCode a String
     * @param country a String
     */
    public Address(String street, String streetNumber,String city, String postalCode, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    /**
     * Get the street
     *
     * @return the street in the form of a string
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street
     *
     * @param street a String
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the street number
     *
     * @return the street number in the form of an int
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Set the street number
     * @param streetNumber an int
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
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
