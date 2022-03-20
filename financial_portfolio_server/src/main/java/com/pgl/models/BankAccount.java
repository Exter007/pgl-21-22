package com.pgl.models;

import javax.persistence.*;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="BANK_ACCOUNT")
@DiscriminatorColumn(name="ACCOUNT_NATURE")
//@MappedSuperclass
public abstract class BankAccount extends FinancialProduct {

//    @Id
    @Column(name="iban",unique = true, nullable = false)
    private String iban;

    @Column(name="nature", nullable = false)
    private ACCOUNT_NATURE nature;

    @Column(name="type", nullable = false)
    private ACCOUNT_TYPE type;

    @Column(name="pin_code", nullable = false)
    private String pin_code;

    @Column(name="amount")
    private float amount;

    @Column(name="currency", nullable = false)
    private CURRENCY currency;

    @Column(name="monthlyFee")
    private float monthlyFee;

    @Column(name="annualYield")
    private float annualYield;

    public BankAccount() {
    }

    public BankAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield) {
        super(state, financialInstitution);
        this.iban = iban;
        this.type = type;
        this.pin_code = pin_code;
        this.amount = 0;
        this.currency = currency;
        this.monthlyFee = monthlyFee;
        this.annualYield = annualYield;
    }

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

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
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

    public ACCOUNT_NATURE getNature() {
        return nature;
    }

    protected void setNature(ACCOUNT_NATURE nature) {
        this.nature = nature;
    }

    public enum ACCOUNT_TYPE {
        INDIVIDUAL_ACCOUNT,
        JOINT_ACCOUNT,
        ACCOUNT_UNDIVIDED
    }

    public enum CURRENCY {
        EURO
    }

    public enum ACCOUNT_NATURE {
        CURRENT_ACCOUNT,
        SAVING_ACCOUNT,
        YOUNG_ACCOUNT,
        TERM_ACCOUNT
    }
}
