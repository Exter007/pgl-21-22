package com.pgl.models;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    BankAccount bankAccount = new CurrentAccount("iban",
            BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT, FinancialProduct.PRODUCT_STATE.UNARCHIVED,
            "pin_code", BankAccount.CURRENCY.EURO, null, 0, 0);

    @Test
    void getIban() {
        assertEquals("String", bankAccount.getIban().getClass().getSimpleName());//test the type
        assertEquals("iban", bankAccount.getIban());//test the value
    }

    @Test
    void getType() {
        assertEquals("ACCOUNT_TYPE", bankAccount.getAccountType().getClass().getSimpleName());//test the type
        assertEquals(BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT, bankAccount.getAccountType());//test the value
    }

    @Test
    void getPin_code() {
        assertEquals("String", bankAccount.getPin_code().getClass().getSimpleName());//test the type
        assertEquals("pin_code", bankAccount.getPin_code());//test the value
    }

    @Test
    void setPin_code() {
        bankAccount.setPin_code("1999");
        String pin = bankAccount.getPin_code();
        assertNotEquals("0000", pin);
        assertEquals("1999", pin);
    }

    @Test
    void getAmount() {
        assertEquals(0, bankAccount.getAmount());//test the value
    }

    @Test
    void setAmount() {
        bankAccount.setAmount(10/4);
        float amount = bankAccount.getAmount();
        assertNotEquals(0, amount);
        assertEquals(10/4, amount);
    }

    @Test
    void getCurrency() {
        assertEquals("CURRENCY", bankAccount.getCurrency().getClass().getSimpleName());//test the type
        assertEquals(BankAccount.CURRENCY.EURO, bankAccount.getCurrency());//test the value
    }

    @Test
    void setCurrency() {
        //ne supporte que les euros
    }

    @Test
    void getMonthlyFee() {
        assertEquals(0, bankAccount.getMonthlyFee());//test the value
    }

    @Test
    void setMonthlyFee() {
        bankAccount.setMonthlyFee(0.1F);
        float monthlyFee = bankAccount.getMonthlyFee();
        assertNotEquals(0, monthlyFee);
        assertEquals(0.1F, monthlyFee);
    }

    @Test
    void getAnnualYield() {
        assertEquals(0, bankAccount.getAnnualYield());//test the value
    }

    @Test
    void setAnnualYield() {
        bankAccount.setAnnualYield(0.1F);
        float annualYield = bankAccount.getAnnualYield();
        assertNotEquals(0, annualYield);
        assertEquals(0.1F, annualYield);
    }

    @Test
    void getNature() {
        assertEquals("ACCOUNT_NATURE", bankAccount.getNature().getClass().getSimpleName());//test the type
        assertEquals(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT, bankAccount.getNature());//test the value
    }
}