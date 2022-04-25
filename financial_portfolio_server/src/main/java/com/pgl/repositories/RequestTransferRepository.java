package com.pgl.repositories;

import com.pgl.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return RequestTransfer or list of them
 */
public interface RequestTransferRepository extends CrudRepository<RequestTransfer, Long> {

    /** Find the RequestTransfer related to an application client and a financial institution in the database
     * by using the application client and the financial institution
     *
     * @param applicationClient the application client
     * @param financialInstitution the financial institution
     * @return the RequestTransfer found or null
     */
    @Query("SELECT r FROM RequestTransfer r WHERE r.applicationClient=:applicationClient AND r.bankAccount.financialInstitution=:financialInstitution")
    RequestTransfer findRequestTransferByClientAndInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    /** Find RequestTransfers related to a financial institution in the database
     * by using the financial institution BIC code
     *
     * @param bic the BIC code
     * @return the list of RequestTransfers found or an empty list
     */
    @Query("SELECT r FROM RequestTransfer r WHERE r.bankAccount.financialInstitution.BIC=:bic")
    List<RequestTransfer> findAllByFinancialInstitution(@Param("bic") String bic);

    /** Find RequestTransfers related to a financial institution in the database
     * by using the financial institution BIC code and its status
     *
     * @param bic the BIC code
     * @param pendingRequestStatus the status
     * @return the list of RequestTransfers found or an empty list
     */
    @Query("SELECT r FROM RequestTransfer r WHERE r.bankAccount.financialInstitution.BIC=:bic and r.status=:status")
    List<RequestTransfer> findPendingRequestTransfersByInstitution(@Param("bic") String bic, @Param("status") Request.REQUEST_STATUS pendingRequestStatus);
}
