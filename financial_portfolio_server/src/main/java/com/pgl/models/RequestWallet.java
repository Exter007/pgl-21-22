package com.pgl.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** Class that represent a request for a wallet
 *
 */
@Entity
@Table(name = "REQUEST_WALLET")
public class RequestWallet extends Request{

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "financial_institution_BIC", nullable=false)
    private FinancialInstitution financialInstitution;

    /** Default constructor
     * (persistent classes requirements)
     */
    public RequestWallet() {
    }

    /** Class constructor
     *
     * @param status a Request.REQUEST_STATUS enum
     * @param applicationClient an ApplicationClient object that represent the user of the client application bound to this request
     * @param financialInstitution a FinancialInstitution object that represent the financial institution bound to this request
     */
    public RequestWallet(REQUEST_STATUS status, ApplicationClient applicationClient, FinancialInstitution financialInstitution) {
        super(status);
        this.applicationClient = applicationClient;
        this.financialInstitution = financialInstitution;
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

    /** Get the financial institution bound to this request
     *
     * @return the financial institution in the form of a FinancialInstitution object
     */
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    /** Set the financial institution bound to this request
     *
     * @param financialInstitution a FinancialInstitution object
     */
    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }
}
