package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.Notification;
import com.pgl.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public NotificationRepository getRepository(){
        return notificationRepository;
    }

    public Notification saveClientNotification(ApplicationClient client, String message){

        Notification notification = new Notification(
                message,
                Notification.NOTIFICATION_STATUS.UNREAD,
                client
        );
        return notificationRepository.save(notification);
    }
}
