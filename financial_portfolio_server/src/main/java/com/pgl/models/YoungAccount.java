package com.pgl.models;

import javax.persistence.*;

/** Class that represent a banking young account
 *
 */
@Entity
@DiscriminatorValue("YOUNG_ACCOUNT")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class YoungAccount extends BankAccount {

    @Column(name="age_limit")
    private int ageLimit;

    @Column(name="max_transaction_amount")
    private float maxTransactionAmount;

    /** Default constructor
     * (persistent classes requirements)
     */
    public YoungAccount() {
        super(ACCOUNT_NATURE.YOUNG_ACCOUNT, TRANSFER_ACCESS.DENIED);
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
     * @param ageLimit an int
     * @param maxTransactionAmount a float
     */
    public YoungAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, int ageLimit, float maxTransactionAmount) {
        super(iban, ACCOUNT_NATURE.YOUNG_ACCOUNT, type, state, TRANSFER_ACCESS.DENIED, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.ageLimit = ageLimit;
        this.maxTransactionAmount = maxTransactionAmount;
    }

    /** Get the age limit of this account
     *
     * @return the age limit in the form of an int
     */
    public int getAgeLimit() {
        return ageLimit;
    }

    /** Set the age limit of this account
     *
     * @param ageLimit an int
     */
    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    /** Get the maximal amount this account can use
     *
     * @return the maximal amount in the form of a float
     */
    public float getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    /** Set the maximal amount this account can use
     *
     * @param maxTransactionAmount a float
     */
    public void setMaxTransactionAmount(float maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }
}
