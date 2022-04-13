package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.repositories.ApplicationClientRepository;
import com.pgl.utils.Code;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service()
public class ApplicationClientService {

    @Autowired
    private ApplicationClientRepository applicationClientRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationClientRepository getRepository(){
        return applicationClientRepository;
    }

    /**
     * Create a new user or update
     * @param user
     * @return
     */
    public ApplicationClient saveClient(ApplicationClient user){
        ApplicationClient client;

        client = getRepository().findByNumberOrEmail(user.getNationalRegister(), user.getEmail());

        //      if it is a new Client and already exists with this national register number
        if(!user.toUpdate && client != null){
            throw new RuntimeException("This User already exists");
        }

        //      if it's a new user and doesn't exist yet
        if (client == null) {
            user.setCreationDate(new Date());
            client = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            client.setPassword(hashPW);
            client.setLanguage(user.getLanguage());
            client.setToken(Code.generateCode());
            client.setRole(User.ROLE.APPLICATION_CLIENT);

        }else { // if the user already exists and update it
            user.setModificationDate(new Date());
            client = user;
        }

        return getRepository().save(client);
    }

    /**
     * Check if the password provided by the Application Client is correct
     * @param applicationClient
     * @return
     */
    public boolean checkPassword(ApplicationClient applicationClient){
        ApplicationClient result = getRepository().findById(applicationClient.getNationalRegister()).get();
        return bCryptPasswordEncoder.matches(applicationClient.getPassword(), result.getPassword());
    }
}
