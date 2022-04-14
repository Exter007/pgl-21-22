package com.pgl.services;

import com.pgl.models.*;
import com.pgl.repositories.AddressRepository;
import com.pgl.repositories.FinancialInstitutionRepository;
import com.pgl.repositories.RequestTransferRepository;
import com.pgl.utils.Code;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service()
public class FinancialInstitutionService {
    @Autowired
    FinancialInstitutionRepository financialInstitutionRepository;

    @Autowired
    RequestTransferRepository requestTransferRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public FinancialInstitutionRepository getRepository(){
        return financialInstitutionRepository;
    }


    /**
     * Saving a financial institution
     * @param user
     * @return
     */
    @Transactional()
    public FinancialInstitution saveInstitution(FinancialInstitution user){

        FinancialInstitution institution = getRepository().findByBicOrEmail(user.getBIC(), user.getEmail());

        //      if it is a new Institution and already exists with this BIC
        if(institution != null){
            throw new RuntimeException("This User already exists");
        }else { //      if it's a new user and doesn't exist yet
            user.setCreationDate(new Date());
            institution = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            institution.setPassword(hashPW);
            institution.setLanguage(user.getLanguage());
            institution.setToken(Code.generateCode());
            institution.setRole(User.ROLE.FINANCIAL_INSTITUTION);

            // Save Institution Address
            Address address = addressRepository.save(institution.getAddress());
            institution.setAddress(address);
        }

        return getRepository().save(institution);
    }

    /**
     * Update a financial institution
     * @param institution
     * @return
     */
    @Transactional()
    public FinancialInstitution updateInstitution(FinancialInstitution institution){

        FinancialInstitution userFound = getRepository()
                .findById(institution.getBIC()).get();

        // Update Institution Address
        addressRepository.save(institution.getAddress());

        userFound.setName(institution.getName());
        userFound.setLogin(institution.getLogin());

        userFound.setModificationDate(new Date());

        return getRepository().save(userFound);
    }

    /**
     * Update financial institution password
     * @param institution
     * @return
     */
    @Transactional()
    public FinancialInstitution updatePassword(FinancialInstitution institution){

        FinancialInstitution userFound = getRepository()
                .findById(institution.getBIC()).get();

        String hashPW = bCryptPasswordEncoder.encode(institution.getPassword());
        userFound.setPassword(hashPW);
        userFound.setModificationDate(new Date());

        return getRepository().save(userFound);
    }

    public void answerTransfer(RequestTransfer rqt, int status) {
        requestTransferRepository.updateRequestedTransfer(rqt.getId(), status);
    }

    /**
     * Update Language
     * @param institution
     * @return
     */
    @Transactional()
    public FinancialInstitution updateLanguage(FinancialInstitution institution){

        FinancialInstitution userFound = getRepository()
                .findById(institution.getBIC()).get();

        userFound.setLanguage(institution.getLanguage());

        userFound.setModificationDate(new Date());

        return getRepository().save(userFound);
    }

    /**
     * Check if the password provided by the financial institution is correct
     * @param institution
     * @return
     */
    public boolean checkPassword(FinancialInstitution institution){
        FinancialInstitution result = getRepository().findById(institution.getBIC()).get();
        return bCryptPasswordEncoder.matches(institution.getPassword(), result.getPassword());
    }

}