package com.pgl.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="BANK_ACCOUNT")
public class TermAccount extends BankAccount {

    @Column(name="maximum_date")
    private Date maximum_date;

    @Column(name="loyalty_bonus")
    private long penalty;

    public TermAccount() {

    }

    public TermAccount(String iban, ACCOUNT_TYPE type, int pin_code, float amount, CURRENCY currency, float monthlyFee, float annualYield, Date maximum_date, long penalty) {
        super(iban, type, pin_code, amount, currency, monthlyFee, annualYield);
        this.maximum_date = maximum_date;
        this.penalty = penalty;
    }
}
