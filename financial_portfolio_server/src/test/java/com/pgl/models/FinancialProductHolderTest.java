package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FinancialProductHolderTest {

    ApplicationClient applicationClient;
    FinancialInstitution financialInstitution;
    Date birthDate = new Date();
    FinancialProductHolder financialProductHolder = new FinancialProductHolder("nationalRegister", "name",
            "firstName", birthDate, "sex", "phone", financialInstitution, applicationClient, null);
    @Test
    void getName() {
        assertEquals("String", financialProductHolder.getName().getClass().getSimpleName());//teste le type
        assertEquals("name", financialProductHolder.getName());//teste la valeur
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
        assertEquals("String", financialProductHolder.getFirstName().getClass().getSimpleName());//teste le type
        assertEquals("firstName", financialProductHolder.getFirstName());//teste la valeur
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
        //TODO
    }

    @Test
    void setBirthDate() {
        //TODO
    }

    @Test
    void getSex() {
        assertEquals("String", financialProductHolder.getSex().getClass().getSimpleName());//teste le type
        assertEquals("sex", financialProductHolder.getSex());//teste la valeur
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
        assertEquals("String", financialProductHolder.getPhone().getClass().getSimpleName());//teste le type
        assertEquals("phone", financialProductHolder.getPhone());//teste la valeur
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
        assertEquals("String", financialProductHolder.getNationalRegister().getClass().getSimpleName());//teste le type
        assertEquals("nationalRegister", financialProductHolder.getNationalRegister());//teste la valeur
    }

    @Test
    void getFinancialInstitution() {
        //TODO
    }

    @Test
    void getFinancialProducts() {
        //TODO
    }
}