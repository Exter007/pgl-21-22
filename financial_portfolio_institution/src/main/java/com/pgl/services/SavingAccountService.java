package com.pgl.services;

import com.pgl.models.SavingsAccount;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class SavingAccountService extends HttpClientService<SavingsAccount> {

    private static final String referencePath = "/saving-account";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Savings Account
     * SavingsAccount.class for deserializing JSON received from Rest API to Savings Account
     */
    public SavingAccountService() {
        super(referencePath, SavingsAccount.class,
                new ParameterizedTypeReference<List<SavingsAccount>>() {
                });
    }
}
