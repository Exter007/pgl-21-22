package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.RequestWallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestWalletRepository extends CrudRepository<RequestWallet, Long> {
    @Query("SELECT r FROM RequestWallet r WHERE r.applicationClient=:applicationClient AND r.financialInstitution=:financialInstitution")
    RequestWallet existsByApplicationClientAndFinancialInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    @Query("SELECT r FROM RequestWallet r WHERE r.financialInstitution.BIC=:bic")
    List<RequestWallet> findAllByFinancialInstitution(@Param("bic") String bic);
}
