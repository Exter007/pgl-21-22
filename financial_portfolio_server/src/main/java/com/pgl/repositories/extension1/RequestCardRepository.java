package com.pgl.repositories.extension1;

import com.pgl.models.*;
import com.pgl.models.extension1.RequestCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestCardRepository extends CrudRepository<RequestCard, Long> {
    @Query("SELECT r FROM RequestCard r WHERE r.applicationClient=:applicationClient AND r.institution=:financialInstitution")
    RequestCard findRequestCardByClientAndInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    @Query("SELECT r FROM RequestCard r WHERE r.institution.BIC=:bic")
    List<RequestCard> findAllByFinancialInstitution(@Param("bic") String bic);

    @Query("SELECT r FROM RequestCard r WHERE r.institution.BIC=:bic and r.status=:status")
    List<RequestCard> findPendingRequestCardsByInstitution(@Param("bic") String bic, @Param("status")Request.REQUEST_STATUS pendingRequestStatus);

    @Query("SELECT r FROM RequestCard r WHERE r.applicationClient.nationalRegister=:nationalRegister AND r.institution.BIC=:bic")
    RequestCard findByApplicationClientAndFinancialInstitution(@Param("nationalRegister") String nationalRegister, @Param("bic") String bic);
}
