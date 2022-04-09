package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {

    FinancialInstitution financialInstitution;
    Date loyaltyDate;
    SavingsAccount savingsAccount = new SavingsAccount("iban", BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT,
            FinancialProduct.PRODUCT_STATE.ARCHIVED, "pin_code", BankAccount.CURRENCY.EURO, financialInstitution,
            0, 0, loyaltyDate, 0,0);
    @Test
    void getLoyaltyDate() {
        //TODO
    }

    @Test
    void setLoyaltyDate() {
        //TODO
    }

    @Test
    void getLoyaltyBonus() {
        assertEquals(0, savingsAccount.getLoyaltyBonus());//test the value
    }

    @Test
    void setLoyaltyBonus() {
        savingsAccount.setLoyaltyBonus(10);
        float loyaltyBonus = savingsAccount.getLoyaltyBonus();
        assertNotEquals(0, loyaltyBonus);
        assertEquals(10, loyaltyBonus);
    }
}