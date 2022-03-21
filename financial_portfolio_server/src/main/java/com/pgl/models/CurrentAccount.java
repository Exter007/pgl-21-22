package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Class that represent a banking current account
 *
 */
@Entity
@DiscriminatorValue("CURRENT_ACCOUNT")
public class CurrentAccount extends BankAccount {

    /** Default constructor
     * (persistent classes requirements)
     */
    public CurrentAccount() {
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
     */
    public CurrentAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency,
                          FinancialInstitution financialInstitution,
                          float monthlyFee, float annualYield) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.CURRENT_ACCOUNT);
    }
}
