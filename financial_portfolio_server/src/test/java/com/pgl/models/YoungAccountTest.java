package com.pgl.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YoungAccountTest {

    FinancialInstitution financialInstitution;
    YoungAccount youngAccount = new YoungAccount("iban", BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT,
            FinancialProduct.PRODUCT_STATE.ARCHIVED, 0, BankAccount.CURRENCY.EURO, financialInstitution,
            0, 0, 18, 50);
    @Test
    void getAgeLimit() {
        assertEquals(18, youngAccount.getAgeLimit());//test the value
    }

    @Test
    void setAgeLimit() {
        youngAccount.setAgeLimit(20);
        int ageLimit = youngAccount.getAgeLimit();
        assertNotEquals(18, ageLimit);
        assertEquals(20, ageLimit);
    }

    @Test
    void getMaxTransactionAmount() {
        assertEquals(50, youngAccount.getMaxTransactionAmount());//test the value
    }

    @Test
    void setMaxTransactionAmount() {
        youngAccount.setAgeLimit(100);
        float maxTransactionAmount = youngAccount.getMaxTransactionAmount();
        assertNotEquals(50, maxTransactionAmount);
        assertEquals(20, maxTransactionAmount);
    }
}