package com.pgl.services.extension5;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProduct;
import com.pgl.models.Wallet;
import com.pgl.models.WalletFinancialProduct;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.repositories.WalletFinancialProductRepository;
import com.pgl.repositories.WalletRepository;
import com.pgl.repositories.extension5.InsuranceContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class InsuranceContractService {

    @Autowired
    InsuranceContractRepository insuranceContractRepository;


    public InsuranceContractRepository getRepository(){
        return insuranceContractRepository;
    }
}
