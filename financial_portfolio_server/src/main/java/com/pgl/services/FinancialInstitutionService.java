package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.repositories.FinancialInstitutionRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

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

        FinancialInstitution userFound = getRepository().findByLogin(user.getLogin());

        //      If a User already exists with this login
        if(!user.toUpdate && userFound != null){
            throw new RuntimeException("This User already exists");
        }

        //      If is a new User
        if (userFound == null) {
            user.setCreationDate(new Date());
            userFound = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            userFound.setPassword(hashPW);
            userFound.setLogin(user.getLogin());
            userFound.setRole(User.ROLE.FINANCIAL_INSTITUTION);

        }else {
            userFound.setModificationDate(new Date());
        }

        return userFound;
    }

}
