package com.pgl.services;

import com.pgl.models.FinancialProduct;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class FinancialProductService extends HttpClientService<FinancialProduct> {

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
    public FinancialProductService() {
        super(referencePath, FinancialProduct.class,
                new ParameterizedTypeReference<List<FinancialProduct>>() {});
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

}
