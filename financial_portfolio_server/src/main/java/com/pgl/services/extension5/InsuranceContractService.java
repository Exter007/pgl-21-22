package com.pgl.services.extension5;

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
