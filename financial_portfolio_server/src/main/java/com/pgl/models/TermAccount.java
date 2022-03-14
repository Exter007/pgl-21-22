package com.pgl.models;

import java.util.Date;


public class TermAccount extends BankAccount {

    public TermAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency , FinancialInstitution financialInstitution, Date maximumDate, long penalty) {
        super(iban, ACCOUNT_NATURE.TERM_ACCOUNT, type, state, pin_code, currency, maximumDate, penalty, financialInstitution);
    }
}
