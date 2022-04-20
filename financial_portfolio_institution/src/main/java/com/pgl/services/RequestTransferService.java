package com.pgl.services;

import com.pgl.models.RequestTransfer;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestTransferService  extends HttpClientService<RequestTransfer>{

    UserService userService = new UserService();

    private static final String referencePath = "/request-transfer";

    public RequestTransferService() {
        super(referencePath, RequestTransfer.class, new ParameterizedTypeReference<List<RequestTransfer>>() {});
    }

    public RequestTransfer acceptRequestTransfer(RequestTransfer RequestTransfer){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/accept";
        return post(url, RequestTransfer);
    }

    public RequestTransfer refuseRequestTransfer(RequestTransfer RequestTransfer){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/refuse";
        return post(url, RequestTransfer);
    }

    public List<RequestTransfer> getPendingRequestTransferByInstitution() {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/pending/list/"
                + userService.getCurrentUser().getBIC();
        return getListByURL(url);
    }

}
