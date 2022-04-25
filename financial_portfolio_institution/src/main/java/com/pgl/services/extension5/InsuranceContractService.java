package com.pgl.services.extension5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.models.extension5.InsuranceContract;
import com.pgl.services.HttpClientService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class InsuranceContractService extends HttpClientService<InsuranceContract> {

    UserService userService = new UserService();

    private static InsuranceContract currentInsurance;

    /**
     * To define the action to perform on an object
     * false if creation of a new object
     * true if modifying an existing object
     */
    private static boolean edit = false;

    private static final String referencePath = "/insurance-contract";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Insurance Contract
     * InsuranceContract.class for deserializing JSON received from Rest API to Insurance Contract
     */
    public InsuranceContractService() {
        super(referencePath, InsuranceContract.class,
                new ParameterizedTypeReference<List<InsuranceContract>>() {});
    }

    /**
     * Get the current Insurance Contract selected in the list
     * @return
     */
    public InsuranceContract getCurrentInsurance() {
        return currentInsurance;
    }

    /**
     * Set the current Insurance Contract selected in the list
     * @param currentInsurance
     */
    public void setCurrentInsurance(InsuranceContract currentInsurance) {
        InsuranceContractService.currentInsurance = currentInsurance;
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
        InsuranceContractService.edit = edit;
    }

    /**
     * Remove the currently selected element
     */
    public void moveCurrentInsuranceContract(){
        setCurrentInsurance(null);
    }

    public InsuranceContract getInsuranceByNumber(String insuranceNumber){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath +"/get-by-number/"
                + insuranceNumber;

        return getByURL(url);
    }

    /**
     * Retrieve Insurance Contract from a Financial Institution
     * @return
     */
    public List<InsuranceContract> getInsuranceContractsByInstitution(){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath +"/get-by-institution/"
                + userService.getCurrentUser().getBIC();

        return getListByURL(url);
    }

}
