package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FinancialProductService extends HttpClientService<LinkedHashMap>{

    private static final String referencePath = "/financialProducts";

    private static FinancialProduct currentFinancialProduct;
    /**
     * Constructor vide
     * ParameterizedTypeReference pour la deserialisation du JSON recu de Rest API en Liste de Financial Product
     * FinancialProduct.class pour la deserialisation du JSON recu de Rest API en Financial Institution
     */
    public FinancialProductService() {
        super(referencePath, LinkedHashMap.class,
                new ParameterizedTypeReference<List<LinkedHashMap>>() {});
    }

    public List<FinancialProduct> getFinancialProductsByWalletID(String walletID){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + "/wallet/" + walletID + "/product/list";
        List<LinkedHashMap> results = getListByURL(url);
        List<FinancialProduct> products = new ArrayList<>();
        results.forEach( result ->{
            ObjectMapper objectMapper = new ObjectMapper();
            if (result.get("classe").equals(CurrentAccount.class.getSimpleName()) ){
                products.add(objectMapper.convertValue(result, CurrentAccount.class));
            }else if (result.get("classe").equals(SavingsAccount.class.getSimpleName()) ){
                products.add(objectMapper.convertValue(result, SavingsAccount.class));
            }else if (result.get("classe").equals(YoungAccount.class.getSimpleName()) ){
                products.add(objectMapper.convertValue(result, YoungAccount.class));
            }else if (result.get("classe").equals(TermAccount.class.getSimpleName()) ){
                products.add(objectMapper.convertValue(result, TermAccount.class));
            }
        });

        return products;
    }

    public void setCurrentFinancialProduct(FinancialProduct financialProduct) {
        currentFinancialProduct = financialProduct;
    }

    public FinancialProduct getCurrentFinancialProduct() {
        return currentFinancialProduct;
    }
}