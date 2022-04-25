package com.pgl.models.extension5;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("FAMILY_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class FamilyInsurance extends InsuranceContract{

    @Column(name = "person_number")
    private int personNumber;

    /** Default constructor
     * (persistent classes requirements)
     */
    public FamilyInsurance() {
        super(INSURANCE_TYPE.FAMILY_INSURANCE);
    }

    /**
     * Class Constructor
     * @param insuranceNumber
     * @param insuranceType
     * @param annualPremium
     * @param insuranceTax
     * @param renewalDate
     * @param domiciliation
     * @param personNumber
     * @param delayAlert
     */
    public FamilyInsurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, int personNumber, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, delayAlert);
        this.personNumber = personNumber;
    }

    /**
     * Get person number
     * @return
     */
    public int getPersonNumber() {
        return personNumber;
    }

    /**
     * Set person number
     * @param personNumber
     */
    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }
}
