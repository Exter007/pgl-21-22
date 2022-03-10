package com.pgl.models;

import java.util.Date;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency, FinancialInstitution financialInstitution, Date loyaltyDate, int loyaltyBonus) {
        super(iban, ACCOUNT_NATURE.SAVING_ACCOUNT, type, state, pin_code, currency, loyaltyDate, loyaltyBonus, financialInstitution);
    }

}
