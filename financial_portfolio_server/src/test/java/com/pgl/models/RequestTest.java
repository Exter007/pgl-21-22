package com.pgl.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    Request request = new Request(Request.REQUEST_STATUS.PENDING);

    @Test
    void getStatus() {
        assertEquals("NOTIFICATION_STATUS", request.getStatus().getClass().getSimpleName());//test the type
        assertEquals(Request.REQUEST_STATUS.PENDING, request.getStatus());//test the value
    }

    @Test
    void setStatus() {
        request.setStatus(Request.REQUEST_STATUS.ACCEPTED);
        Request.REQUEST_STATUS status = request.getStatus();
        assertNotEquals(Request.REQUEST_STATUS.PENDING, status);
        assertEquals(Request.REQUEST_STATUS.ACCEPTED, status);
    }
}