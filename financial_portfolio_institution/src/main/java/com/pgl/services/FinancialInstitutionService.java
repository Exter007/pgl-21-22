package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.utils.GlobalVariables;

public class FinancialInstitutionService extends HttpClientService<FinancialInstitution>{
    private static final String referencePath = "";
    UserService userService = new UserService();

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
}
