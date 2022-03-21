package com.pgl.models;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST_FINANCIAL_PRODUCT")
public class RequestFinancialProduct extends Request{

    @ManyToOne()
    @JoinColumn(name = "application_client_id", nullable=false)
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "financialProduct_id", nullable=false)
    private FinancialProduct financialProduct;

    /**
     * Default constructor (persistent classes requirements)
     */
    public RequestFinancialProduct() {
    }

    public RequestFinancialProduct(REQUEST_STATUS status, ApplicationClient applicationClient, FinancialProduct financialProduct) {
        super(status);
        this.applicationClient = applicationClient;
        this.financialProduct = financialProduct;
    }

    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    public FinancialProduct getFinancialProduct() {
        return financialProduct;
    }

    public void setFinancialProduct(FinancialProduct financialProduct) {
        this.financialProduct = financialProduct;
    }
}
