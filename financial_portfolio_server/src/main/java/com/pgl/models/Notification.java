package com.pgl.models;

import javax.persistence.*;

/** Class that represent a notification
 *
 */
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

    /** Default constructor
     * (persistent classes requirements)
     */
    public Notification() {
    }

    /** Class constructor for ApplicationClient
     *
     * @param message a String object
     * @param status a Notification.NOTIFICATION_STATUS enum
     * @param applicationClient an ApplicationClient object that represent the user of the client application bound to this notification
     */
    public Notification(String message, NOTIFICATION_STATUS status, ApplicationClient applicationClient) {
        this.message = message;
        this.status = status;
        this.applicationClient = applicationClient;
    }

    /** Class constructor for FinancialInstitution
     *
     * @param message a String object
     * @param status a Notification.NOTIFICATION_STATUS enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution bound to this notification
     */
    public Notification(String message, NOTIFICATION_STATUS status, FinancialInstitution financialInstitution) {
        this.message = message;
        this.status = status;
        this.financialInstitution = financialInstitution;
    }

    /**
     * Class constructeur for all arguments
     * @param message a String object
     * @param status a Notification.NOTIFICATION_STATUS enum
     * @param applicationClient a ApplicationClient object
     * @param financialInstitution a FinancialInstitution object that represent the financial institution bound to this notification
     */
    public Notification(String message, NOTIFICATION_STATUS status, ApplicationClient applicationClient, FinancialInstitution financialInstitution) {
        this.message = message;
        this.status = status;
        this.applicationClient = applicationClient;
        this.financialInstitution = financialInstitution;
    }

    /** Get the message of this notification
     *
     * @return the message in the form of a String object
     */
    public String getMessage() {
        return message;
    }

    /** Set the message of this notification
     *
     * @param message a String object
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Get the status of this notification
     *
     * @return the status in the form of a Notification.NOTIFICATION_STATUS enum
     */
    public NOTIFICATION_STATUS getStatus() {
        return status;
    }

    /** Set the status of this notification
     *
     * @param status a Notification.NOTIFICATION_STATUS enum
     */
    public void setStatus(NOTIFICATION_STATUS status) {
        this.status = status;
    }

    /** Get the application client bound to this notification
     *
     * @return the application client in the form of an ApplicationClient object
     */
    public ApplicationClient getApplicationClient() {
        return applicationClient;
    }

    /** Set the application client bound to this notification
     *
     * @param applicationClient an ApplicationClient object
     */
    public void setApplicationClient(ApplicationClient applicationClient) {
        this.applicationClient = applicationClient;
    }

    /** Get the financial institution bound to this notification
     *
     * @return the financial institution in the form of an FinancialInstitution object
     */
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    /** Set the financial institution bound to this notification
     *
     * @param financialInstitution an FinancialInstitution object
     */
    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    /** Represent the status of a notification
     */
    public enum NOTIFICATION_STATUS{
        READ,
        UNREAD,
    }
}
