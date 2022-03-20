package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.repositories.FinancialInstitutionRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service()
public class FinancialInstitutionService {
    @Autowired
    FinancialInstitutionRepository financialInstitutionRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    FinancialInstitutionRepository getRepository(){
        return financialInstitutionRepository;
    }

    public FinancialInstitution saveClient(FinancialInstitution user) throws Exception {

        Optional<FinancialInstitution> result = getRepository().findById(user.getBIC());

        //      if it is a new Institution and already exists with this BIC
        if(!user.toUpdate && result.isPresent()){
            throw new RuntimeException("This User already exists");
        }

        FinancialInstitution institution;

        //      if it's a new user and doesn't exist yet
        if (!result.isPresent()) {
            user.setCreationDate(new Date());
            institution = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            institution.setPassword(hashPW);
            institution.setLogin(user.getLogin());
            institution.setRole(User.ROLE.FINANCIAL_INSTITUTION);

        }else { // if the user already exists and update it
            institution = result.get();
            institution.setModificationDate(new Date());
        }

        return financialInstitutionRepository.save(institution);
    }

}
