package com.pgl.repositories;

import com.pgl.models.FinancialProduct;
import com.pgl.models.WalletFinancialProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return wallet financial product or list of them
 */
public interface WalletFinancialProductRepository extends CrudRepository<WalletFinancialProduct, Long> {

    /** Find wallet financial products in the database
     * by using a wallet id
     *
     * @param idWallet the wallet id
     * @return a list of wallet financial products found or null
     */
    @Query("SELECT r FROM WalletFinancialProduct r where r.wallet.id=:w")
    List<WalletFinancialProduct> findWalletFinancialProductByWallet(@Param("w") Long idWallet);

    /** Find bank accounts in the database
     * by using a wallet id and a type
     *
     * @param idWallet the wallet id
     * @param accountProductType the type
     * @return a list of bank account or null
     */
    @Query("SELECT r FROM WalletFinancialProduct r where r.wallet.id=:w and r.financialProduct.productType=:t")
    List<WalletFinancialProduct> findBankAccountsByWallet(@Param("w") Long idWallet, @Param("t") FinancialProduct.PRODUCT_TYPE accountProductType);
}
