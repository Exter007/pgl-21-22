package com.pgl.models.extension1;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.pgl.models.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/** Abstract class that represent a card
 *
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCard.class, name = "CreditCard"),
        @JsonSubTypes.Type(value = DebitCard.class, name = "DebitCard"),
})
@Entity
@DiscriminatorValue(value="CARD")
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Card extends FinancialProduct {

    @Column(name="card_number")
    private String cardNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linked_bank_account")
    private BankAccount bankAccount;

    @Column(name="end_validity")
    private Date endValidityDate;

    @Column(name="is_valid")
    private boolean isValid;

    @Column(name="is_blocked")
    private boolean isBlocked;

    @Column(name="cvc")
    private int CVC;

    @Column(name="annual_fee")
    private float annualFee;

    @Column(name="commission_fee")
    private float commissionFee;

    @Column(name="internationally_usable")
    private boolean internationallyUsable;

    @Column(name="international_fee")
    private float internationalFee;

    @Column(name="withdrawal_fee")
    private float withdrawalFee;

    @Column(name="card_type")
    private CARD_TYPE cardType;


    /** Default constructor
     * (persistent classes requirements)
     */
    public Card() {
        super(PRODUCT_TYPE.CARD);
    }

    /** Class constructor
     *
     * @param wording a String object that contains a description
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this product
     * @param financialProductHolders a List that contains the financial product holders who have this product
     * @param cardNumber a String object that contains the card number
     * @param bankAccount a BankAccount object who is inked to this card
     * @param endValidityDate a Date object that contains the validity end date
     * @param isValid a boolean object that contains the valid state of the card
     * @param isBlocked a boolean object that contains the block state of the card
     * @param CVC an integer object that contains the CVC
     * @param annualFee a float object that contains the annual fee
     * @param commissionFee a float object that contains the commission fee
     * @param internationallyUsable a boolean object that international use state
     * @param internationalFee a float object that contains the international use fee
     * @param withdrawalFee a float object that contains the withdrawal fee
     * @param cardType a Card_Type enum
     */
    public Card(String wording,
                FinancialInstitution financialInstitution,
                List<FinancialProductHolder> financialProductHolders,
                String cardNumber,
                BankAccount bankAccount,
                Date endValidityDate,
                boolean isValid,
                boolean isBlocked,
                int CVC,
                float annualFee,
                float commissionFee,
                boolean internationallyUsable,
                float internationalFee,
                float withdrawalFee,
                CARD_TYPE cardType) {
        super(wording, PRODUCT_TYPE.CARD, PRODUCT_STATE.UNARCHIVED, TRANSFER_ACCESS.AUTHORIZED, financialInstitution, financialProductHolders);
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
        this.endValidityDate = endValidityDate;
        this.isValid = isValid;
        this.isBlocked = isBlocked;
        this.CVC = CVC;
        this.annualFee = annualFee;
        this.commissionFee = commissionFee;
        this.internationallyUsable = internationallyUsable;
        this.internationalFee = internationalFee;
        this.withdrawalFee = withdrawalFee;
        this.cardType = cardType;
    }

    /** Get the card number
     *
     * @return the card number in the form of a String object
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /** Set the card number
     *
     * @param cardNumber a String object that contains the card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /** Get the bank account
     *
     * @return the bank account in the form of a BankAccount object
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /** Set the bank account
     *
     * @param bankAccount a BankAccount object
     */
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    /** Get the end validity date of the card
     *
     * @return the end validity date of the card in the form of a Date object
     */
    public Date getEndValidityDate() {
        return endValidityDate;
    }

    /** Set the end validity date of the card
     *
     * @param endValidityDate a Date object
     */
    public void setEndValidityDate(Date endValidityDate) {
        this.endValidityDate = endValidityDate;
    }

    /** Get the validity of the card
     *
     * @return the validity of the card in the form of a boolean object
     */
    public boolean getIsValid() {
        return isValid;
    }

    /** Set the validity of the card
     *
     * @param isValid a boolean object
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    /** Get the blocking state of the card
     *
     * @return the blocking state of the card in the form of a boolean object
     */
    public boolean getIsBlocked() {
        return isBlocked;
    }

    /** Set the blocking state of the card
     *
     * @param isBlocked a boolean object
     */
    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    /** Get the CVC of the card
     *
     * @return the CVC of the card in the form of an integer object
     */
    public int getCVC() {
        return CVC;
    }

    /** Set the CVC of the card
     *
     * @param CVC an integer object
     */
    public void setCVC(int CVC) {
        this.CVC = CVC;
    }

    /** Get the annual fee of the card
     *
     * @return the annual fee of the card in the form of a float object
     */
    public float getAnnualFee() {
        return annualFee;
    }

    /** Set the annual fee of the card
     *
     * @param annualFee a float object
     */
    public void setAnnualFee(float annualFee) {
        this.annualFee = annualFee;
    }

    /** Get the commission fee of the card
     *
     * @return the commission fee of the card in the form of a float object
     */
    public float getCommissionFee() {
        return commissionFee;
    }

    /** Set the commission fee of the card
     *
     * @param commissionFee a float object
     */
    public void setCommissionFee(float commissionFee) {
        this.commissionFee = commissionFee;
    }

    /** Get the internationally usable state of the card
     *
     * @return the internationally usable state of the card in the form of a boolean object
     */
    public boolean getInternationallyUsable() {
        return internationallyUsable;
    }

    /** Set the internationally usable state of the card
     *
     * @param internationallyUsable a boolean object
     */
    public void setInternationallyUsable(boolean internationallyUsable) {
        this.internationallyUsable = internationallyUsable;
    }

    /** Get the international fee of the card
     *
     * @return the international fee of the card in the form of a float object
     */
    public float getInternationalFee() {
        return internationalFee;
    }

    /** Set the international fee of the card
     *
     * @param internationalFee a float object
     */
    public void setInternationalFee(float internationalFee) {
        this.internationalFee = internationalFee;
    }

    /** Get the withdrawal fee of the card
     *
     * @return the withdrawal fee of the card in the form of a float object
     */
    public float getWithdrawalFee() {
        return withdrawalFee;
    }

    /** Set the withdrawal fee of the card
     *
     * @param withdrawalFee a float object
     */
    public void setWithdrawalFee(float withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }

    /** Get the card type of the card
     *
     * @return the card type of the card in the form of a CARD_TYPE enum
     */
    public CARD_TYPE getCardType() {
        return cardType;
    }

    /** Set the card type of the card
     *
     * @param cardType a CARD_TYPE enum
     */
    public void setCardType(CARD_TYPE cardType) {
        this.cardType = cardType;
    }

    /** Represent the card type
     */
    public enum CARD_TYPE{
        DEBIT_CARD,
        CREDIT_CARD,
    }
}
