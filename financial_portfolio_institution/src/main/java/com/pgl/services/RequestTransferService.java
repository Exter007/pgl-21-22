package com.pgl.services;

import com.pgl.models.RequestTransfer;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestTransferService  extends HttpClientService<RequestTransfer>{

    private static final String referencePath = "/request-transfer";

    public RequestTransferService() {
        super(referencePath, RequestTransfer.class, new ParameterizedTypeReference<List<RequestTransfer>>() {});
    }

    public RequestTransfer updateRequestTransfer(RequestTransfer RequestTransfer){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/update";
        return post(url, RequestTransfer);
    }

    public List<RequestTransfer> getAllRequestTransfer(String bic) {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/list/" + bic;
        return getListByURL(url);
    }

    public RequestTransfer findById(Long id) {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/find-by-id/" + id;
        return getByURL(url);
    }
    // TODO: ATTENTION ! MODIFIER AUSSI DANS LE CUSTOMER !
}
