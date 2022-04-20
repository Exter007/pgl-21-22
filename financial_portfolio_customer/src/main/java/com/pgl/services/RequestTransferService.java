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

}
