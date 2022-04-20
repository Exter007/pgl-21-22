package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

    @Query("SELECT r FROM Wallet r where r.applicationClient.nationalRegister=:n")
    List<Wallet> findWalletsByClient(@Param("n") String clientNumber);

    @Query("SELECT r FROM Wallet r WHERE r.applicationClient.nationalRegister=:n AND r.financialInstitution.BIC=:b")
    Wallet findByClientAndInstitution(@Param("n") String clientNumber, @Param("b") String bic);
}
