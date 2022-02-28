package com.pgl.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="BANK_ACCOUNT")
public class YoungAccount extends BankAccount {

    @Column(name="age_limit")
    private int age_limit;

    @Column(name="max_transaction_amount")
    private float max_transaction_amount;

    public YoungAccount() {

    }

    public YoungAccount(String iban, int pin_code, float amount, CURRENCY currency, float monthlyFee, float annualYield, int age_limit, float max_transaction_amount) {
        super(iban, ACCOUNT_TYPE.JOINT_ACCOUNT, pin_code, amount, currency, monthlyFee, annualYield);
        this.age_limit = age_limit;
        this.max_transaction_amount = max_transaction_amount;
    }
}
