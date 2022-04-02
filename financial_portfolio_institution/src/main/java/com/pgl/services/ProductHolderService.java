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
     * false si création d'un nouvel object
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

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        ProductHolderService.edit = edit;
    }

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
}