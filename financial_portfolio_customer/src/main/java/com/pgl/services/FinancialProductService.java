package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FinancialProductService extends HttpClientService<LinkedHashMap> {
    WalletService walletService = new WalletService();

    private static FinancialProduct currentProduct;

    private static final String referencePath = "/product";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Financial Product
     * FinancialProduct.class for deserializing JSON received from Rest API to Financial Product
     */
    public FinancialProductService() {
        super(referencePath, LinkedHashMap.class,
                new ParameterizedTypeReference<List<LinkedHashMap>>() {});
    }

    /**
     * Get the current Financial Product selected in the list
     * @return
     */
    public FinancialProduct getCurrentProduct() {
        return currentProduct;
    }

    /**
     * Set the current Financial Product selected in the list
     * @param currentProduct
     */
    public void setCurrentProduct(FinancialProduct currentProduct) {
        FinancialProductService.currentProduct = currentProduct;
    }

    /**
     * Retrieve Bank Account from a IBAN and Financial Institution
     * @return
     */
    public FinancialProduct getAccountByIBAN(String iban){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + "/bank-account" +"/find-by-iban/"
                + iban;

        LinkedHashMap result = getByURL(url);
        FinancialProduct product = null;
        ObjectMapper objectMapper = new ObjectMapper();
        if (result.get("classe").equals(CurrentAccount.class.getSimpleName()) ){
            product = objectMapper.convertValue(result, CurrentAccount.class);
        }else if (result.get("classe").equals(SavingsAccount.class.getSimpleName()) ){
            product = objectMapper.convertValue(result, SavingsAccount.class);
        }else if (result.get("classe").equals(YoungAccount.class.getSimpleName()) ){
            product = objectMapper.convertValue(result, YoungAccount.class);
        }else if (result.get("classe").equals(TermAccount.class.getSimpleName()) ){
            product = objectMapper.convertValue(result, TermAccount.class);
        }

        return product;
    }

    public List<FinancialProduct> getAccountsByWallet(Long idWallet){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-wallet/"
                + idWallet;

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

}
