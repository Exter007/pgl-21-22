package com.pgl.models.extension5;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("LIFE_BRANCH_23_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class LifeBranch23Insurance extends LifeBranchInsurance{

    /** Default constructor
     * (persistent classes requirements)
     */
    public LifeBranch23Insurance() {
        super(INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE);
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
     * @param delayAlert
     */
    public LifeBranch23Insurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, float admissionFee, float annualReturn, Date duration, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, admissionFee, annualReturn, duration, delayAlert);
    }
}
