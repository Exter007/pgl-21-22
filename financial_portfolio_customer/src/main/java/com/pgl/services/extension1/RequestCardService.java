package com.pgl.services.extension1;

import com.pgl.models.extension1.RequestCard;
import com.pgl.services.HttpClientService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class RequestCardService extends HttpClientService<RequestCard> {

    private static final String referencePath = "/request-wallet";

    public RequestCardService() {
        super(referencePath, RequestCard.class, new ParameterizedTypeReference<List<RequestCard>>() {});
    }
}
