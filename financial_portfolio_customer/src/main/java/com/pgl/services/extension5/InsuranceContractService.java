package com.pgl.services.extension5;

import com.pgl.models.extension5.InsuranceContract;
import com.pgl.services.HttpClientService;
import com.pgl.services.WalletService;
import com.pgl.utils.GlobalVariables;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class InsuranceContractService extends HttpClientService<InsuranceContract> {

    WalletService walletService = new WalletService();

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

    /**
     * Retrieve InsuranceContract from a Wallet
     * @return
     */
    public List<InsuranceContract> getInsuranceByWallet(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-wallet/"
                + walletService.getCurrentWallet().getId();

        return getListByURL(url);
    }

}
