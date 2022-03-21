package com.pgl.repositories;

import com.pgl.models.FinancialProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinancialProductRepository extends CrudRepository<FinancialProduct, Long> {

    @Query("SELECT r FROM FinancialProduct r where r.financialInstitution.BIC=:f")
    List<FinancialProduct> findAllByFinancialInstitution_BIC(@Param("f") String bic);
}
