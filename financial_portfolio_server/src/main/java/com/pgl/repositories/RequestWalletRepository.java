package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.Request;
import com.pgl.models.RequestWallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestWalletRepository extends CrudRepository<RequestWallet, Long> {
    @Query("SELECT r FROM RequestWallet r WHERE r.applicationClient=:applicationClient AND r.financialInstitution=:financialInstitution")
    RequestWallet findRequestWalletByClientAndInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    @Query("SELECT r FROM RequestWallet r WHERE r.financialInstitution.BIC=:bic")
    List<RequestWallet> findAllByFinancialInstitution(@Param("bic") String bic);

    @Query("SELECT r FROM RequestWallet r WHERE r.financialInstitution.BIC=:bic and r.status=:status")
    List<RequestWallet> findPendingRequestWalletsByInstitution(@Param("bic") String bic, @Param("status")Request.REQUEST_STATUS pendingRequestStatus);

    @Query("SELECT r FROM RequestWallet r WHERE r.applicationClient.nationalRegister=:nationalRegister AND r.financialInstitution.BIC=:bic")
    RequestWallet findByApplicationClientAndFinancialInstitution(@Param("nationalRegister") String nationalRegister, @Param("bic") String bic);
}
