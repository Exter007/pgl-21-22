package com.pgl.services.extension5;

import com.pgl.models.extension5.AssistanceInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class AssistanceInsuranceService extends HttpClientService<AssistanceInsurance> {

    private static final String referencePath = "/assistance-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Assistance Insurance
     * AssistanceInsurance.class for deserializing JSON received from Rest API to Assistance Insurance
     */
    public AssistanceInsuranceService() {
        super(referencePath, AssistanceInsurance.class,
                new ParameterizedTypeReference<List<AssistanceInsurance>>() {});
    }

}
