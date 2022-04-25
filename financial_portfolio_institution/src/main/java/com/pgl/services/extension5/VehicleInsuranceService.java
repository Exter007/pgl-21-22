package com.pgl.services.extension5;

import com.pgl.models.extension5.VehicleInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class VehicleInsuranceService extends HttpClientService<VehicleInsurance> {

    private static final String referencePath = "/vehicle-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Vehicle Insurance
     * VehicleInsurance.class for deserializing JSON received from Rest API to Vehicle Insurance
     */
    public VehicleInsuranceService() {
        super(referencePath, VehicleInsurance.class,
                new ParameterizedTypeReference<List<VehicleInsurance>>() {});
    }

}
