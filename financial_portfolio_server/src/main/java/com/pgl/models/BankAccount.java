package com.pgl.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="BANK_ACCOUNT")
public class BankAccount {
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
    private float monthlyfee;

    @Column(name="creationDate")
    private Date creationDate;

    @Column(name="annualYield")
    private float annualYield;

    @Column(name="lastUpdate")
    private Date lastUpdate;

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

    public float getMonthlyfee() {
        return monthlyfee;
    }

    public void setMonthlyfee(float monthlyfee) {
        this.monthlyfee = monthlyfee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public float getAnnualYield() {
        return annualYield;
    }

    public void setAnnualYield(float annualYield) {
        this.annualYield = annualYield;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
