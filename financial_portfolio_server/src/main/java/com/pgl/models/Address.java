/** Class that represent an address
 *
 */
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

    /** Default constructor
     * (persistent classes requirements)
     */
    public Address() {
    }

    /** Class constructor
     *
     * @param street a String object
     * @param streetNumber a String object
     * @param city a String object
     * @param postalCode a String object
     * @param country a String object
     */
    public Address(String street, String streetNumber,String city, String postalCode, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    /** Get the street
     *
     * @return the street in the form of a String object
     */
    public String getStreet() {
        return street;
    }

    /** Set the street
     *
     * @param street a String object
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /** Get the street number
     *
     * @return the street number in the form of a String object
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /** Set the street number
     *
     * @param streetNumber a String object
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /** Get the city
     *
     * @return the city in the form of a String object
     */
    public String getCity() {
        return city;
    }

    /** Set the city
     *
     * @param city a String object
     */
    public void setCity(String city) {
        this.city = city;
    }

    /** Get the postal code
     *
     * @return the postal code in the form of a String object
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** Set the postal code
     *
     * @param postalCode a String object
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Get the country
     *
     * @return the country in the form of a String object
     */
    public String getCountry() {
        return country;
    }

    /** Set the country
     *
     * @param country a String object
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Get the financial institution bound to this address
     *
     * @return the financial institution in the form of a FinancialInstitution object
     */
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    /** Set the financial institution that will be bound to this address
     *
     * @param financialInstitution a FinancialInstitution object
     */
    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }
}
