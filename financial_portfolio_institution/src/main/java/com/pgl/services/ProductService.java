package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProductService extends HttpClientService<LinkedHashMap> {

    UserService userService = new UserService();

    private static FinancialProduct currentProduct;

    /**
     * To define the action to perform on an object
     * false if creation of a new object
     * true if modifying an existing object
     */
    private static boolean edit = false;

    private static final String referencePath = "/product";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Financial Product
     * FinancialProduct.class for deserializing JSON received from Rest API to Financial Product
     */
    public ProductService() {
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
        ProductService.currentProduct = currentProduct;
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
        ProductService.edit = edit;
    }

    /**
     * Remove the currently selected element
     */
    public void moveCurrentProduct(){
        setCurrentProduct(null);
    }

    /**
     * Retrieve Financial Products from a Financial Institution
     * @return
     */
    public List<FinancialProduct> getFinancialProductsByInstitution(){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath +"/get-by-institution/"
                + userService.getCurrentUser().getBIC();

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
