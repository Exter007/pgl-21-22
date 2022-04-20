package com.pgl.services;

import com.pgl.models.Request;
import com.pgl.models.RequestWallet;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestWalletService  extends HttpClientService<RequestWallet>{

    UserService userService = new UserService();

    private static final String referencePath = "/request-wallet";

    public RequestWalletService() {
        super(referencePath, RequestWallet.class, new ParameterizedTypeReference<List<RequestWallet>>() {});
    }

    public RequestWallet acceptRequestWallet(RequestWallet requestWallet){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/accept";
        return post(url, requestWallet);
    }

    public RequestWallet refuseRequestWallet(RequestWallet requestWallet){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/refuse";
        return post(url, requestWallet);
    }

    public List<RequestWallet> getPendingRequestWalletByInstitution() {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/pending/list/"
                + userService.getCurrentUser().getBIC();
        return getListByURL(url);
    }

}
