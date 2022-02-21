package com.pgl.services;

import com.pgl.repositories.FinancialInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class FinancialInstitutionService {
    @Autowired
    FinancialInstitutionRepository financialInstitutionRepository;


    FinancialInstitutionRepository getRepository(){
        return financialInstitutionRepository;
    }
}
