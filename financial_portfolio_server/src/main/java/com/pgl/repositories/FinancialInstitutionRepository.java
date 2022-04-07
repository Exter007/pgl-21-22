package com.pgl.repositories;

import com.pgl.models.FinancialInstitution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FinancialInstitutionRepository extends CrudRepository<FinancialInstitution, String> {

    @Query("SELECT r FROM FinancialInstitution r where r.login=:l")
    FinancialInstitution findByLogin(@Param("l") String login);

    @Query("SELECT r FROM FinancialInstitution r where r.name=:l")
    FinancialInstitution findByName(@Param("l") String name);
}
