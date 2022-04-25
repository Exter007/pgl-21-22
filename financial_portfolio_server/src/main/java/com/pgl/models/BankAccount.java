package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import javax.persistence.*;

/** Abstract class that represent a bank account
 *
 */

@JsonSubTypes({
        @JsonSubTypes.Type(value = CurrentAccount.class, name = "CurrentAccount"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SavingsAccount"),
        @JsonSubTypes.Type(value = YoungAccount.class, name = "YoungAccount"),
        @JsonSubTypes.Type(value = TermAccount.class, name = "TermAccount")
})
@Entity
@DiscriminatorValue(value="BANK_ACCOUNT")
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class BankAccount extends FinancialProduct {

    @Column(name="iban")
    private String iban;

    @Column(name="nature")
    private ACCOUNT_NATURE nature;

    @Column(name="account_type")
    private ACCOUNT_TYPE accountType;

    @Column(name="pin_code")
    private String pin_code;

    @Column(name="currency")
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
     * @param nature a account nature enum
     * @param transferAccess a transfer access enum
     */
    public BankAccount(ACCOUNT_NATURE nature, TRANSFER_ACCESS transferAccess) {
        super(PRODUCT_TYPE.BANK_ACCOUNT, transferAccess);
        this.nature = nature;
    }

    /** Class constructor
     *
     * @param iban a String object that contains the iban
     * @param type a BankAccount.ACCOUNT_TYPE enum
     * @param state a FinancialProduct.PRODUCT_STATE enum
     * @param transferAccess a FinancialProduct.TRANSFER_ACCESS enum
     * @param pin_code a String object that contains the pin code
     * @param currency a BankAccount.CURRENCY enum
     * @param financialInstitution a FinancialInstitution object that represent the financial institution that provide this bank account
     * @param monthlyFee a float that contains the monthly fee
     * @param annualYield a float that contains the annual yield
     */
    public BankAccount(String iban, ACCOUNT_NATURE nature, ACCOUNT_TYPE type, PRODUCT_STATE state, TRANSFER_ACCESS transferAccess, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield) {
        super(PRODUCT_TYPE.BANK_ACCOUNT, state, transferAccess, financialInstitution);
        this.iban = iban;
        this.accountType = type;
        this.pin_code = pin_code;
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
     * @param iban a String object that contains the iban
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
     * @param pin_code a String object that contains the pin code
     */
    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
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
     * @param monthlyFee a float that contains the monthly fee
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
     * @param annualYield a float that contains the annual yield
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
     * @return the joint IBAN of this account
     */
    public CurrentAccount getJointIban() {
        return jointIban;
    }

    /** Set the IBAN joint of this account
     *
     * @param jointIban that contains the IBAN joint of this account
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
