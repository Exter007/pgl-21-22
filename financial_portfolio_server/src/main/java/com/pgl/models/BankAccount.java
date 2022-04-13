package com.pgl.models;

import javax.persistence.*;

/** Abstract class that represent a bank account
 *
 */
@Entity
@DiscriminatorValue(value="BANK_ACCOUNT")
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class BankAccount extends FinancialProduct {

    @Column(name="iban")
    private String iban;

    @Column(name="nature", nullable = false)
    private ACCOUNT_NATURE nature;

    @Column(name="account_type")
    private ACCOUNT_TYPE accountType;

    @Column(name="pin_code")
    private String pin_code;

    @Column(name="amount")
    private float amount;

    @Column(name="currency", nullable = false)
    private CURRENCY currency;

    @Column(name="monthlyFee")
    private float monthlyFee;

    @Column(name="annualYield")
    private float annualYield;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "joint_iban", referencedColumnName = "id")
    private CurrentAccount jointIban;


    /** Default constructor
     * (persistent classes requirements)
     */
    public BankAccount() {
        super(PRODUCT_TYPE.BANK_ACCOUNT);
    }

    /** Class constructor
     *
     * @param nature
     * @param transferAccess
     */
    public BankAccount(ACCOUNT_NATURE nature, TRANSFER_ACCESS transferAccess) {
        super(PRODUCT_TYPE.BANK_ACCOUNT, transferAccess);
        this.nature = nature;
    }

    /** Class constructor
     *
     * @param iban a String object
     * @param type a BankAccount.ACCOUNT_TYPE enum
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param transferAccess a FinancialProduct.TRANSFER_ACCESS enum
     * @param pin_code a String object
     * @param currency a BankAccount.CURRENCY enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this bank account
     * @param monthlyFee a float
     * @param annualYield a float
     */
    public BankAccount(String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, PRODUCT_STATE state, TRANSFER_ACCESS transferAccess, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield) {
        super(PRODUCT_TYPE.BANK_ACCOUNT, state, transferAccess, financialInstitution);
        this.iban = iban;
        this.accountType = type;
        this.pin_code = pin_code;
        this.amount = 0;
        this.currency = currency;
        this.monthlyFee = monthlyFee;
        this.annualYield = annualYield;
        this.nature = nature;
    }

    /** Get the IBAN
     *
     * @return the IBAN in the form of a String object
     */
    public String getIban() {
        return iban;
    }

    /** Set the IBAN
     *
     * @param iban a String object
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /** Get the type of account
     *
     * @return the type in the form of a BankAccount.ACCOUNT_TYPE enum
     */
    public ACCOUNT_TYPE getAccountType() {
        return accountType;
    }

    /** Set the type of account
     *
     * @param type a BankAccount.ACCOUNT_TYPE enum
     */
    public void setAccountType(ACCOUNT_TYPE type) {
        this.accountType = type;
    }

    /** Get the pin code
     *
     * @return the pin code in the form of a String object
     */
    public String getPin_code() {
        return pin_code;
    }

    /** Set the pin code
     *
     * @param pin_code a String object
     */
    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    /** Get the amount available
     *
     * @return the amount available in this account in the form of a float
     */
    public float getAmount() {
        return amount;
    }

    /** Set the amount available
     *
     * @param amount a float
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /** Get the currency of this account
     *
     * @return the currency in the form of a BankAccount.CURRENCY enum
     */
    public CURRENCY getCurrency() {
        return currency;
    }

    /** Set the currency of this account
     *
     * @param currency a BankAccount.CURRENCY enum
     */
    public void setCurrency(CURRENCY currency) {
        this.currency = currency;
    }

    /** Get the monthly fee of this account
     *
     * @return the monthly fee in the form of a float
     */
    public float getMonthlyFee() {
        return monthlyFee;
    }

    /** Set the monthly fee of this account
     *
     * @param monthlyFee a float
     */
    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    /** Get the annual yield of this account
     *
     * @return the annual yield in the form of a float
     */
    public float getAnnualYield() {
        return annualYield;
    }

    /** Set the annual yield of this account
     *
     * @param annualYield a float
     */
    public void setAnnualYield(float annualYield) {
        this.annualYield = annualYield;
    }

    /** Get the nature of this account
     *
     * @return the nature in the form of a BankAccount.ACCOUNT_NATURE enum
     */
    public ACCOUNT_NATURE getNature() {
        return nature;
    }

    /** Set the nature of this account
     *
     * @param nature a BankAccount.ACCOUNT_NATURE enum
     */
    protected void setNature(ACCOUNT_NATURE nature) {
        this.nature = nature;
    }

    /** Get the IBAN joint of this account
     *
     * @return
     */
    public CurrentAccount getJointIban() {
        return jointIban;
    }

    /** Set the IBAN joint of this account
     *
     * @param jointIban
     */
    public void setJointIban(CurrentAccount jointIban) {
        this.jointIban = jointIban;
    }

    /** Represent the account type
     */
    public enum ACCOUNT_TYPE {
        INDIVIDUAL_ACCOUNT,
        JOINT_ACCOUNT,
        ACCOUNT_UNDIVIDED
    }

    /** Represent the account nature
     */
    public enum ACCOUNT_NATURE {
        CURRENT_ACCOUNT,
        SAVING_ACCOUNT,
        YOUNG_ACCOUNT,
        TERM_ACCOUNT
    }

    /** Represent the currency
     */
    public enum CURRENCY {
        EURO
    }
}
