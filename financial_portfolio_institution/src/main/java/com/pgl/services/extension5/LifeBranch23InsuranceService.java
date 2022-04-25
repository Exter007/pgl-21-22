package com.pgl.services.extension5;

import com.pgl.models.extension5.LifeBranch23Insurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class LifeBranch23InsuranceService extends HttpClientService<LifeBranch23Insurance> {

    private static final String referencePath = "/life23branch-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Life Branch 23 Insurance
     * LifeBranch23Insurance.class for deserializing JSON received from Rest API to Life Branch 23 Insurance
     */
    public LifeBranch23InsuranceService() {
        super(referencePath, LifeBranch23Insurance.class,
                new ParameterizedTypeReference<List<LifeBranch23Insurance>>() {});
    }

}
