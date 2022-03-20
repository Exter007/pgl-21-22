package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationClientTest {

    ApplicationClient appClient = new ApplicationClient("nationalRegister", "firstName",  "name",
            "password", "email", false, "language", "token", null, null);

    @Test
    void getNationalRegister() {
        assertEquals("String", appClient.getNationalRegister().getClass().getSimpleName());//test the type
        assertEquals("nationalRegister", appClient.getNationalRegister());//test the value
        //TODO tester la longueur de la chaine (11) et le type de caractère (numérique) (refaire ligne 14 et 19)
    }

    @Test
    void setNationalRegister() {
        //N'a pas de sens
    }

    @Test
    void getFirstName() {
        assertEquals("String", appClient.getFirstName().getClass().getSimpleName());//test the type
        assertEquals("firstName", appClient.getFirstName());//test the value
    }

    @Test
    void setFirstName() {
        appClient.setFirstName("Mister");
        String firstName = appClient.getFirstName();
        assertNotEquals("firstName", firstName);
        assertEquals("Mister", firstName);
    }

    @Test
    void getName() {
        assertEquals("String", appClient.getName().getClass().getSimpleName());//test the type
        assertEquals("name", appClient.getName());//test the value
    }

    @Test
    void setName() {
        appClient.setName("Test");
        String name = appClient.getName();
        assertNotEquals("name", name);
        assertEquals("Test", name);
    }

    @Test
    void getFinancialProductHolders() {
    }

    @Test
    void setFinancialProductHolders() {
        //TODO remplacer ce setter par une methode addFinancialProductHolder et une methode removeFinancialProductHolder
    }

    @Test
    void getNotifications() {
    }

    @Test
    void setNotifications() {
        //TODO remplacer ce setter par une methode addNotification
    }

    @Test
    void buildLogin() {
        //TODO
    }
}
