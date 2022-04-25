package com.pgl.services.extension5;

import com.pgl.models.extension5.HabitationInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class HabitationInsuranceService extends HttpClientService<HabitationInsurance> {

    private static final String referencePath = "/habitation-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Habitation Insurance
     * HabitationInsurance.class for deserializing JSON received from Rest API to Habitation Insurance
     */
    public HabitationInsuranceService() {
        super(referencePath, HabitationInsurance.class,
                new ParameterizedTypeReference<List<HabitationInsurance>>() {});
    }

}
