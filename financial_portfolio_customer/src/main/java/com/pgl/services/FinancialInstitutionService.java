package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class FinancialInstitutionService extends HttpClientService<FinancialInstitution>{

    private static FinancialInstitution currentInstitution;
    private static final String referencePath = "/institution";
    /**
     * Constructor vide
     * ParameterizedTypeReference pour la deserialisation du JSON recu de Rest API en Liste de Financial Institution
     * FinancialInstitution.class pour la deserialisation du JSON recu de Rest API en Financial Institution
     */
    public FinancialInstitutionService() {
        super(referencePath, FinancialInstitution.class,
                new ParameterizedTypeReference<List<FinancialInstitution>>() {});
    }

    public static FinancialInstitution getCurrentInstitution() {
        return currentInstitution;
    }

    public static void setCurrentInstitution(FinancialInstitution currentInstitution) {
        FinancialInstitutionService.currentInstitution = currentInstitution;
    }

    public List<FinancialInstitution> getAllFinancialInstitutions(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/list";
        List<FinancialInstitution> financialInstitutions = getListByURL(url);
        System.out.println(financialInstitutions);
        return financialInstitutions;
    }
}