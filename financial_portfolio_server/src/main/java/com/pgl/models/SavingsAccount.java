package com.pgl.models;

import javax.persistence.*;
import java.util.Date;

/** Class that represent a banking saving account
 *
 */
@Entity
@DiscriminatorValue("SAVING_ACCOUNT")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class SavingsAccount extends BankAccount {

    @Column(name="loyalty_date")
    private Date loyaltyDate;

    @Column(name="loyalty_bonus")
    private float loyaltyBonus;

    @Column(name="annual_interest")
    private float annualInterest;


    /** Default constructor
     * (persistent classes requirements)
     */
    public SavingsAccount() {
        super(ACCOUNT_NATURE.SAVING_ACCOUNT, TRANSFER_ACCESS.UNAVAILABLE);
    }

    /** Class constructor
     *
     * @param iban a String object
     * @param type a BankAccount.ACCOUNT_TYPE enum
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param pin_code a String object
     * @param currency a BankAccount.CURRENCY enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this bank account
     * @param monthlyFee a float
     * @param annualYield a float
     * @param loyaltyDate a Date object
     * @param loyaltyBonus an float
     * @param annualInterest an float
     */
    public SavingsAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, Date loyaltyDate, float loyaltyBonus, float annualInterest) {
        super(iban, ACCOUNT_NATURE.SAVING_ACCOUNT, type, state, TRANSFER_ACCESS.UNAVAILABLE, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.loyaltyDate = loyaltyDate;
        this.loyaltyBonus = loyaltyBonus;
        this.annualInterest = annualInterest;
    }

    /** Get the loyalty date of this account
     *
     * @return the loyalty date in the form of a Date object
     */
    public Date getLoyaltyDate() {
        return loyaltyDate;
    }

    /** Set the loyalty date of this account
     *
     * @param loyaltyDate a Date object
     */
    public void setLoyaltyDate(Date loyaltyDate) {
        this.loyaltyDate = loyaltyDate;
    }

    /** Get the loyalty bonus of this account
     *
     * @return the loyalty bonus in the form of an float
     */
    public float getLoyaltyBonus() {
        return loyaltyBonus;
    }

    /** Set the loyalty bonus of this account
     *
     * @param loyaltyBonus an float
     */
    public void setLoyaltyBonus(float loyaltyBonus) {
        this.loyaltyBonus = loyaltyBonus;
    }

    /**
     * Get the Annual Interest of this account
     * @return
     */
    public float getAnnualInterest() {
        return annualInterest;
    }

    /**
     * Set the Annual Interest of this account
     * @param annualInterest
     */
    public void setAnnualInterest(float annualInterest) {
        this.annualInterest = annualInterest;
    }
}
