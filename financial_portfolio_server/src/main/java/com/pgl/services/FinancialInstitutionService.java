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

    FinancialInstitutionRepository getRepository(){
        return financialInstitutionRepository;
    }


    /**
     * Saving a financial institution
     * @param user
     * @return
     */
    public FinancialInstitution saveInstitution(FinancialInstitution user){

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
            institution.setToken(Code.generateCode());
            institution.setRole(User.ROLE.FINANCIAL_INSTITUTION);

        }else { // if the user already exists and update it
            user.setModificationDate(new Date());
            institution = SerializationUtils.clone(user);
        }

        // Save Institution Address
        Address address = addressRepository.save(institution.getAddress());
        institution.setAddress(address);

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