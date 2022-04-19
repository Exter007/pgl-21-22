package com.pgl.services;

import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProduct;
import com.pgl.models.WalletFinancialProduct;
import com.pgl.repositories.WalletFinancialProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class WalletFinancialProductService {

    @Autowired
    WalletFinancialProductRepository walletFinancialProductRepository;

    WalletFinancialProductRepository getRepository(){
        return walletFinancialProductRepository;
    }

    public List<FinancialProduct> findFinancialProductsByWallet(Long idWallet){
        List<WalletFinancialProduct> walletFinancialProducts = getRepository()
                .findWalletFinancialProductByWallet(idWallet);

        List<FinancialProduct> financialProducts = new ArrayList<>();
        walletFinancialProducts.forEach(walletFinancialProduct -> {
            financialProducts.add(walletFinancialProduct.getFinancialProduct());
        });

        return financialProducts;
    }

    public List<FinancialProduct> findBankAccountsByWallet(Long idWallet){
        List<WalletFinancialProduct> walletFinancialProducts = getRepository()
                .findBankAccountsByWallet(idWallet, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT);

        List<FinancialProduct> bankAccounts = new ArrayList<>();
        walletFinancialProducts.forEach(walletFinancialProduct -> {
            bankAccounts.add(walletFinancialProduct.getFinancialProduct());
        });

        return bankAccounts;
    }
}
