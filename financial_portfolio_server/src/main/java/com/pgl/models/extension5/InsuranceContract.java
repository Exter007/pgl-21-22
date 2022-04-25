package com.pgl.models.extension5;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.pgl.models.*;

import javax.persistence.*;
import java.util.Date;

@JsonSubTypes({
        @JsonSubTypes.Type(value = AssistanceInsurance.class, name = "AssistanceInsurance"),
        @JsonSubTypes.Type(value = FamilyInsurance.class, name = "FamilyInsurance"),
        @JsonSubTypes.Type(value = HabitationInsurance.class, name = "HabitationInsurance"),
        @JsonSubTypes.Type(value = HospitalizationInsurance.class, name = "HospitalizationInsurance"),
        @JsonSubTypes.Type(value = TravelInsurance.class, name = "TravelInsurance"),
        @JsonSubTypes.Type(value = VehicleInsurance.class, name = "VehicleInsurance"),
        @JsonSubTypes.Type(value = LifeBranch21Insurance.class, name = "LifeBranch21Insurance"),
        @JsonSubTypes.Type(value = LifeBranch23Insurance.class, name = "LifeBranch23Insurance"),
        @JsonSubTypes.Type(value = PensionSavingsInsurance.class, name = "PensionSavingsInsurance"),
})
@Entity
@DiscriminatorValue(value="INSURANCE_CONTRACT")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public abstract class InsuranceContract extends FinancialProduct {

    @Column(name = "insurance_number")
    private String insuranceNumber;

    @Column(name = "insurance_type")
    private INSURANCE_TYPE insuranceType;

    @Column(name = "annual_premium")
    private float annualPremium;

    @Column(name = "insurance_tax")
    private float insuranceTax;

    @Column(name = "renewal_date")
    private Date renewalDate;

    @Column(name = "domiciliation")
    private boolean domiciliation;

    @Column(name = "delay_alert")
    private boolean delayAlert;


    public InsuranceContract() {
        super(PRODUCT_TYPE.INSURANCE_CONTRACT, TRANSFER_ACCESS.UNAVAILABLE);
        this.delayAlert = false;
    }

    public InsuranceContract(INSURANCE_TYPE insuranceType) {
        super(PRODUCT_TYPE.INSURANCE_CONTRACT, TRANSFER_ACCESS.UNAVAILABLE);
        this.insuranceType = insuranceType;
        this.delayAlert = false;
    }

    public InsuranceContract(INSURANCE_TYPE insuranceType, TRANSFER_ACCESS transferAccess) {
        super(PRODUCT_TYPE.INSURANCE_CONTRACT, transferAccess);
        this.insuranceType = insuranceType;
        this.delayAlert = false;
    }

    public InsuranceContract(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, boolean delayAlert) {
        super(PRODUCT_TYPE.INSURANCE_CONTRACT, TRANSFER_ACCESS.UNAVAILABLE);
        this.insuranceNumber = insuranceNumber;
        this.insuranceType = insuranceType;
        this.annualPremium = annualPremium;
        this.insuranceTax = insuranceTax;
        this.renewalDate = renewalDate;
        this.domiciliation = domiciliation;
        this.delayAlert = delayAlert;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public INSURANCE_TYPE getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(INSURANCE_TYPE insuranceType) {
        this.insuranceType = insuranceType;
    }

    public float getAnnualPremium() {
        return annualPremium;
    }

    public void setAnnualPremium(float annualPremium) {
        this.annualPremium = annualPremium;
    }

    public float getInsuranceTax() {
        return insuranceTax;
    }

    public void setInsuranceTax(float insuranceTax) {
        this.insuranceTax = insuranceTax;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public boolean isDomiciliation() {
        return domiciliation;
    }

    public void setDomiciliation(boolean domiciliation) {
        this.domiciliation = domiciliation;
    }

    public boolean isDelayAlert() {
        return delayAlert;
    }

    public void setDelayAlert(boolean delayAlert) {
        this.delayAlert = delayAlert;
    }

    /** Represent the Insurance type
     */
    public enum INSURANCE_TYPE {
        ASSISTANCE_INSURANCE,
        FAMILY_INSURANCE,
        HABITATION_INSURANCE,
        HOSPITALIZATION_INSURANCE,
        TRAVEL_INSURANCE,
        VEHICLE_INSURANCE,
        LIFE_BRANCH_21_INSURANCE,
        LIFE_BRANCH_23_INSURANCE,
        PENSION_SAVINGS_INSURANCE
    }
}
