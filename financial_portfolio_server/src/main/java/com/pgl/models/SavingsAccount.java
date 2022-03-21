package com.pgl.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.Date;

@Entity
@DiscriminatorValue("SAVING_ACCOUNT")
public class SavingsAccount extends BankAccount {

    @Column(name="loyalty_date")
    private Date loyaltyDate;

    @Column(name="loyalty_bonus")
    private int loyaltyBonus;

    /**
     * Default constructor (persistent classes requirements)
     */
    public SavingsAccount() {
    }

    public SavingsAccount(String iban, ACCOUNT_TYPE type, PRODUCT_STATE state, String pin_code, CURRENCY currency, FinancialInstitution financialInstitution, float monthlyFee, float annualYield, Date loyaltyDate, int loyaltyBonus) {
        super(iban, type, state, pin_code, currency, financialInstitution, monthlyFee, annualYield);
        this.setNature(ACCOUNT_NATURE.SAVING_ACCOUNT);
        this.loyaltyDate = loyaltyDate;
        this.loyaltyBonus = loyaltyBonus;
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
}
