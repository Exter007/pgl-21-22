package com.pgl.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    ApplicationClient applicationClient;
    FinancialInstitution financialInstitution;
    Notification notification = new Notification("message", Notification.NOTIFICATION_STATUS.UNREAD, applicationClient);

    @Test
    void getMessage() {
        assertEquals("String", notification.getMessage().getClass().getSimpleName());//test the type
        assertEquals("message", notification.getMessage());//test the value
    }

    @Test
    void setMessage() {
        notification.setMessage("New Message");
        String message = notification.getMessage();
        assertNotEquals("message", message);
        assertEquals("New Message", message);
    }

    @Test
    void getStatus() {
        assertEquals("NOTIFICATION_STATUS", notification.getStatus().getClass().getSimpleName());//test the type
        assertEquals(Notification.NOTIFICATION_STATUS.UNREAD, notification.getStatus());//test the value
    }

    @Test
    void setStatus() {
        notification.setStatus(Notification.NOTIFICATION_STATUS.READ);
        Notification.NOTIFICATION_STATUS status = notification.getStatus();
        assertNotEquals(Notification.NOTIFICATION_STATUS.UNREAD, status);
        assertEquals(Notification.NOTIFICATION_STATUS.READ, status);
    }

    @Test
    void getApplicationClient() {
        //TODO
    }

    @Test
    void setApplicationClient() {
        //TODO
    }

    @Test
    void getFinancialInstitution() {
        //TODO
    }

    @Test
    void setFinancialInstitution() {
        //TODO
    }
}