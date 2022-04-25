package com.pgl.services.extension5;

import com.pgl.models.extension5.HospitalizationInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class HospitalizationInsuranceService extends HttpClientService<HospitalizationInsurance> {

    private static final String referencePath = "/hospitalization-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Hospitalization Insurance
     * HospitalizationInsurance.class for deserializing JSON received from Rest API to Hospitalization Insurance
     */
    public HospitalizationInsuranceService() {
        super(referencePath, HospitalizationInsurance.class,
                new ParameterizedTypeReference<List<HospitalizationInsurance>>() {});
    }

}
