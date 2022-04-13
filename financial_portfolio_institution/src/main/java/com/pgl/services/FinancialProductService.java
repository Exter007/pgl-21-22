package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.List;

public class FinancialProductService extends HttpClientService<FinancialProduct> {

    private static final String referencePath = "/product";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Financial Product
     * FinancialProduct.class for deserializing JSON received from Rest API to Financial Product
     */
    public FinancialProductService() {
        super(referencePath, FinancialProduct.class,
                new ParameterizedTypeReference<List<FinancialProduct>>() {});
    }


}
