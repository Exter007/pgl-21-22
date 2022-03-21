package com.pgl.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    BankAccount bankAccount;
    Date date;
    Transaction transaction = new Transaction("transactionNumber",
            Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER, bankAccount,
            "destinationIBAN", "destinationName", 0, Transaction.COMMUNICATION_TYPE.FREE,
            "communication", date, Request.REQUEST_STATUS.PENDING);

    @Test
    void getTransactionNumber() {
        assertEquals("String", transaction.getTransactionNumber().getClass().getSimpleName());//test the type
        assertEquals("transactionNumber", transaction.getTransactionNumber());//test the value
    }

    @Test
    void setTransactionNumber() {
        transaction.setTransactionNumber("1");
        String transactionNumber = transaction.getTransactionNumber();
        assertNotEquals("transactionNumber", transactionNumber);
        assertEquals("1", transactionNumber);
    }

    @Test
    void getType() {
        assertEquals("TRANSACTION_TYPE", transaction.getType().getClass().getSimpleName());//test the type
        assertEquals(Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER, transaction.getType());//test the value
    }

    @Test
    void setType() {
        transaction.setType(Transaction.TRANSACTION_TYPE.INCOMING_TRANSFER);
        Transaction.TRANSACTION_TYPE type = transaction.getType();
        assertNotEquals(Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER, type);
        assertEquals(Transaction.TRANSACTION_TYPE.INCOMING_TRANSFER, type);
    }

    @Test
    void getBankAccount() {
        //TODO
    }

    @Test
    void setBankAccount() {
        //TODO
    }

    @Test
    void getDestinationIBAN() {
        assertEquals("String", transaction.getDestinationIBAN().getClass().getSimpleName());//test the type
        assertEquals("destinationIBAN", transaction.getDestinationIBAN());//test the value
    }

    @Test
    void setDestinationIBAN() {
        transaction.setDestinationIBAN("BE5");
        String destinationIBAN = transaction.getDestinationIBAN();
        assertNotEquals("destinationIBAN", destinationIBAN);
        assertEquals("BE5", destinationIBAN);
    }

    @Test
    void getDestinationName() {
        assertEquals("String", transaction.getDestinationName().getClass().getSimpleName());//test the type
        assertEquals("destinationName", transaction.getDestinationName());//test the value
    }

    @Test
    void setDestinationName() {
        transaction.setDestinationName("name");
        String destinationName = transaction.getDestinationName();
        assertNotEquals("destinationName", destinationName);
        assertEquals("name", destinationName);
    }

    @Test
    void getAmount() {
        assertEquals(0, transaction.getAmount());//test the value
    }

    @Test
    void setAmount() {
        transaction.setAmount(10);
        float amount = transaction.getAmount();
        assertNotEquals(0, amount);
        assertEquals(10, amount);
    }

    @Test
    void getCommunication_type() {
        assertEquals("COMMUNICATION_TYPE", transaction.getCommunication_type().getClass().getSimpleName());//test the type
        assertEquals(Transaction.COMMUNICATION_TYPE.FREE, transaction.getCommunication_type());//test the value
    }

    @Test
    void setCommunication_type() {
        transaction.setCommunication_type(Transaction.COMMUNICATION_TYPE.STRUCTURED);
        Transaction.COMMUNICATION_TYPE type = transaction.getCommunication_type();
        assertNotEquals(Transaction.COMMUNICATION_TYPE.FREE, type);
        assertEquals(Transaction.COMMUNICATION_TYPE.STRUCTURED, type);
    }

    @Test
    void getCommunication() {
        assertEquals("String", transaction.getCommunication().getClass().getSimpleName());//test the type
        assertEquals("communication", transaction.getCommunication());//test the value
    }

    @Test
    void setCommunication() {
        transaction.setCommunication("new");
        String communication = transaction.getCommunication();
        assertNotEquals("communication", communication);
        assertEquals("new", communication);
    }

    @Test
    void getDate() {
        //TODO
    }

    @Test
    void setDate() {
        //TODO
    }

    @Test
    void getStatus() {
        assertEquals("REQUEST_STATUS", transaction.getStatus().getClass().getSimpleName());//test the type
        assertEquals(Request.REQUEST_STATUS.PENDING, transaction.getStatus());//test the value
    }

    @Test
    void setStatus() {
        transaction.setStatus(Request.REQUEST_STATUS.ACCEPTED);
        Request.REQUEST_STATUS status = transaction.getStatus();
        assertNotEquals(Request.REQUEST_STATUS.PENDING, status);
        assertEquals(Request.REQUEST_STATUS.ACCEPTED, status);
    }
}