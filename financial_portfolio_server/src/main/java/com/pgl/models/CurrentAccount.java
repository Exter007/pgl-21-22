package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CURRENT_ACCOUNT")
public class CurrentAccount extends BankAccount {

    /**
     * Default constructor (persistent classes requirements)
     */
    public CurrentAccount() {
    }

    public CurrentAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency,
                          FinancialInstitution financialInstitution,
                          float monthlyFee, float annualYield) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.CURRENT_ACCOUNT);
    }
}
