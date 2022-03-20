package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.repositories.ApplicationClientRepository;
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
     * @throws Exception
     */
    public ApplicationClient saveClient(ApplicationClient user){

        Optional<ApplicationClient> result = getRepository().findById(user.getNationalRegister());

        //      if it is a new Client and already exists with this national register number
        if(!user.toUpdate && result.isPresent()){
            throw new RuntimeException("This User already exists");
        }

        ApplicationClient client;

        //      if it's a new user and doesn't exist yet
        if (!result.isPresent()) {
            user.setCreationDate(new Date());
            client = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            client.setPassword(hashPW);
            client.setLogin(user.getLogin());
            client.setRole(User.ROLE.APPLICATION_CLIENT);

        }else { // if the user already exists and update it
            client = result.get();
            client.setModificationDate(new Date());
        }

        return applicationClientRepository.save(client);
    }

}
