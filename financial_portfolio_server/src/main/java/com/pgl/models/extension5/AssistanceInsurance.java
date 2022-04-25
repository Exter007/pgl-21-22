package com.pgl.models.extension5;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("ASSISTANCE_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class AssistanceInsurance extends InsuranceContract{

    /** Default constructor
     * (persistent classes requirements)
     */
    public AssistanceInsurance() {
        super(INSURANCE_TYPE.ASSISTANCE_INSURANCE);
    }

    /** Class constructor
     *
     * @param insuranceNumber
     * @param insuranceType
     * @param annualPremium
     * @param insuranceTax
     * @param renewalDate
     * @param domiciliation
     * @param delayAlert
     */
    public AssistanceInsurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, delayAlert);
    }
}
