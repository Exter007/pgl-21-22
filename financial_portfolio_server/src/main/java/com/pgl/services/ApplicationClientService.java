package com.pgl.services;

import com.pgl.repositories.ApplicationClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class ApplicationClientService {

    @Autowired
    ApplicationClientRepository applicationClientRepository;


    public ApplicationClientRepository getRepository(){
        return applicationClientRepository;
    }

}
