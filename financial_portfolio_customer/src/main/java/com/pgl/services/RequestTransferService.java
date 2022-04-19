package com.pgl.services;

import com.pgl.models.RequestTransfer;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestTransferService extends HttpClientService<RequestTransfer>{

    private static final String referencePath = "/request-transfer";

    public RequestTransferService() {
        super(referencePath, RequestTransfer.class, new ParameterizedTypeReference<List<RequestTransfer>>() {});
    }
    public RequestTransfer createRequestTransfer(RequestTransfer requestTransfer){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/save";
        return post(url, requestTransfer);
    }

    public boolean deleteByInstitutionBICAndApplicationID(String financialInstitutionBIC, String applicationClientID){
        //return deleteRequestTransfer(financialInstitutionBIC, applicationClientID);
        return false;
    }
}
