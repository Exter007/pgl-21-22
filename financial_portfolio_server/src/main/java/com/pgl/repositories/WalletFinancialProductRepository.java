package com.pgl.repositories;

import com.pgl.models.FinancialProduct;
import com.pgl.models.WalletFinancialProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletFinancialProductRepository extends CrudRepository<WalletFinancialProduct, Long> {

    @Query("SELECT r FROM WalletFinancialProduct r where r.wallet.id=:w")
    List<WalletFinancialProduct> findWalletFinancialProductByWallet(@Param("w") Long idWallet);

    @Query("SELECT r FROM WalletFinancialProduct r where r.wallet.id=:w and r.financialProduct.productType=:t")
    List<WalletFinancialProduct> findBankAccountsByWallet(@Param("w") Long idWallet, @Param("t") FinancialProduct.PRODUCT_TYPE accountProductType);
}
