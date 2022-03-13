package com.pgl.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    Address address = new Address("street", "city", 0, "country");
    /*
    * address attendu après les corrections
    * Address address = new Address("street","streetNumber", "city", "postalCode", "country");
    * */
    @Test
    void getStreet() {
        assertEquals("String", address.getStreet().getClass().getSimpleName());//teste le type
        assertEquals("street", address.getStreet());//teste la valeur
    }

    @Test
    void setStreet() {
        address.setStreet("set");
        String street = address.getStreet();
        assertNotEquals("street", street);//teste le changement de valeur
        assertEquals("set", street);//teste la valeur
    }

    @Test
    void getStreetNumber() {
        //assertEquals("String", address.getStreetNumber().getClass().getSimpleName());//teste le type
        //assertEquals("streetNumber",address.getStreetNumber());//teste la valeur
        assertEquals(0, address.getStreetNumber());//teste la valeur
    }

    @Test
    void setStreetNumber() {
        //address.setStreetNumber("333A");
        //String streetNumber = address.getStreetNumber();
        //assertNotEquals("streetNumber", streetNumber);
        //assertEquals("333A", streetNumber);
        address.setStreetNumber(333);
        int streetNumber = address.getStreetNumber();
        assertNotEquals(0, streetNumber);
        assertEquals(333, streetNumber);
    }

    @Test
    void getCity() {
        assertEquals("String", address.getCity().getClass().getSimpleName());//teste le type
        assertEquals("city", address.getCity());//teste la valeur
    }

    @Test
    void setCity() {
        address.setCity("Test");
        String city = address.getCity();
        assertNotEquals("city", city);
        assertEquals("Test", city);
    }

    @Test
    void getPostalCode() {
        //assertEquals("String", address.getPostalCode().getClass().getSimpleName());//teste le type
        //assertEquals("postalCode",address.getPostalCode());//teste la valeur
        assertEquals(0, address.getPostalCode());//teste la valeur
    }

    @Test
    void setPostalCode() {
        //address.setPostalCode("777SSS");
        //String postalCode = address.getPostalCode();
        //assertNotEquals("postalCode", postalCode);
        //assertEquals("777SSS", postalCode);
        address.setPostalCode(777);
        int postalCode = address.getPostalCode();
        assertNotEquals(0, postalCode);
        assertEquals(777, postalCode);
    }

    @Test
    void getCountry() {
        assertEquals("String", address.getCountry().getClass().getSimpleName());//teste le type
        assertEquals("country", address.getCountry());//teste la valeur
    }

    @Test
    void setCountry() {
        address.setCountry("Land");
        String country = address.getCountry();
        assertNotEquals("country", country);
        assertEquals("Land", country);
    }

    @Test
    void getFinancialInstitution() {
        //N'a pas de sens
    }

    @Test
    void setFinancialInstitution() {
        //N'a pas de sens
    }
}