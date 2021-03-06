package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FinancialProductHolderTest {

    ApplicationClient applicationClient = new ApplicationClient();
    FinancialInstitution financialInstitution = new FinancialInstitution();
    Date birthDate = new Date();
    FinancialProductHolder financialProductHolder = new FinancialProductHolder("nationalRegister", "name",
            "firstName", birthDate, "sex", "phone", financialInstitution, applicationClient, null);
    @Test
    void getName() {
        assertEquals("String", financialProductHolder.getName().getClass().getSimpleName());//test the type
        assertEquals("name", financialProductHolder.getName());//test the value
    }

    @Test
    void setName() {
        financialProductHolder.setName("new");
        String name = financialProductHolder.getName();
        assertNotEquals("name", name);
        assertEquals("new", name);
    }

    @Test
    void getFirstName() {
        assertEquals("String", financialProductHolder.getFirstName().getClass().getSimpleName());//test the type
        assertEquals("firstName", financialProductHolder.getFirstName());//test the value
    }

    @Test
    void setFirstName() {
        financialProductHolder.setFirstName("New");
        String firstName = financialProductHolder.getFirstName();
        assertNotEquals("firstName", firstName);
        assertEquals("New", firstName);
    }

    @Test
    void getBirthDate() {
    }

    @Test
    void setBirthDate() {
    }

    @Test
    void getSex() {
        assertEquals("String", financialProductHolder.getSex().getClass().getSimpleName());//test the type
        assertEquals("sex", financialProductHolder.getSex());//test the value
    }

    @Test
    void setSex() {
        financialProductHolder.setSex("M");
        String sex = financialProductHolder.getSex();
        assertNotEquals("sex", sex);
        assertEquals("M", sex);
    }

    @Test
    void getPhone() {
        assertEquals("String", financialProductHolder.getPhone().getClass().getSimpleName());//test the type
        assertEquals("phone", financialProductHolder.getPhone());//test the value
    }

    @Test
    void setPhone() {
        financialProductHolder.setPhone("04");
        String phone = financialProductHolder.getPhone();
        assertNotEquals("phone", phone);
        assertEquals("04", phone);
    }

    @Test
    void getNationalRegister() {
        assertEquals("String", financialProductHolder.getNationalRegister().getClass().getSimpleName());//test the type
        assertEquals("nationalRegister", financialProductHolder.getNationalRegister());//test the value
    }

    @Test
    void getFinancialInstitution() {
    }

    @Test
    void getFinancialProducts() {
    }
}