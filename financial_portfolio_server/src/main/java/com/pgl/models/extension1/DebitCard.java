package com.pgl.models.extension1;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProductHolder;
import com.pgl.models.BankAccount;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/** Class that represent a debit card
 *
 */
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
     * @param debitCardType a DEBIT_CARD_TYPE enum
     * @param negativeBalance a boolean object that contains the negative balance state
     * @param negativeFee a float object that contains the negative fee
     * @param amountPerDay an integer object that contains the amount available per day
     * @param amountPerWeek an integer object that contains the amount available per week
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
