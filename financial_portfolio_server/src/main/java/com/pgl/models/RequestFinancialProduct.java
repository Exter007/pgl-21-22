package com.pgl.models;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** Class that represent a request for a financial product
 *
 */
@Entity
@Table(name = "REQUEST_FINANCIAL_PRODUCT")
public class RequestFinancialProduct extends Request{

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "financialProduct_id", nullable=false)
    private FinancialProduct financialProduct;

    /** Default constructor
     * (persistent classes requirements)
     */
    public RequestFinancialProduct() {
    }

    /** Class constructor
     *
     * @param status a Request.REQUEST_STATUS enum
     * @param applicationClient an ApplicationClient object that represent the user of the client application bound to this request
     * @param financialProduct a FinancialProduct object that represent the financial product bound to this request
     */
    public RequestFinancialProduct(REQUEST_STATUS status, ApplicationClient applicationClient, FinancialProduct financialProduct) {
        super(status);
        this.applicationClient = applicationClient;
        this.financialProduct = financialProduct;
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

    /** Get the financial product bound to this request
     *
     * @return the financial product in the form of a FinancialProduct object
     */
    public FinancialProduct getFinancialProduct() {
        return financialProduct;
    }

    /** Set the financial product bound to this request
     *
     * @param financialProduct a FinancialProduct object
     */
    public void setFinancialProduct(FinancialProduct financialProduct) {
        this.financialProduct = financialProduct;
    }
}
