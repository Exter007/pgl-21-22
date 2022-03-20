package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TermAccountTest {

    FinancialInstitution financialInstitution;
    Date maximumDate;
    TermAccount termAccount = new TermAccount("iban", BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT,
            FinancialProduct.PRODUCT_STATE.ARCHIVED, "pin_code", BankAccount.CURRENCY.EURO, financialInstitution,
            0, 0, maximumDate, 0);
    @Test
    void getMaximumDate() {
        //TODO
    }

    @Test
    void setMaximumDate() {
        //TODO
    }

    @Test
    void getPenalty() {
        assertEquals(0, termAccount.getPenalty());//test the value
    }

    @Test
    void setPenalty() {
        termAccount.setPenalty(10);
        long penalty = termAccount.getPenalty();
        assertNotEquals(0, penalty);
        assertEquals(10, penalty);
    }
}