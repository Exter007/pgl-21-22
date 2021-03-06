package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class FinancialInstitutionService extends HttpClientService<FinancialInstitution>{
    private static final String referencePath = "";
    UserService userService = new UserService();

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Financial Institution
     * FinancialInstitution.class for deserializing JSON received from Rest API to Financial Institution
     */
    public FinancialInstitutionService() {
        super(referencePath, FinancialInstitution.class,
                new ParameterizedTypeReference<List<FinancialInstitution>>() {});
    }

    /**
     * Check the password of a financial institution
     * @param password
     * @return
     */
    public boolean checkPassword(String password){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/check-password";
        FinancialInstitution institution = userService.getCurrentUser();
        institution.setPassword(password);

        return post2(url, institution);
    }

    /**
     * Update financial institution
     * @param institution
     * @return
     */
    public FinancialInstitution updateInstitution(FinancialInstitution institution){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/update";
        return post(url, institution);
    }

    /**
     * Update financial institution password
     * @param password
     * @return
     */
    public FinancialInstitution updatePassword(String password){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/password/update";
        FinancialInstitution institution = userService.getCurrentUser();
        institution.setPassword(password);
        return post(url, institution);
    }

    /**
     * Update Financial Institution language
     * @param language
     * @return
     */
    public FinancialInstitution updateLanguage(String language){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/language/update";
        FinancialInstitution institution = userService.getCurrentUser();
        institution.setLanguage(language);
        return post(url, institution);
    }
}
