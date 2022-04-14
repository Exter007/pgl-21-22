package com.pgl.repositories;

import com.pgl.models.FinancialProduct;
import com.pgl.models.WalletFinancialProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinancialProductRepository extends CrudRepository<FinancialProduct, Long> {

    @Query("SELECT r FROM FinancialProduct r where r.financialInstitution.BIC=:f")
    List<FinancialProduct> findProductsByInstitution(@Param("f") String bic);

    @Query("SELECT r FROM FinancialProduct r where r.financialInstitution.BIC=:b and r.productType =:t ")
    List<FinancialProduct> findProductsByInstitutionAndProductType(@Param("b")String bic, @Param("t") FinancialProduct.PRODUCT_TYPE productType);

    @Query("SELECT r FROM BankAccount r where r.financialInstitution.BIC=:b and r.productType =:t and r.iban=:i ")
    FinancialProduct findProductByInstitutionAndIBAN(@Param("b")String bic, @Param("i")String iban, @Param("t") FinancialProduct.PRODUCT_TYPE productType);

    @Query("SELECT f FROM FinancialProduct f, WalletFinancialProduct w WHERE w.wallet.id=:id and w.financialProduct.id = f.id")
    List<FinancialProduct> findProductsByWallet(@Param("id") Long id);
}
