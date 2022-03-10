package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.repositories.ApplicationClientRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service()
public class ApplicationClientService {

    @Autowired
    private ApplicationClientRepository applicationClientRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationClientRepository getRepository(){
        return applicationClientRepository;
    }

    public ApplicationClient saveClient(ApplicationClient user) throws Exception {

        ApplicationClient userFound = getRepository().findByLogin(user.getLogin());

        //      If a User already exists with this login
        if(userFound != null){
            throw new RuntimeException("This User already exists");
        }

        //      If is a new User
        if (userFound == null) {
            user.setCreationDate(new Date());
            userFound = SerializationUtils.clone(user);
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            userFound.setPassword(hashPW);
            userFound.setLogin(user.getEmail());
            userFound.setRole(User.ROLE.APPLICATION_CLIENT);

        }else {
            userFound.setModificationDate(new Date());
        }

        return userFound;
    }

}
