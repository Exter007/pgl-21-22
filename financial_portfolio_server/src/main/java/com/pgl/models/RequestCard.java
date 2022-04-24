package com.pgl.models;

import javax.persistence.*;

/** Class that represent a request for a financial product
 *
 */
@Entity
@Table(name = "REQUEST_CARD")
public class RequestCard extends Request {

    @ManyToOne()
    @JoinColumn(name = "application_client_id")
    private ApplicationClient applicationClient;

    @ManyToOne()
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @ManyToOne()
    @JoinColumn(name = "institution_id")
    private FinancialInstitution institution;

    @Column(name = "card_type")
    private Card.CARD_TYPE cardType;

    @Column(name = "debit_card_type")
    private DebitCard.DEBIT_CARD_TYPE debitCardType;

    @Column(name = "credit_card_type")
    private CreditCard.CREDIT_CARD_TYPE creditCardType;

    /** Default constructor
     * (persistent classes requirements)
     */
    public RequestCard() {
    }

    /** Class constructor
     *
     * @param status a Request.REQUEST_STATUS enum
     * @param applicationClient an ApplicationClient object that represent the user of the client application bound to this request
     * @param bankAccount a BankAccount object that represent the bank account witch the card would be link
     */
    public RequestCard(REQUEST_STATUS status, ApplicationClient applicationClient, BankAccount bankAccount) {
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

    /** Get the bank account
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

    /** Get the institution
     *
     * @return the institution in the form of a FinancialInstitution object
     */
    public FinancialInstitution getInstitution() {
        return institution;
    }

    /** Set the institution bound to this request
     *
     * @param institution an FinancialInstitution object
     */
    public void setInstitution(FinancialInstitution institution) {
        this.institution = institution;
    }

    /** Get the card type
     *
     * @return the institution in the form of a FinancialInstitution object
     */
    public Card.CARD_TYPE getCardType() {
        return cardType;
    }

    /** Set the card type to this request
     *
     * @param cardType
     */
    public void setCardType(Card.CARD_TYPE cardType) {
        this.cardType = cardType;
    }

    /** Get the debit card type
     *
     * @return the institution in the form of a FinancialInstitution object
     */
    public DebitCard.DEBIT_CARD_TYPE getDebitCardType() {
        return debitCardType;
    }

    /** Set the debit card type to this request
     *
     * @param debitCardType
     */
    public void setDebitCardType(DebitCard.DEBIT_CARD_TYPE debitCardType) {
        this.debitCardType = debitCardType;
    }

    /** Get the debit card type
     *
     * @return the institution in the form of a FinancialInstitution object
     */
    public CreditCard.CREDIT_CARD_TYPE getCreditCardType() {
        return creditCardType;
    }

    /** Set the debit card type to this request
     *
     * @param creditCardType
     */
    public void setCreditCardType(CreditCard.CREDIT_CARD_TYPE creditCardType) {
        this.creditCardType = creditCardType;
    }
}
