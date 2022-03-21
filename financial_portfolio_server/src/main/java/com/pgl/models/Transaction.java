package com.pgl.models;

import javax.persistence.*;
import java.util.Date;

/** Class that represent a transaction with a bank account available in this application
 *
 */
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

    /** Default constructor
     * (persistent classes requirements)
     */
    public Transaction() {
    }

    /** Class constructor
     *
     * @param transactionNumber a String object
     * @param type a Transaction.TRANSACTION_TYPE enum
     * @param bankAccount a BankAccount object
     * @param destinationIBAN a String object
     * @param destinationName a String object
     * @param amount a float
     * @param date a Date object
     * @param status a Request.REQUEST_STATUS enum
     */
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

    /** Class constructor with a communication
     *
     * @param transactionNumber a String object
     * @param type a Transaction.TRANSACTION_TYPE enum
     * @param bankAccount a BankAccount object
     * @param destinationIBAN a String object
     * @param destinationName a String object
     * @param amount a float
     * @param communication_type a Transaction.COMMUNICATION_TYPE enum
     * @param communication a String object
     * @param date a Date object
     * @param status a Request.REQUEST_STATUS enum
     */
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

    /** Get the transaction number of this transaction
     *
     * @return the transaction number in the form of a String object
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /** Set the transaction number of this transaction
     *
     * @param transactionNumber a String object
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /** Get the transaction type of this transaction
     *
     * @return the transaction type in the form of a Transaction.TRANSACTION_TYPE enum
     */
    public TRANSACTION_TYPE getType() {
        return type;
    }

    /** Set the transaction type of this transaction
     *
     * @param type a Transaction.TRANSACTION_TYPE enum
     */
    public void setType(TRANSACTION_TYPE type) {
        this.type = type;
    }

    /** Get the bank account bound to this transaction
     *
     * @return the bank account in the form of a BankAccount object
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /** Set the bank account bound to this transaction
     *
     * @param bankAccount a BankAccount object
     */
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    /** Get the IBAN of the other bank account
     *
     * @return the IBAN in the form of a String object
     */
    public String getDestinationIBAN() {
        return destinationIBAN;
    }

    /** Set the IBAN of the other bank account
     *
     * @param destinationIBAN a String object
     */
    public void setDestinationIBAN(String destinationIBAN) {
        this.destinationIBAN = destinationIBAN;
    }

    /** Get the name of the other bank account
     *
     * @return the name in the form of a String object
     */
    public String getDestinationName() {
        return destinationName;
    }

    /** Set the name of the other bank account
     *
     * @param destinationName a String object
     */
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /** Get the amount of the transaction
     *
     * @return the amount in the form of a float
     */
    public float getAmount() {
        return amount;
    }

    /** Set the amount of the transaction
     *
     * @param amount a float
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /** Get the communication type
     *
     * @return the communication type in the form of a Transaction.COMMUNICATION_TYPE enum
     */
    public COMMUNICATION_TYPE getCommunication_type() {
        return communication_type;
    }

    /** Set the communication type
     *
     * @param communication_type a Transaction.COMMUNICATION_TYPE enum
     */
    public void setCommunication_type(COMMUNICATION_TYPE communication_type) {
        this.communication_type = communication_type;
    }

    /** Get the communication of this transaction
     *
     * @return the communication in the form of a String object
     */
    public String getCommunication() {
        return communication;
    }

    /** Set the communication of this transaction
     *
     * @param communication a String object
     */
    public void setCommunication(String communication) {
        this.communication = communication;
    }

    /** Get the date of this transaction
     *
     * @return the date in the form of a Date object
     */
    public Date getDate() {
        return date;
    }

    /** Set the date of this transaction
     *
     * @param date a Date object
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /** Get the status of this transaction
     *
     * @return the status in the form of a Request.REQUEST_STATUS enum
     */
    public Request.REQUEST_STATUS getStatus() {
        return status;
    }

    /** Set the status of this transaction
     *
     * @param status a Request.REQUEST_STATUS enum
     */
    public void setStatus(Request.REQUEST_STATUS status) {
        this.status = status;
    }

    /** Represent the transaction type
     */
    public enum TRANSACTION_TYPE{
        OUTGOING_TRANSFER,
        INCOMING_TRANSFER,
    }

    /** Represent the communication type
     */
    public enum COMMUNICATION_TYPE{
        FREE,
        STRUCTURED,
    }

}
