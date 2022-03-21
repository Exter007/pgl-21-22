package com.pgl.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST_TRANSFER")
public class RequestTransfer extends Request{

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "bank_account_id", nullable=false)
    private BankAccount bankAccount;

    /**
     * Default constructor (persistent classes requirements)
     */
    public RequestTransfer() {
    }

    public RequestTransfer(REQUEST_STATUS status, ApplicationClient applicationClient, BankAccount bankAccount) {
        super(status);
        this.applicationClient = applicationClient;
        this.bankAccount = bankAccount;
    }

    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
