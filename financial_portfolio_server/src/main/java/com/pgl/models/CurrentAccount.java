package com.pgl.models;


public class CurrentAccount extends BankAccount {

    public CurrentAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency, FinancialInstitution financialInstitution) {
        super(iban, ACCOUNT_NATURE.CURRENT_ACCOUNT, type,state, pin_code, currency, financialInstitution);
    }

}
