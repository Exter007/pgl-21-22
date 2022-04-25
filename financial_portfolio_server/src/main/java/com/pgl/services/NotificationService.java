package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.Notification;
import com.pgl.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    /**
     * Get Notification Repository
     * @return repository
     */
    public NotificationRepository getRepository(){
        return notificationRepository;
    }

    /**
     * Save notification
     * @param notification
     * @return notification saved
     */
    public Notification saveNotification(Notification notification){
        if (notification.getId()!=null){
            Optional<Notification> result = getRepository().findById(notification.getId());
            if (result.isPresent()){
                Notification not = result.get();
                not.setApplicationClient(notification.getApplicationClient());
                not.setFinancialInstitution(notification.getFinancialInstitution());
                not.setMessage(notification.getMessage());
                not.setStatus(notification.getStatus());
                not.setModificationDate(notification.getModificationDate());

                notification = not;
            }
        }

        return notificationRepository.save(notification);
    }

    /**
     * Save notification
     * @param client the Application Client
     * @param institution the Financial Institution
     * @param message
     * @return notification saved
     */
    public Notification saveNotification(ApplicationClient client, FinancialInstitution institution, String message){

        Notification notification = new Notification(
                message,
                Notification.NOTIFICATION_STATUS.UNREAD,
                client,
                institution
        );
        return notificationRepository.save(notification);
    }
}
