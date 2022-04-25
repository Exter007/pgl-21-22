package com.pgl.models.extension5;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("HABITATION_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class HabitationInsurance extends InsuranceContract{

    @Column(name = "house_address")
    private String houseAddress;

    /** Default constructor
     * (persistent classes requirements)
     */
    public HabitationInsurance() {
        super(INSURANCE_TYPE.HABITATION_INSURANCE);
    }

    /**
     * Class Constructor
     * @param insuranceNumber
     * @param insuranceType
     * @param annualPremium
     * @param insuranceTax
     * @param renewalDate
     * @param domiciliation
     * @param houseAddress
     * @param delayAlert
     */
    public HabitationInsurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, String houseAddress, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, delayAlert);
        this.houseAddress = houseAddress;
    }

    /**
     * Get House Address
     * @return
     */
    public String getHouseAddress() {
        return houseAddress;
    }

    /**
     * set House Address
     * @param houseAddress
     */
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
}
