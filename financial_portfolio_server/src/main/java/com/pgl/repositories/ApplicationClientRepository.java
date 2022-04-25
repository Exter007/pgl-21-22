package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/** Interface that manage query that return application client or list of them
 */
public interface ApplicationClientRepository extends CrudRepository<ApplicationClient, String> {

    /** Find an application client in the database
     * by using his login
     *
     * @param login the login
     * @return the application client found or null
     */
    @Query("SELECT r FROM ApplicationClient r where r.login=:l")
    ApplicationClient findByLogin(@Param("l") String login);

    /** Find an application client in the database
     * by using his national register number and his email
     *
     * @param registerNumber the national register number
     * @param email the email
     * @return the application client found or null
     */
    @Query("SELECT r FROM ApplicationClient r where r.nationalRegister=:n or r.email=:e")
    ApplicationClient findByNumberOrEmail(@Param("n") String registerNumber, @Param("e") String email);
}
