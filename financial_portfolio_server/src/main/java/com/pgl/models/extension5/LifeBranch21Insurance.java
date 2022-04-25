package com.pgl.models.extension5;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("LIFE_BRANCH_21_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class LifeBranch21Insurance extends LifeBranchInsurance{

    @Column(name = "deposit_guarantee")
    private float depositGuarantee;

    @Column(name = "withholding_tax")
    private float withholdingTax;

    /** Default constructor
     * (persistent classes requirements)
     */
    public LifeBranch21Insurance() {
        super(INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE);
    }

    /**
     * Class contructor
     * @param insuranceNumber
     * @param insuranceType
     * @param annualPremium
     * @param insuranceTax
     * @param renewalDate
     * @param domiciliation
     * @param admissionFee
     * @param annualReturn
     * @param duration
     * @param depositGuarantee
     * @param withholdingTax
     * @param delayAlert
     */
    public LifeBranch21Insurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, float admissionFee, float annualReturn, Date duration, float depositGuarantee, float withholdingTax, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, admissionFee, annualReturn, duration, delayAlert);
        this.depositGuarantee = depositGuarantee;
        this.withholdingTax = withholdingTax;
    }

    public float getDepositGuarantee() {
        return depositGuarantee;
    }

    public void setDepositGuarantee(float depositGuarantee) {
        this.depositGuarantee = depositGuarantee;
    }

    public float getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(float withholdingTax) {
        this.withholdingTax = withholdingTax;
    }
}
