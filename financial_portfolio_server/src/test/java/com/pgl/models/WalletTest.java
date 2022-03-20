package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    FinancialInstitution financialInstitution;
    List<WalletFinancialProduct> walletFinancialProducts;
    Wallet wallet = new Wallet("name", financialInstitution, walletFinancialProducts);

    @Test
    void getFinancialInstitution() {
        //TODO
    }

    @Test
    void setFinancialInstitution() {
        //TODO
    }

    @Test
    void getName() {
        assertEquals("String", wallet.getName().getClass().getSimpleName());//test the type
        assertEquals("name", wallet.getName());//test the value
    }

    @Test
    void setName() {
        wallet.setName("Test");
        String name = wallet.getName();
        assertNotEquals("name", name);
        assertEquals("Test", name);
    }

    @Test
    void getWalletFinancialProducts() {
        //TODO
    }

    @Test
    void setWalletFinancialProducts() {
        //TODO
    }
}