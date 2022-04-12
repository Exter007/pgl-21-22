package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.RequestTransfer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestTransferRepository extends CrudRepository<RequestTransfer, Long> {
    @Modifying
    @Query("UPDATE RequestTransfer r set r.status=:s where r.id=:id")
    void updateRequestedTransfer(@Param("id") Long id, @Param("s") int status);

    @Query("SELECT r FROM RequestTransfer r WHERE r.applicationClient=:applicationClient AND r.bankAccount.financialInstitution=:financialInstitution")
    RequestTransfer existsByApplicationClientAndFinancialInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    @Query("SELECT r FROM RequestTransfer r WHERE r.bankAccount.financialInstitution.BIC=:bic")
    List<RequestTransfer> findAllByFinancialInstitution(@Param("bic") String bic);
}
