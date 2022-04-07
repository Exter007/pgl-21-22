package com.pgl.services;

import com.pgl.models.RequestWallet;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestWalletService  extends HttpClientService<RequestWallet>{

    private static final String referencePath = "/request-wallet";

    public RequestWalletService() {
        super(referencePath, RequestWallet.class, new ParameterizedTypeReference<List<RequestWallet>>() {});
    }

    public RequestWallet updateRequestWallet(RequestWallet requestWallet){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/update";
        return post(url, requestWallet);
    }
}
