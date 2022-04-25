package com.pgl.models.extension1;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProductHolder;
import com.pgl.models.BankAccount;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/** Class that represent a credit card
 *
 */
//@JsonTypeName("CreditCard")
@Entity
@DiscriminatorValue("CREDIT_CARD")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class CreditCard extends Card {

    @Column(name="monthly_credit")
    private int monthlyCredit;

    @Column(name="temp_monthly_credit")
    private int tempMonthlyCredit;

    @Column(name="temp_monthly_credit_end_date")
    private Date tempMonthlyCreditEndDate;

    @Column(name="credit_card_type")
    private CREDIT_CARD_TYPE creditCardType;

    /** Default constructor
     * (persistent classes requirements)
     */
    public CreditCard() {
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
     * @param creditCardType a CREDIT_CARD_TYPE enum
     * @param monthlyCredit a integer object
     * @param tempMonthlyCredit a integer object
     * @param tempMonthlyCreditEndDate a Date object
     */
    public CreditCard(String wording,
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
                CREDIT_CARD_TYPE creditCardType,
                int monthlyCredit,
                int tempMonthlyCredit,
                Date tempMonthlyCreditEndDate) {
        super(wording, financialInstitution, financialProductHolders, cardNumber, bankAccount, endValidityDate, isValid, isBlocked,
                CVC, annualFee, commissionFee, internationallyUsable, internationalFee, withdrawalFee, CARD_TYPE.CREDIT_CARD);
        this.creditCardType = creditCardType;
        this.monthlyCredit = monthlyCredit;
        this.tempMonthlyCredit = tempMonthlyCredit;
        this.tempMonthlyCreditEndDate = tempMonthlyCreditEndDate;
    }

    /** Get the credit card type of the card
     *
     * @return the credit card type of the card in the form of a CREDIT_CARD_TYPE enum
     */
    public CREDIT_CARD_TYPE getCreditCardType() {
        return creditCardType;
    }

    /** Set the credit card type of the card
     *
     * @param creditCardType a DEBIT_CARD_TYPE enum
     */
    public void setCreditCardType(CREDIT_CARD_TYPE creditCardType) {
        this.creditCardType = creditCardType;
    }

    /** Get the monthly credit of the card
     *
     * @return the monthly credit of the card in the form of a integer
     */
    public int getMonthlyCredit() {
        return monthlyCredit;
    }

    /** Set the monthly credit end date of the card
     *
     * @param monthlyCredit a integer
     */
    public void setMonthlyCredit(int monthlyCredit) {
        this.monthlyCredit = monthlyCredit;
    }

    /** Get the temp monthly credit of the card
     *
     * @return the temp monthly credit of the card in the form of a integer
     */
    public int getTempMonthlyCredit() {
        return tempMonthlyCredit;
    }

    /** Set the temp monthly credit end date of the card
     *
     * @param tempMonthlyCredit a integer
     */
    public void setTempMonthlyCredit(int tempMonthlyCredit) {
        this.tempMonthlyCredit = tempMonthlyCredit;
    }

    /** Get the temp monthly credit end date of the card
     *
     * @return the temp monthly credit end date of the card in the form of a Date object
     */
    public Date getTempMonthlyCreditEndDate() {
        return tempMonthlyCreditEndDate;
    }

    /** Set the temp monthly credit of the card
     *
     * @param tempMonthlyCreditEndDate a Date object
     */
    public void setTempMonthlyCreditEndDate(Date tempMonthlyCreditEndDate) {
        this.tempMonthlyCreditEndDate = tempMonthlyCreditEndDate;
    }

    /** Represent the credit card type
     */
    public enum CREDIT_CARD_TYPE{
        VISA,
        AMERICAN_EXPRESS,
        MASTERCARD,
    }
}
