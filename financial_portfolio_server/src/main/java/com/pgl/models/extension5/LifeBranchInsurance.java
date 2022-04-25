package com.pgl.models.extension5;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import javax.persistence.*;
import java.util.Date;

@JsonSubTypes({
        @JsonSubTypes.Type(value = LifeBranch21Insurance.class, name = "LifeBranch21Insurance"),
        @JsonSubTypes.Type(value = LifeBranch23Insurance.class, name = "LifeBranch23Insurance"),
        @JsonSubTypes.Type(value = PensionSavingsInsurance.class, name = "PensionSavingsInsurance"),
})
@Entity
@DiscriminatorValue("LIFE_BRANCH_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public abstract class LifeBranchInsurance extends InsuranceContract{

    @Column(name = "admission_fees")
    private float admissionFee;

    @Column(name = "annual_return")
    private float annualReturn;

    @Column(name = "duration")
    private Date duration;


    /** Default constructor
     * (persistent classes requirements)
     */
    public LifeBranchInsurance() {
    }

    /**
     * Class constructor
     * @param insuranceType
     */
    public LifeBranchInsurance(INSURANCE_TYPE insuranceType) {
        super(insuranceType, TRANSFER_ACCESS.UNAVAILABLE);
    }

    /**
     * Class constructor
     * @param insuranceNumber
     * @param insuranceType
     * @param annualPremium
     * @param insuranceTax
     * @param renewalDate
     * @param domiciliation
     * @param admissionFee
     * @param annualReturn
     * @param duration
     * @param delayAlert
     */
    public LifeBranchInsurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, float admissionFee, float annualReturn, Date duration, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, delayAlert);
        this.admissionFee = admissionFee;
        this.annualReturn = annualReturn;
        this.duration = duration;
    }

    public float getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(float admissionFee) {
        this.admissionFee = admissionFee;
    }

    public float getAnnualReturn() {
        return annualReturn;
    }

    public void setAnnualReturn(float annualReturn) {
        this.annualReturn = annualReturn;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
