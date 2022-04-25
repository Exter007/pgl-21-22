package com.pgl.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/** Class that represent a debit card
 *
 */
//@JsonTypeName("DebitCard")
@Entity
@DiscriminatorValue("DEBIT_CARD")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class DebitCard extends Card {

    @Column(name="debit_card_type")
    private DEBIT_CARD_TYPE debitCardType;

    @Column(name="negative_balance")
    private boolean negativeBalance;

    @Column(name="negative_fee")
    private float negativeFee;

    @Column(name="amount_per_day")
    private int amountPerDay;

    @Column(name="amount_per_week")
    private int amountPerWeek;

    /** Default constructor
     * (persistent classes requirements)
     */
    public DebitCard() {
        super();
    }

    /** Class constructor
     *
     * @param wording a String object
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this product
     * @param financialProductHolders a List that contains the financial product holders who have this product
     * @param cardNumber a String object
     * @param bankAccount a BankAccount object who is inked to this card
     * @param endValidityDate a Date object
     * @param isValid a boolean object
     * @param isBlocked a boolean object
     * @param CVC a integer object
     * @param annualFee a float object
     * @param commissionFee a float object
     * @param internationallyUsable a boolean object
     * @param internationalFee a float object
     * @param withdrawalFee a float object
     * @param debitCardType a DEBIT_CARD_TYPE enum
     * @param negativeBalance a boolean object
     * @param negativeFee a float object
     * @param amountPerDay a integer object
     * @param amountPerWeek a integer object
     */
    public DebitCard(String wording,
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
                     DEBIT_CARD_TYPE debitCardType,
                     boolean negativeBalance,
                     float negativeFee,
                     int amountPerDay,
                     int amountPerWeek) {
        super(wording, financialInstitution, financialProductHolders, cardNumber, bankAccount, endValidityDate, isValid, isBlocked,
                CVC, annualFee, commissionFee, internationallyUsable, internationalFee, withdrawalFee, CARD_TYPE.DEBIT_CARD);
        this.debitCardType = debitCardType;
        this.negativeBalance = negativeBalance;
        this.negativeFee = negativeFee;
        this.amountPerDay = amountPerDay;
        this.amountPerWeek = amountPerWeek;
    }

    /** Get the debit card type of the card
     *
     * @return the debit card type of the card in the form of a DEBIT_CARD_TYPE enum
     */
    public DEBIT_CARD_TYPE getDebitCardType() {
        return debitCardType;
    }

    /** Set the debit card type of the card
     *
     * @param debitCardType a DEBIT_CARD_TYPE enum
     */
    public void setDebitCardType(DEBIT_CARD_TYPE debitCardType) {
        this.debitCardType = debitCardType;
    }

    /** Get the negative balance status of the card
     *
     * @return the negative balance status of the card in the form of a boolean object
     */
    public boolean getNegativeBalance() {
        return negativeBalance;
    }

    /** Set the negative balance status of the card
     *
     * @param negativeBalance a boolean object
     */
    public void setNegativeBalance(boolean negativeBalance) {
        this.negativeBalance = negativeBalance;
    }

    /** Get the negative balance fee of the card
     *
     * @return the negative balance fee of the card in the form of a float object
     */
    public float getNegativeFee() {
        return negativeFee;
    }

    /** Set the negative balance fee of the card
     *
     * @param negativeFee a float object
     */
    public void setNegativeFee(float negativeFee) {
        this.negativeFee = negativeFee;
    }

    /** Get the amount per day of the card
     *
     * @return the amount per day of the card in the form of a integer object
     */
    public int getAmountPerDay() {
        return amountPerDay;
    }

    /** Set the amount per day of the card
     *
     * @param amountPerDay a integer object
     */
    public void setAmountPerDay(int amountPerDay) {
        this.amountPerDay = amountPerDay;
    }

    /** Get the amount per week of the card
     *
     * @return the amount per week of the card in the form of a integer object
     */
    public int getAmountPerWeek() {
        return amountPerWeek;
    }

    /** Set the amount per week of the card
     *
     * @param amountPerWeek a integer object
     */
    public void setAmountPerWeek(int amountPerWeek) {
        this.amountPerWeek = amountPerWeek;
    }

    /** Represent the debit card type
     */
    public enum DEBIT_CARD_TYPE{
        BANCONTACT,
        MAESTRO,
    }
}
