package com.pgl.services;

import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

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


    public FinancialProduct updateFinancialProduct(FinancialProduct financialProduct) {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/update";
        return post(url, financialProduct);
    }
}
