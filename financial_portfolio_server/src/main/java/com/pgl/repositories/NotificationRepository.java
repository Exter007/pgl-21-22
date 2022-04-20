package com.pgl.repositories;

import com.pgl.models.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query("SELECT r FROM Notification r where r.applicationClient.nationalRegister=:n")
    List<Notification> findNotificationsByClient(@Param("n") String clientNumber);
}
