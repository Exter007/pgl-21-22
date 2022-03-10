package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicationClientRepository extends CrudRepository<ApplicationClient, String> {

    @Query("SELECT r FROM ApplicationClient r where r.login=:l")
    ApplicationClient findByLogin(@Param("l") String login);
}
