package com.pgl.repositories;

import com.pgl.models.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

    @Query("SELECT r FROM Wallet r where r.applicationClient.nationalRegister=:n")
    List<Wallet> findWalletsByClient(@Param("n") String clientNumber);
}
