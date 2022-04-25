package com.pgl.models;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Class that represent a banking current account
 *
 */
@Entity
@DiscriminatorValue("CURRENT_ACCOUNT")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class CurrentAccount extends BankAccount {

    /** Default constructor
     * (persistent classes requirements)
     */
    public CurrentAccount() {
        super(ACCOUNT_NATURE.CURRENT_ACCOUNT, TRANSFER_ACCESS.DENIED);
    }

    /** Class constructor
     *
     * @param iban a String object that contains the iban
     * @param type a BankAccount.ACCOUNT_TYPE enum
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param pin_code a String object that contains the pin code
     * @param currency a BankAccount.CURRENCY enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this bank account
     * @param monthlyFee a float that contains the monthly fee
     * @param annualYield a float that contains the annual yield
     */
    public CurrentAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency,
                          FinancialInstitution financialInstitution,
                          float monthlyFee, float annualYield) {
        super(iban, ACCOUNT_NATURE.CURRENT_ACCOUNT, type, state, TRANSFER_ACCESS.DENIED, pin_code, currency, financialInstitution, monthlyFee, annualYield);
    }
}
