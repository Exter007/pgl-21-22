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
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    /**
     * Default constructor (persistent classes requirements)
     */
    public RequestWallet() {
    }

    public RequestWallet(REQUEST_STATUS status, ApplicationClient applicationClient, FinancialInstitution financialInstitution) {
        super(status);
        this.applicationClient = applicationClient;
        this.financialInstitution = financialInstitution;
    }

    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }
}
