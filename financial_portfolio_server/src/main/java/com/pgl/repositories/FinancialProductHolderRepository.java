package com.pgl.repositories;

import com.pgl.models.FinancialProductHolder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FinancialProductHolderRepository extends CrudRepository<FinancialProductHolder, String> {

    @Query("SELECT r FROM FinancialProductHolder r where r.nationalRegister=:n and r.financialInstitution=:b ")
    FinancialProductHolder getFinancialProductHolderByID_BIC(@Param("n") String nationalRegister, @Param("b")String BIC);
}
