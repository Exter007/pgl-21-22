package com.pgl.services;

import com.pgl.models.CurrentAccount;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class CurrentAccountService extends HttpClientService<CurrentAccount>{

    private static final String referencePath = "/current-account";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Current Account
     * CurrentAccount.class for deserializing JSON received from Rest API to Current Account
     */
    public CurrentAccountService() {
        super(referencePath, CurrentAccount.class,
                new ParameterizedTypeReference<List<CurrentAccount>>() {});
    }
}
