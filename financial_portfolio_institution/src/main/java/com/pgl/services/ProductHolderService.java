package com.pgl.services;

import com.pgl.models.FinancialProductHolder;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class ProductHolderService extends HttpClientService<FinancialProductHolder>{

    UserService userService = new UserService();

    private static FinancialProductHolder currentHolder;

    /**
     * To define the action to perform on an object
     * false if creation of a new object
     * true if modifying an existing object
     */
    private static boolean edit = false;

    private static final String referencePath = "/holder";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Financial Product Holder
     * FinancialProductHolder.class for deserializing JSON received from Rest API to Financial Product Holder
     */
    public ProductHolderService() {
        super(referencePath, FinancialProductHolder.class,
                new ParameterizedTypeReference<List<FinancialProductHolder>>() {});
    }

    /**
     * Get the current customer selected in the list
     * @return
     */
    public FinancialProductHolder getCurrentHolder() {
        return currentHolder;
    }

    /**
     * Set the current customer selected in the list
     * @param currentHolder
     */
    public void setCurrentClient(FinancialProductHolder currentHolder) {
        ProductHolderService.currentHolder = currentHolder;
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
        ProductHolderService.edit = edit;
    }

    /**
     * Remove the currently selected element
     */
    public void moveCurrentClient(){
        setCurrentClient(null);
    }

    /**
     * Retrieve Customers from a Financial Institution
     * @return
     */
    public List<FinancialProductHolder> getHolderByInstitution(){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath +"/get-by-institution/"
                + userService.getCurrentUser().getBIC();

        return getListByURL(url);
    }

    /**
     * Retrieve Customer from his National Register number an a Financial Institution BIC
     * @return
     */
    public FinancialProductHolder getHolderByInstitutionAndRegisterNum(String registerNumber){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath +"/get-by-institution-and-client/"
                + userService.getCurrentUser().getBIC() + "/"
                + registerNumber;

        return getByURL(url);
    }
}