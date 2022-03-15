package com.pgl.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="BANK_ACCOUNT")
//@MappedSuperclass
public class BankAccount extends FinancialProduct {

//    @Id
    @Column(name="iban",unique = true, nullable = false)
    private String iban;

    @Column(name="nature", nullable = false)
    private ACCOUNT_NATURE nature;

    @Column(name="type", nullable = false)
    private ACCOUNT_TYPE type;

    @Column(name="pin_code", nullable = false)
    private int pin_code;

    @Column(name="amount")
    private float amount;

    @Column(name="currency", nullable = false)
    private CURRENCY currency;

    @Column(name="monthlyFee")
    private float monthlyFee;

    @Column(name="annualYield")
    private float annualYield;

    /** YoungAccount fields  **/
    @Column(name="age_limit")
    private int ageLimit;

    @Column(name="max_transaction_amount")
    private float maxTransactionAmount;

    /** SavingsAccount fields  **/
    @Column(name="loyalty_date")
    private Date loyaltyDate;

    @Column(name="loyalty_bonus")
    private int loyaltyBonus;

    /** TermAccount fields  **/
    @Column(name="maximum_date")
    private Date maximumDate;

    @Column(name="penalty")
    private long penalty;

    public BankAccount() {
    }

    /** Base builder  **/
    public BankAccount(String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency, FinancialInstitution financialInstitution) {
        super(state, financialInstitution);
        this.iban = iban;
        this.pin_code = pin_code;
        this.currency = currency;
        this.type = type;
        this.nature = nature;
    }

    /** Builder for SavingsAccount  **/
    public BankAccount(String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency, Date loyaltyDate, int loyaltyBonus, FinancialInstitution financialInstitution) {
        super(state, financialInstitution);
        this.iban = iban;
        this.pin_code = pin_code;
        this.currency = currency;
        this.type = type;
        this.nature = nature;
        this.loyaltyDate = loyaltyDate;
        this.loyaltyBonus = loyaltyBonus;
    }

    /** Builder for YoungAccount  **/
    public BankAccount(String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency, int ageLimit, float maxTransactionAmount, FinancialInstitution financialInstitution ) {
        super(state,financialInstitution);
        this.iban = iban;
        this.pin_code = pin_code;
        this.currency = currency;
        this.type = type;
        this.nature = nature;
        this.ageLimit = ageLimit;
        this.maxTransactionAmount = maxTransactionAmount;
    }

    /** Builder for TermAccount  **/
    public BankAccount(String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, PRODUCT_STATE state, int pin_code, CURRENCY currency, Date maximumDate, long penalty, FinancialInstitution financialInstitution) {
        super(state,financialInstitution);
        this.iban = iban;
        this.pin_code = pin_code;
        this.currency = currency;
        this.type = type;
        this.nature = nature;
        this.maximumDate = maximumDate;
        this.penalty = penalty;
    }

    /** Builder for all attributes  **/
    public BankAccount(String wording, String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, int pin_code, float amount, CURRENCY currency ,PRODUCT_STATE state, FinancialInstitution financialInstitution, List<FinancialProductHolder> financialProductHolders, float monthlyFee, float annualYield, int ageLimit, float maxTransactionAmount, Date loyaltyDate, int loyaltyBonus, Date maximumDate, long penalty) {
        super(wording, state,financialInstitution, financialProductHolders);
        this.iban = iban;
        this.nature = nature;
        this.type = type;
        this.pin_code = pin_code;
        this.amount = amount;
        this.currency = currency;
        this.monthlyFee = monthlyFee;
        this.annualYield = annualYield;
        this.ageLimit = ageLimit;
        this.maxTransactionAmount = maxTransactionAmount;
        this.loyaltyDate = loyaltyDate;
        this.loyaltyBonus = loyaltyBonus;
        this.maximumDate = maximumDate;
        this.penalty = penalty;
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

    public ACCOUNT_NATURE getNature() {
        return nature;
    }

    public void setNature(ACCOUNT_NATURE nature) {
        this.nature = nature;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public float getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    public void setMaxTransactionAmount(float maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }

    public Date getLoyaltyDate() {
        return loyaltyDate;
    }

    public void setLoyaltyDate(Date loyaltyDate) {
        this.loyaltyDate = loyaltyDate;
    }

    public int getLoyaltyBonus() {
        return loyaltyBonus;
    }

    public void setLoyaltyBonus(int loyaltyBonus) {
        this.loyaltyBonus = loyaltyBonus;
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
