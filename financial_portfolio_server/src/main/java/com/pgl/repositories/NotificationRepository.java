package com.pgl.repositories;

import com.pgl.models.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return notification or list of them
 */
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    /** Find notifications related to an application client in the database
     * by using the application client national register number
     *
     * @param clientNumber the national register number
     * @return the list of notifications found or an empty list
     */
    @Query("SELECT r FROM Notification r where r.applicationClient.nationalRegister=:n")
    List<Notification> findNotificationsByClient(@Param("n") String clientNumber);

    /** Find notifications related to an application client in the database
     * by using the application client national register number and its status
     *
     * @param clientNumber the national register number
     * @param notification_status the status
     * @return the list of notifications found or an empty list
     */
    @Query("SELECT r FROM Notification r where r.applicationClient.nationalRegister=:n and r.status=:s")
    List<Notification> findNotificationsByStatusAndClient(@Param("n") String clientNumber, @Param("s")Notification.NOTIFICATION_STATUS notification_status);
}
