package com.pgl.services.extension5;

import com.pgl.models.extension5.FamilyInsurance;
import com.pgl.models.extension5.VehicleInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class FamilyInsuranceService extends HttpClientService<FamilyInsurance> {

    private static final String referencePath = "/family-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Family Insurance
     * FamilyInsurance.class for deserializing JSON received from Rest API to Family Insurance
     */
    public FamilyInsuranceService() {
        super(referencePath, FamilyInsurance.class,
                new ParameterizedTypeReference<List<FamilyInsurance>>() {});
    }

}
