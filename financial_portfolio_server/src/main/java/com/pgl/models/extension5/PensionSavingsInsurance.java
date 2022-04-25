package com.pgl.models.extension5;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("PENSION_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class PensionSavingsInsurance extends LifeBranchInsurance{

    @Column(name = "tax_benefit")
    private float taxBenefit;

    public PensionSavingsInsurance() {
        super(INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE);
    }

    public PensionSavingsInsurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, float admissionFee, float annualReturn, Date duration, float taxBenefit, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, admissionFee, annualReturn, duration, delayAlert);
        this.taxBenefit = taxBenefit;
    }

    public float getTaxBenefit() {
        return taxBenefit;
    }

    public void setTaxBenefit(float taxBenefit) {
        this.taxBenefit = taxBenefit;
    }
}
