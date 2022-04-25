package com.pgl.repositories;

import com.pgl.models.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return wallet or list of wallets
 */
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    /** Find wallets related to a customer in the database
     * by using the customer national register number
     *
     * @param nationalRegister the customer national register number
     * @return the list of wallets found or an empty list
     */
    @Query("SELECT r FROM Wallet r where r.applicationClient.nationalRegister=:n")
    List<Wallet> findWalletsByClient(@Param("n") String nationalRegister);

    /** Find a wallet related to a customer and an institution in the database
     * by using the customer national register number and the institution BIC code
     *
     * @param nationalRegister the customer national register number
     * @param bic the institution BIC code
     * @return the wallet found or null
     */
    @Query("SELECT r FROM Wallet r WHERE r.applicationClient.nationalRegister=:n AND r.financialInstitution.BIC=:b")
    Wallet findByClientAndInstitution(@Param("n") String nationalRegister, @Param("b") String bic);
}
