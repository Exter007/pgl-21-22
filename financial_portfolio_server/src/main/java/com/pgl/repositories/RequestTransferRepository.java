package com.pgl.repositories;

import com.pgl.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestTransferRepository extends CrudRepository<RequestTransfer, Long> {

    @Query("SELECT r FROM RequestTransfer r WHERE r.applicationClient=:applicationClient AND r.bankAccount.financialInstitution=:financialInstitution")
    RequestTransfer findRequestTransferByClientAndInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    @Query("SELECT r FROM RequestTransfer r WHERE r.bankAccount.financialInstitution.BIC=:bic")
    List<RequestTransfer> findAllByFinancialInstitution(@Param("bic") String bic);

    @Query("SELECT r FROM RequestTransfer r WHERE r.bankAccount.financialInstitution.BIC=:bic and r.status=:status")
    List<RequestTransfer> findPendingRequestTransfersByInstitution(@Param("bic") String bic, @Param("status") Request.REQUEST_STATUS pendingRequestStatus);
}
