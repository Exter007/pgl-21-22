package com.pgl.services;

import com.pgl.models.Address;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.RequestTransfer;
import com.pgl.models.User;
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
    public FinancialInstitution saveInstitution(FinancialInstitution user){
        FinancialInstitution institution;
        institution = getRepository().findByBicOrEmail(user.getBIC(), user.getEmail());

        //      if it is a new Institution and already exists with this BIC
        if(!user.toUpdate && institution != null){
            throw new RuntimeException("This User already exists");
        }

        //      if it's a new user and doesn't exist yet
        if (institution == null) {
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

        }else { // if the user already exists and update it
            if (user.getPassword() != null){
               user.setPassword(institution.getPassword());
            }

            // Update Institution Address
            addressRepository.save(institution.getAddress());

            user.setModificationDate(new Date());
            institution = user;
        }

        return financialInstitutionRepository.save(institution);
    }

    /**
     * Saving a financial institution
     * @param institution
     * @return
     */
    @Transactional()
    public FinancialInstitution updateInstitution(FinancialInstitution institution){

        // Update Institution Address
        addressRepository.save(institution.getAddress());

        institution.setModificationDate(new Date());

        return financialInstitutionRepository.save(institution);
    }

    public void answerTransfer(RequestTransfer rqt, int status) {
        requestTransferRepository.updateRequestedTransfer(rqt.getId(), status);
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