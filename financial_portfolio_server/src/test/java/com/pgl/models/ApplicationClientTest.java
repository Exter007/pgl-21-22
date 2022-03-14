package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationClientTest {

    List<FinancialProductHolder> financialProductHolders = new ArrayList<>();
    List<Notification> notifications = new ArrayList<>();
    ApplicationClient appClient = new ApplicationClient("nationalRegister", "firstName",  "name", "password", "email", false, "language", "token", financialProductHolders, notifications);

    @Test
    void getNationalRegister() {
        assertEquals("String", appClient.getNationalRegister().getClass().getSimpleName());//teste le type
        assertEquals("nationalRegister", appClient.getNationalRegister());//teste la valeur
        //TODO tester la longueur de la chaine (11) et le type de caractère (numérique) (refaire ligne 14 et 19)
    }

    @Test
    void setNationalRegister() {
        //N'a pas de sens
    }

    @Test
    void getFirstName() {
        assertEquals("String", appClient.getFirstName().getClass().getSimpleName());//teste le type
        assertEquals("firstName", appClient.getFirstName());//teste la valeur
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
        assertEquals("String", appClient.getName().getClass().getSimpleName());//teste le type
        assertEquals("name", appClient.getName());//teste la valeur
    }

    @Test
    void setName() {
        appClient.setName("Test");
        String name = appClient.getName();
        assertNotEquals("name", name);
        assertEquals("Test", name);
    }

    @Test
    void getEmail() {
        assertEquals("String", appClient.getEmail().getClass().getSimpleName());//teste le type
        assertEquals("email", appClient.getEmail());//teste la valeur
        //TODO tester la présence de @ ?
    }

    @Test
    void setEmail() {
        appClient.setEmail("test");
        String email = appClient.getEmail();
        assertNotEquals("email", email);
        assertEquals("test", email);
    }

    @Test
    void getFinancialProductHolders() {
        assertTrue(appClient.getFinancialProductHolders().isEmpty(),"La liste doit être vide");
    }

    @Test
    void setFinancialProductHolders() {
        //TODO remplacer ce setter par une methode addFinancialProductHolder et une methode removeFinancialProductHolder
    }

    @Test
    void getNotifications() {
        assertTrue(appClient.getNotifications().isEmpty(),"La liste doit être vide");
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
