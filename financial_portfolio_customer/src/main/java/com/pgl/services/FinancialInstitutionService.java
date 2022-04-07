package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProductHolder;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class FinancialInstitutionService extends HttpClientService<FinancialInstitution>{

    private static final String referencePath = "/financialInstitution";
    /**
     * Constructor vide
     * ParameterizedTypeReference pour la deserialisation du JSON recu de Rest API en Liste de Financial Institution
     * FinancialInstitution.class pour la deserialisation du JSON recu de Rest API en Financial Institution
     */
    public FinancialInstitutionService() {
        super(referencePath, FinancialInstitution.class,
                new ParameterizedTypeReference<List<FinancialInstitution>>() {});
    }

    public FinancialInstitution getFinancialInstitutionByName(String name) {
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/" + name;
        return getByURL(url);
    }

    public List<FinancialInstitution> getAllFinancialInstitutions(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + "/institution/list";
        List<FinancialInstitution> financialInstitutions = getListByURL(url);
        System.out.println(financialInstitutions);
        return getListByURL(url);
    }
}