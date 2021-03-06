package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinancialProductTest {

    FinancialInstitution financialInstitution;
    List<FinancialProductHolder> financialProductHolders;
    FinancialProduct.PRODUCT_TYPE productType;
    FinancialProduct.PRODUCT_STATE state;
    FinancialProduct.TRANSFER_ACCESS transferAccess;
    FinancialProduct financialProduct = new FinancialProduct("wording",productType, state, transferAccess, financialInstitution, financialProductHolders);
    @Test
    void getWording() {
        assertEquals("String", financialProduct.getWording().getClass().getSimpleName());//test the type
        assertEquals("wording", financialProduct.getWording());//test the value
    }

    @Test
    void setWording() {
        financialProduct.setWording("NewWording");
        String wording = financialProduct.getWording();
        assertNotEquals("wording", wording);
        assertEquals("NewWording", wording);
    }

    @Test
    void getState() {
        assertEquals("PRODUCT_STATE", financialProduct.getState().getClass().getSimpleName());//test the type
        assertEquals(FinancialProduct.PRODUCT_STATE.UNARCHIVED, financialProduct.getState());//test the value
    }

    @Test
    void setState() {
        financialProduct.setState(FinancialProduct.PRODUCT_STATE.ARCHIVED);
        FinancialProduct.PRODUCT_STATE state = financialProduct.getState();
        assertNotEquals(FinancialProduct.PRODUCT_STATE.UNARCHIVED, state);
        assertEquals(FinancialProduct.PRODUCT_STATE.ARCHIVED, state);
    }

    @Test
    void getFinancialInstitution() {
        //TODO
    }

    @Test
    void setFinancialInstitution() {
        //TODO
    }

    @Test
    void getFinancialProductHolders() {
        //TODO
    }

    @Test
    void setFinancialProductHolders() {
        //TODO
    }
}