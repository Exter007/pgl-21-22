package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FinancialProductService extends HttpClientService<LinkedHashMap> {

    private static FinancialProduct currentProduct;
    WalletService walletService = new WalletService();

    private static final String referencePath = "/product";

    /**
     * To define the action to perform on an object
     * false if creation of a new object
     * true if modifying an existing object
     */
    private static boolean edit = false;

    /**
     * Constructor vide
     * ParameterizedTypeReference pour la deserialisation du JSON recu de Rest API en Liste de Financial Product
     * FinancialProduct.class pour la deserialisation du JSON recu de Rest API en Financial Institution
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
     * Determine interface access mode
     * @return Mode of access to the interface represented by a boolean
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * Define the mode of access to the interface
     * @param edit
     */
    public void setEdit(boolean edit) {
        FinancialProductService.edit = edit;
    }

    /**
     * Remove the currently selected element
     */
    public void moveCurrentProduct(){
        setCurrentProduct(null);
    }


    /**
     * Retrieve Financial Products from a Wallet
     * @return
     */
    public List<FinancialProduct> getFinancialProductsByWallet(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-wallet/"
                + walletService.getCurrentWallet().getId();
        return deserialization(url);
    }

    public List<FinancialProduct> getFinancialProductsByClient( ApplicationClient client){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath+"/get-by-client/"
                + client.getNationalRegister();
        return deserialization(url);
    }

    private List<FinancialProduct> deserialization(String url){
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