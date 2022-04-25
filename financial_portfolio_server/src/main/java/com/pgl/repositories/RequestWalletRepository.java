package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.Request;
import com.pgl.models.RequestWallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return RequestWallet or list of them
 */
public interface RequestWalletRepository extends CrudRepository<RequestWallet, Long> {

    /** Find the RequestWallet related to an application client and a financial institution in the database
     * by using the application client and the financial institution
     *
     * @param applicationClient the application client
     * @param financialInstitution the financial institution
     * @return the RequestWallet found or null
     */
    @Query("SELECT r FROM RequestWallet r WHERE r.applicationClient=:applicationClient AND r.financialInstitution=:financialInstitution")
    RequestWallet findRequestWalletByClientAndInstitution(@Param("applicationClient") ApplicationClient applicationClient, @Param("financialInstitution") FinancialInstitution financialInstitution);

    /** Find RequestWallets related to a financial institution in the database
     * by using the financial institution BIC code
     *
     * @param bic the BIC code
     * @return the list of RequestWallets found or an empty list
     */
    @Query("SELECT r FROM RequestWallet r WHERE r.financialInstitution.BIC=:bic")
    List<RequestWallet> findAllByFinancialInstitution(@Param("bic") String bic);

    /** Find RequestWallets related to a financial institution in the database
     * by using the financial institution BIC code and its status
     *
     * @param bic the BIC code
     * @param pendingRequestStatus the status
     * @return the list of RequestWallets found or an empty list
     */
    @Query("SELECT r FROM RequestWallet r WHERE r.financialInstitution.BIC=:bic and r.status=:status")
    List<RequestWallet> findPendingRequestWalletsByInstitution(@Param("bic") String bic, @Param("status")Request.REQUEST_STATUS pendingRequestStatus);

    /** Find the RequestWallet related to an application client and a financial institution in the database
     * by using the application client national register number and the financial institution BIC code
     *
     * @param nationalRegister the national register number
     * @param bic the BIC code
     * @return the RequestWallet found or null
     */
    @Query("SELECT r FROM RequestWallet r WHERE r.applicationClient.nationalRegister=:nationalRegister AND r.financialInstitution.BIC=:bic")
    RequestWallet findByApplicationClientAndFinancialInstitution(@Param("nationalRegister") String nationalRegister, @Param("bic") String bic);
}
