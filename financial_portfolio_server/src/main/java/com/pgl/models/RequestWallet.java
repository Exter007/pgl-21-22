package com.pgl.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST_WALLET")
public class RequestWallet extends Request{

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "wallet_id", nullable=false)
    private Wallet wallet;

    public RequestWallet() {
    }

    public RequestWallet(REQUEST_STATUS status, ApplicationClient applicationClient, Wallet wallet) {
        super(status);
        this.applicationClient = applicationClient;
        this.wallet = wallet;
    }

    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
