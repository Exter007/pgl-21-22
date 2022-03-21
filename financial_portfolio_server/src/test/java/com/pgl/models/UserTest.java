package com.pgl.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user = new User("password", "email", "token", true, User.ROLE.APPLICATION_CLIENT);

    @Test
    void getPassword() {
        assertEquals("String", user.getPassword().getClass().getSimpleName());//test the type
        assertEquals("password", user.getPassword());//test the value
    }

    @Test
    void setPassword() {
        user.setPassword("12345");
        String password = user.getPassword();
        assertNotEquals("password", password);
        assertEquals("12345", password);
    }

    @Test
    void getLanguage() {
        //TODO
    }

    @Test
    void setLanguage() {
        //TODO
    }

    @Test
    void getToken() {
        assertEquals("String", user.getToken().getClass().getSimpleName());//test the type
        assertEquals("token", user.getToken());//test the value
    }

    @Test
    void setToken() {
        user.setToken("new");
        String token = user.getToken();
        assertNotEquals("token", token);
        assertEquals("new", token);
    }

    @Test
    void getActive() {
        assertEquals(true, user.getActive());//test the value
    }

    @Test
    void setActive() {
        user.setActive(false);
        boolean active = user.getActive();
        assertFalse(active);
    }

    @Test
    void getEmail() {
        assertEquals("String", user.getEmail().getClass().getSimpleName());//test the type
        assertEquals("email", user.getEmail());//test the value
    }

    @Test
    void setEmail() {
        user.setEmail("test");
        String email = user.getEmail();
        assertNotEquals("email", email);
        assertEquals("test", email);
    }

    @Test
    void getRole() {
        assertEquals("ROLE", user.getRole().getClass().getSimpleName());//test the type
        assertEquals(User.ROLE.APPLICATION_CLIENT, user.getRole());//test the value
    }

    @Test
    void setRole() {
        user.setRole(User.ROLE.FINANCIAL_INSTITUTION);
        User.ROLE role = user.getRole();
        assertNotEquals(User.ROLE.APPLICATION_CLIENT, role);
        assertEquals(User.ROLE.FINANCIAL_INSTITUTION, role);
    }
}