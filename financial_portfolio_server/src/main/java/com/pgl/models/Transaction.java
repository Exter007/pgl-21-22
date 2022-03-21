package com.pgl.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APP_TRANSACTION")
public class Transaction extends PersistentWithoutId{

    @Id
    @Column(name = "transaction_number",unique = true, nullable = false)
    private String transactionNumber;

    @Column(name = "type", nullable = false)
    private TRANSACTION_TYPE type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transmitter_id", referencedColumnName = "id", nullable = false)
    private BankAccount bankAccount;

    @Column(name = "destination_IBAN", nullable = false)
    private String destinationIBAN;

    @Column(name = "destination_name", nullable = false)
    private String destinationName;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "communication_type")
    private COMMUNICATION_TYPE communication_type;

    @Column(name = "communication")
    private String communication;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "status", nullable = false)
    private Request.REQUEST_STATUS status;

    /**
     * Default constructor (persistent classes requirements)
     */
    public Transaction() {
    }

    public Transaction(String transactionNumber, TRANSACTION_TYPE type, BankAccount bankAccount, String destinationIBAN, String destinationName, float amount, Date date, Request.REQUEST_STATUS status) {
        this.transactionNumber = transactionNumber;
        this.type = type;
        this.bankAccount = bankAccount;
        this.destinationIBAN = destinationIBAN;
        this.destinationName = destinationName;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public Transaction(String transactionNumber, TRANSACTION_TYPE type, BankAccount bankAccount, String destinationIBAN, String destinationName, float amount, COMMUNICATION_TYPE communication_type, String communication, Date date, Request.REQUEST_STATUS status) {
        this.transactionNumber = transactionNumber;
        this.type = type;
        this.bankAccount = bankAccount;
        this.destinationIBAN = destinationIBAN;
        this.destinationName = destinationName;
        this.amount = amount;
        this.communication_type = communication_type;
        this.communication = communication;
        this.date = date;
        this.status = status;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public TRANSACTION_TYPE getType() {
        return type;
    }

    public void setType(TRANSACTION_TYPE type) {
        this.type = type;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDestinationIBAN() {
        return destinationIBAN;
    }

    public void setDestinationIBAN(String destinationIBAN) {
        this.destinationIBAN = destinationIBAN;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public COMMUNICATION_TYPE getCommunication_type() {
        return communication_type;
    }

    public void setCommunication_type(COMMUNICATION_TYPE communication_type) {
        this.communication_type = communication_type;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Request.REQUEST_STATUS getStatus() {
        return status;
    }

    public void setStatus(Request.REQUEST_STATUS status) {
        this.status = status;
    }

    public enum TRANSACTION_TYPE{
        OUTGOING_TRANSFER,
        INCOMING_TRANSFER,
    }

    public enum COMMUNICATION_TYPE{
        FREE,
        STRUCTURED,
    }

}
