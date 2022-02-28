package com.pgl.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="BANK_ACCOUNT")
public class SavingsAccount extends BankAccount {

    @Column(name="loyalty_date")
    private Date loyalty_date;

    @Column(name="loyalty_bonus")
    private int loyalty_bonus;

    public SavingsAccount() {

    }

    public SavingsAccount(String iban,ACCOUNT_TYPE type, int pin_code, float amount, CURRENCY currency, float monthlyFee, float annualYield, Date loyalty_date, int loyalty_bonus) {
        super(iban, type, pin_code, amount, currency, monthlyFee, annualYield);
        this.loyalty_date = loyalty_date;
        this.loyalty_bonus = loyalty_bonus;
    }
}
