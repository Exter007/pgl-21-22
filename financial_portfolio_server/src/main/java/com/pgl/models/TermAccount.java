package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.Date;

/** Class that represent a banking term account
 *
 */
@Entity
@DiscriminatorValue("TERM_ACCOUNT")
public class TermAccount extends BankAccount {

    @Column(name="maximum_date")
    private Date maximumDate;

    @Column(name="penalty")
    private long penalty;

    /** Default constructor
     * (persistent classes requirements)
     */
    public TermAccount() {
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
     * @param maximumDate a Date
     * @param penalty a long
     */
    public TermAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, Date maximumDate, long penalty) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.TERM_ACCOUNT);
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
