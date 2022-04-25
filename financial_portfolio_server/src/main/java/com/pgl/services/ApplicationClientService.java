package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.RequestTransfer;
import com.pgl.models.User;
import com.pgl.repositories.ApplicationClientRepository;
import com.pgl.utils.Code;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Service for Application Client
 */
@Service()
public class ApplicationClientService {

    @Autowired
    private ApplicationClientRepository applicationClientRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Get Application Client Repository
     * @return repository
     */
    public ApplicationClientRepository getRepository(){
        return applicationClientRepository;
    }

    /**
     * Create a new user or update
     * @param user
     * @return
     */
    public ApplicationClient saveClient(ApplicationClient user){

        ApplicationClient client = getRepository().findByNumberOrEmail(user.getNationalRegister(), user.getEmail());

        //      if it is a new Client and already exists with this national register number
        if(client != null){
            throw new RuntimeException("This User already exists");
        }else { //      if it's a new user and doesn't exist yet
            user.setCreationDate(new Date());
            client = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            client.setPassword(hashPW);
            client.setLanguage(user.getLanguage());
            client.setToken(Code.generateCode());
            client.setRole(User.ROLE.APPLICATION_CLIENT);
        }

        return getRepository().save(client);
    }

    /**
     * Update a Application Client
     * @param client
     * @return
     */
    @Transactional()
    public ApplicationClient updateClient(ApplicationClient client){

        ApplicationClient userFound = getRepository()
                .findById(client.getNationalRegister()).get();

        userFound.setFirstName(client.getFirstName());
        userFound.setName(client.getName());
        userFound.setLogin(client.getLogin());

        userFound.setModificationDate(new Date());

        return getRepository().save(userFound);
    }

    /**
     * Update Application Client password
     * @param client
     * @return
     */
    @Transactional()
    public ApplicationClient updatePassword(ApplicationClient client){

        ApplicationClient userFound = getRepository()
                .findById(client.getNationalRegister()).get();

        String hashPW = bCryptPasswordEncoder.encode(client.getPassword());
        userFound.setPassword(hashPW);
        userFound.setModificationDate(new Date());

        return getRepository().save(userFound);
    }

    /**
     * Update Language
     * @param client
     * @return
     */
    @Transactional()
    public ApplicationClient updateLanguage(ApplicationClient client){

        ApplicationClient userFound = getRepository()
                .findById(client.getNationalRegister()).get();

        userFound.setLanguage(client.getLanguage());

        userFound.setModificationDate(new Date());

        return getRepository().save(userFound);
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
