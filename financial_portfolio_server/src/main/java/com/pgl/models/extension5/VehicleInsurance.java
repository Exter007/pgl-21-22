package com.pgl.models.extension5;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("VEHICLE_INSURANCE")
@DiscriminatorColumn(name="TYPE",discriminatorType= DiscriminatorType.STRING)
public class VehicleInsurance extends InsuranceContract{

    @Column(name = "immatriculation")
    private String immatriculation;

    /** Default constructor
     * (persistent classes requirements)
     */
    public VehicleInsurance() {
        super(INSURANCE_TYPE.VEHICLE_INSURANCE);
    }

    /**
     * Class constructor
     * @param insuranceNumber
     * @param insuranceType
     * @param annualPremium
     * @param insuranceTax
     * @param renewalDate
     * @param domiciliation
     * @param immatriculation
     * @param delayAlert
     */
    public VehicleInsurance(String insuranceNumber, INSURANCE_TYPE insuranceType, float annualPremium, float insuranceTax, Date renewalDate, boolean domiciliation, String immatriculation, boolean delayAlert) {
        super(insuranceNumber, insuranceType, annualPremium, insuranceTax, renewalDate, domiciliation, delayAlert);
        this.immatriculation = immatriculation;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }
}
