package com.pgl.models;

import javax.persistence.*;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends Persistent{

    @Column(name = "message", nullable=false)
    private String message;

    @Column(name = "status", nullable=false)
    private NOTIFICATION_STATUS status;

    @ManyToOne()
    @JoinColumn(name = "applicationClient")
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "financialInstitution")
    private FinancialInstitution financialInstitution;

    public Notification() {
    }

    /** Builder for ApplicationClient  **/
    public Notification(String message, NOTIFICATION_STATUS status, ApplicationClient applicationClient) {
        this.message = message;
        this.status = status;
        this.applicationClient = applicationClient;
    }

    /** Builder for FinancialInstitution  **/
    public Notification(String message, NOTIFICATION_STATUS status, FinancialInstitution financialInstitution) {
        this.message = message;
        this.status = status;
        this.financialInstitution = financialInstitution;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NOTIFICATION_STATUS getStatus() {
        return status;
    }

    public void setStatus(NOTIFICATION_STATUS status) {
        this.status = status;
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

    public enum NOTIFICATION_STATUS{
        READ,
        UNREAD,
    }
}
