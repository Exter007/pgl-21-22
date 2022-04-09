package com.pgl.services;

import com.pgl.models.RequestWallet;
import com.pgl.repositories.RequestWalletRepository;
import com.pgl.utils.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

public class RequestWalletService extends HttpClientService<RequestWallet>{

    private static final String referencePath = "/request-wallet";

    public RequestWalletService() {
        super(referencePath, RequestWallet.class, new ParameterizedTypeReference<List<RequestWallet>>() {});
    }
    public RequestWallet createRequestWallet(RequestWallet requestWallet){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/save";
        return post(url, requestWallet);
    }
}
