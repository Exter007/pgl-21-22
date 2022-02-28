package com.pgl.models;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.ManyToMany;

public class CurrentAccount extends BankAccount {

    public CurrentAccount(String iban, ACCOUNT_TYPE type, int pin_code, float amount, CURRENCY currency, float monthlyFee, float annualYield) {
        super(iban, type, pin_code, amount, currency, monthlyFee, annualYield);
    }
}
