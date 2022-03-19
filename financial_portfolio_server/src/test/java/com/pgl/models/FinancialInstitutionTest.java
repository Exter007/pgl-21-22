package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinancialInstitutionTest {

    List<FinancialProductHolder> financialProductHolders = new ArrayList<>();
    List<Notification> notifications = new ArrayList<>();
    Address address = new Address("street", "city", 0, "country");
    FinancialInstitution financialInstitution = new FinancialInstitution("BIC", "name", "password", "email", "address", "token", true, "phone", financialProductHolders, address, notifications);
    @Test
    void getBIC() {
        assertEquals("String", financialInstitution.getBIC().getClass().getSimpleName());//teste le type
        assertEquals("BIC", financialInstitution.getBIC());//teste la valeur
    }

    @Test
    void setBIC() {
        //n'as pas de sens
    }

    @Test
    void getName() {
        assertEquals("String", financialInstitution.getName().getClass().getSimpleName());//teste le type
        assertEquals("name", financialInstitution.getName());//teste la valeur
    }

    @Test
    void setName() {
        financialInstitution.setName("test");
        String name = financialInstitution.getName();
        assertNotEquals("name", name);
        assertEquals("test", name);
    }

    @Test
    void getEmail() {
        assertEquals("String", financialInstitution.getEmail().getClass().getSimpleName());//teste le type
        assertEquals("email", financialInstitution.getEmail());//teste la valeur
    }

    @Test
    void setEmail() {
        financialInstitution.setEmail("test@umons.com");
        String email = financialInstitution.getEmail();
        assertNotEquals("email", email);
        assertEquals("test@umons.com", email);
    }

    @Test
    void getPhone() {
        assertEquals("String", financialInstitution.getPhone().getClass().getSimpleName());//teste le type
        assertEquals("phone", financialInstitution.getPhone());//teste la valeur
    }

    @Test
    void setPhone() {
        financialInstitution.setPhone("0000");
        String phone = financialInstitution.getPhone();
        assertNotEquals("phone", phone);
        assertEquals("0000", phone);
    }

    @Test
    void getFinancialProductHolders() {
        assertTrue(financialInstitution.getFinancialProductHolders().isEmpty(), "La liste doit être vide");
    }

    @Test
    void setFinancialProductHolders() {
        //TODO
    }

    @Test
    void getAddress() {
        //TODO
    }

    @Test
    void setAddress() {
        //TODO
    }

    @Test
    void getNotifications() {
        //TODO
    }

    @Test
    void setNotifications() {
        //TODO
    }

    @Test
    void buildLogin() {
        //TODO
    }
}