package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.Date;

@Entity
@DiscriminatorValue("TERM_ACCOUNT")
public class TermAccount extends BankAccount {

    @Column(name="maximum_date")
    private Date maximumDate;

    @Column(name="penalty")
    private long penalty;

    public TermAccount() {
    }

    public TermAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, Date maximumDate, long penalty) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.TERM_ACCOUNT);
        this.maximumDate = maximumDate;
        this.penalty = penalty;
    }

    public Date getMaximumDate() {
        return maximumDate;
    }

    public void setMaximumDate(Date maximumDate) {
        this.maximumDate = maximumDate;
    }

    public long getPenalty() {
        return penalty;
    }

    public void setPenalty(long penalty) {
        this.penalty = penalty;
    }
}
