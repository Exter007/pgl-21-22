package com.pgl.services;

import com.pgl.models.YoungAccount;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class YoungAccountService extends HttpClientService<YoungAccount> {

    private static final String referencePath = "/young-account";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Young Account
     * YoungAccount.class for deserializing JSON received from Rest API to YoungAccount
     */
    public YoungAccountService() {
        super(referencePath, YoungAccount.class,
                new ParameterizedTypeReference<List<YoungAccount>>() {
                });
    }
}