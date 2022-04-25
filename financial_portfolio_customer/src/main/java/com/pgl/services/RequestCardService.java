package com.pgl.services;

import com.pgl.models.extension1.RequestCard;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestCardService extends HttpClientService<RequestCard>{

    private static final String referencePath = "/request-wallet";

    public RequestCardService() {
        super(referencePath, RequestCard.class, new ParameterizedTypeReference<List<RequestCard>>() {});
    }
}
