package com.pgl.services;

import com.pgl.models.TermAccount;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class TermAccountService extends HttpClientService<TermAccount> {

    private static final String referencePath = "/term-account";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Term Account
     * TermAccount.class for deserializing JSON received from Rest API to Term Account
     */
    public TermAccountService() {
        super(referencePath, TermAccount.class,
                new ParameterizedTypeReference<List<TermAccount>>() {
                });
    }
}