package com.pgl.repositories;

import com.pgl.models.FinancialProductHolder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinancialProductHolderRepository extends CrudRepository<FinancialProductHolder, Long> {

    @Query("SELECT r FROM FinancialProductHolder r where r.financialInstitution.BIC=:b ")
    List<FinancialProductHolder> findHoldersByInstitution(@Param("b")String BIC);

    @Query("SELECT r FROM FinancialProductHolder r where r.financialInstitution.BIC=:b and r.nationalRegister=:n ")
    FinancialProductHolder findHolderByInstitutionAndClient(@Param("b")String BIC, @Param("n") String nationalRegister);
}
