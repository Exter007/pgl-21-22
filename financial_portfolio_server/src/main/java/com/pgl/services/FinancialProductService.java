package com.pgl.services;

import com.pgl.repositories.FinancialProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class FinancialProductService {

    @Autowired
    FinancialProductRepository financialProductRepository;


    FinancialProductRepository getRepository(){
        return financialProductRepository;
    }
}
