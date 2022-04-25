package com.pgl.models;

import javax.persistence.*;
import java.util.Date;

/** Class that represent a banking term account
 *
 */
@Entity
@DiscriminatorValue("TERM_ACCOUNT")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class TermAccount extends BankAccount {

    @Column(name="maximum_date")
    private Date maximumDate;

    @Column(name="penalty")
    private long penalty;

    /** Default constructor
     * (persistent classes requirements)
     */
    public TermAccount() {
        super(ACCOUNT_NATURE.TERM_ACCOUNT, TRANSFER_ACCESS.UNAVAILABLE);
    }

    /** Class constructor
     *
     * @param iban a String object
     * @param type a BankAccount.ACCOUNT_TYPE enum
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param pin_code a String object
     * @param currency a BankAccount.CURRENCY enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this bank account
     * @param monthlyFee a float
     * @param annualYield a float
     * @param maximumDate a Date object
     * @param penalty a long object
     */
    public TermAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, Date maximumDate, long penalty) {
        super(iban, ACCOUNT_NATURE.TERM_ACCOUNT, type, state, TRANSFER_ACCESS.UNAVAILABLE, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.maximumDate = maximumDate;
        this.penalty = penalty;
    }

    /** Get the date of closing of this account
     *
     * @return the date of closing in the form of a Date object
     */
    public Date getMaximumDate() {
        return maximumDate;
    }

    /** Set the date of closing of this account
     *
     * @param maximumDate a Date object
     */
    public void setMaximumDate(Date maximumDate) {
        this.maximumDate = maximumDate;
    }

    /** Get the penalty of transaction
     *
     * @return the penalty in the form of a long
     */
    public long getPenalty() {
        return penalty;
    }

    /** Set the penalty of transaction
     *
     * @param penalty a long
     */
    public void setPenalty(long penalty) {
        this.penalty = penalty;
    }
}
