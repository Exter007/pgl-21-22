package com.pgl.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** Class that represent a request for the transfer permission
 *
 */
@Entity
@Table(name = "REQUEST_TRANSFER")
public class RequestTransfer extends Request{

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "bank_account_id", nullable=false)
    private BankAccount bankAccount;

    /** Default constructor
     * (persistent classes requirements)
     */
    public RequestTransfer() {
    }

    /** Class constructor
     *
     * @param status a Request.REQUEST_STATUS enum
     * @param applicationClient an ApplicationClient object that represent the user of the client application bound to this request
     * @param bankAccount a BankAccount object that represent the financial product bound to this request
     */
    public RequestTransfer(REQUEST_STATUS status, ApplicationClient applicationClient, BankAccount bankAccount) {
        super(status);
        this.applicationClient = applicationClient;
        this.bankAccount = bankAccount;
    }

    /** Get the user of the client application bound to this request
     *
     * @return the user of the client application in the form of an ApplicationClient object
     */
    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    /** Set the user of the client application bound to this request
     *
     * @param applicationClient an ApplicationClient object
     */
    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    /** Get the bank account bound to this request
     *
     * @return the bank account in the form of a BankAccount object
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /** Set the bank account bound to this request
     *
     * @param bankAccount a BankAccount object
     */
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
