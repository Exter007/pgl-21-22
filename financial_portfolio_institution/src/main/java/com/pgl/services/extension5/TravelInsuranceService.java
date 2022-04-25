package com.pgl.services.extension5;

import com.pgl.models.extension5.TravelInsurance;
import com.pgl.models.extension5.VehicleInsurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class TravelInsuranceService extends HttpClientService<TravelInsurance> {

    private static final String referencePath = "/travel-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Travel Insurance
     * TravelInsurance.class for deserializing JSON received from Rest API to Travel Insurance
     */
    public TravelInsuranceService() {
        super(referencePath, TravelInsurance.class,
                new ParameterizedTypeReference<List<TravelInsurance>>() {});
    }

}
