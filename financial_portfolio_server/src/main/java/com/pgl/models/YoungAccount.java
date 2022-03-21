package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
@DiscriminatorValue("YOUNG_ACCOUNT")
public class YoungAccount extends BankAccount {

    @Column(name="age_limit")
    private int ageLimit;

    @Column(name="max_transaction_amount")
    private float maxTransactionAmount;

    /**
     * Default constructor (persistent classes requirements)
     */
    public YoungAccount() {
    }

    /** Class constructor
     *
     * @param iban a String object
     * @param type a BankAccount.ACCOUNT_TYPE enum
     * @param state a BankAccount.PRODUCT_STATE enum
     * @param pin_code a String object
     * @param currency a BankAccount.CURRENCY enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this bank account
     * @param monthlyFee a float
     * @param annualYield a float
     * @param ageLimit an int
     * @param maxTransactionAmount a float
     */
    public YoungAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, int ageLimit, float maxTransactionAmount) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.YOUNG_ACCOUNT);
        this.ageLimit = ageLimit;
        this.maxTransactionAmount = maxTransactionAmount;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public float getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    public void setMaxTransactionAmount(float maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }
}
