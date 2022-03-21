package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.Date;

/** Class that represent a banking saving account
 *
 */
@Entity
@DiscriminatorValue("SAVING_ACCOUNT")
public class SavingsAccount extends BankAccount {

    @Column(name="loyalty_date")
    private Date loyaltyDate;

    @Column(name="loyalty_bonus")
    private int loyaltyBonus;

    /** Default constructor
     * (persistent classes requirements)
     */
    public SavingsAccount() {
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
     * @param loyaltyBonus an int
     */
    public SavingsAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, Date loyaltyDate, int loyaltyBonus) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.SAVING_ACCOUNT);
        this.loyaltyDate = loyaltyDate;
        this.loyaltyBonus = loyaltyBonus;
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
     * @return the loyalty bonus in the form of an int
     */
    public int getLoyaltyBonus() {
        return loyaltyBonus;
    }

    /** Set the loyalty bonus of this account
     *
     * @param loyaltyBonus an int
     */
    public void setLoyaltyBonus(int loyaltyBonus) {
        this.loyaltyBonus = loyaltyBonus;
    }
}
