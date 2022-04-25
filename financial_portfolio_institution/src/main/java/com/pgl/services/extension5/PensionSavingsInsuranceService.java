package com.pgl.services.extension5;

import com.pgl.models.extension5.PensionSavingsInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class PensionSavingsInsuranceService extends HttpClientService<PensionSavingsInsurance> {

    private static final String referencePath = "/pension-savings-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Pension Savings Insurance
     * PensionSavingsInsurance.class for deserializing JSON received from Rest API to Pension Savings Insurance
     */
    public PensionSavingsInsuranceService() {
        super(referencePath, PensionSavingsInsurance.class,
                new ParameterizedTypeReference<List<PensionSavingsInsurance>>() {});
    }

}
