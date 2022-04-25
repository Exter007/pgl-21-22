package com.pgl.services.extension5;

import com.pgl.models.extension5.LifeBranch21Insurance;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class LifeBranch21InsuranceService extends HttpClientService<LifeBranch21Insurance> {

    private static final String referencePath = "/life21branch-insurance";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Life Branch 21 Insurance
     * LifeBranch21Insurance.class for deserializing JSON received from Rest API to Life Branch 21 Insurance
     */
    public LifeBranch21InsuranceService() {
        super(referencePath, LifeBranch21Insurance.class,
                new ParameterizedTypeReference<List<LifeBranch21Insurance>>() {});
    }

}
