package com.pgl.services;

import com.pgl.models.Notification;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class NotificationService extends HttpClientService<Notification>{
    UserService userService = new UserService();

    private static final String referencePath = "/notification";

    public NotificationService() {
        super(referencePath, Notification.class, new ParameterizedTypeReference<List<Notification>>() {});
    }

    public List<Notification> getUnreadNotificationsByClient(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/unread/get-by-client/"
                + userService.getCurrentUser().getNationalRegister();

        return getListByURL(url);
    }
}
