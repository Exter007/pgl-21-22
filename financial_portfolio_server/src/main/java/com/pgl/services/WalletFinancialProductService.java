package com.pgl.services;

import com.pgl.repositories.WalletFinancialProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class WalletFinancialProductService {

    @Autowired
    WalletFinancialProductRepository walletFinancialProductRepository;

    /**
     * Get Wallet Financial Product Repository
     * @return repository
     */
    WalletFinancialProductRepository getRepository(){
        return walletFinancialProductRepository;
    }

}
