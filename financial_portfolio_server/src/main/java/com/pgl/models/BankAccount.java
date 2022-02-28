package com.pgl.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="BANK_ACCOUNT")
public abstract class BankAccount extends PersistentWithoutId {

    public enum ACCOUNT_TYPE {
        INDIVIDUAL_ACCOUNT,
        JOINT_ACCOUNT,
        ACCOUNT_UNDIVIDED
    };

    public enum CURRENCY {
        EURO
    };

    @Id
    @Column(name="iban",unique = true,nullable = false)
    private String iban;

    @Column(name="type")
    private ACCOUNT_TYPE type;

    @Column(name="pin_code")
    private int pin_code;

    @Column(name="amount")
    private float amount;

    @Column(name="currency")
    private CURRENCY currency;

    @Column(name="monthlyFee")
    private float monthlyFee;

    @Column(name="annualYield")
    private float annualYield;

    public BankAccount() {
    }

    public BankAccount(String iban, ACCOUNT_TYPE type, int pin_code, float amount, CURRENCY currency, float monthlyFee, float annualYield) {
        this.iban = iban;
        this.type = type;
        this.pin_code = pin_code;
        this.amount = amount;
        this.currency = currency;
        this.monthlyFee = monthlyFee;
        this.annualYield = annualYield;
    }

    //TODO : Implementer les methodes relatives a BankAccount

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public ACCOUNT_TYPE getType() {
        return type;
    }

    public void setType(ACCOUNT_TYPE type) {
        this.type = type;
    }

    public int getPin_code() {
        return pin_code;
    }

    public void setPin_code(int pin_code) {
        this.pin_code = pin_code;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public CURRENCY getCurrency() {
        return currency;
    }

    public void setCurrency(CURRENCY currency) {
        this.currency = currency;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public float getAnnualYield() {
        return annualYield;
    }

    public void setAnnualYield(float annualYield) {
        this.annualYield = annualYield;
    }
}
