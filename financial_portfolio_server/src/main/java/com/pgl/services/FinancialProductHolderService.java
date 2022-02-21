package com.pgl.services;

import com.pgl.repositories.FinancialProductHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class FinancialProductHolderService {

    @Autowired
    FinancialProductHolderRepository financialProductHolderRepository;

    FinancialProductHolderRepository getRepository(){
        return financialProductHolderRepository;
    }
}
