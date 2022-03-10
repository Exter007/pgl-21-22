package com.pgl.models;


public class YoungAccount extends BankAccount {

    public YoungAccount(String iban, PRODUCT_STATE state, int pin_code, CURRENCY currency , FinancialInstitution financialInstitution, int ageLimit, float maxTransactionAmount) {
        super(iban, ACCOUNT_NATURE.YOUNG_ACCOUNT, ACCOUNT_TYPE.JOINT_ACCOUNT, state, pin_code, currency, ageLimit, maxTransactionAmount, financialInstitution);
    }

}
