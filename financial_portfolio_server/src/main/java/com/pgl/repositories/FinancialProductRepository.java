package com.pgl.repositories;

import com.pgl.models.ApplicationClient;
import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProduct;
import com.pgl.models.FinancialProductHolder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return financial product or list of them
 */
public interface FinancialProductRepository extends CrudRepository<FinancialProduct, Long> {

    /** Find financial products related to a financial institution in the database
     * by using the institution BIC code
     *
     * @param bic the BIC code
     * @return the list of financial products found or an empty list
     */
    @Query("SELECT r FROM FinancialProduct r where r.financialInstitution.BIC=:f")
    List<FinancialProduct> findProductsByInstitution(@Param("f") String bic);

    /** Find financial products of a specific type related to a financial institution in the database
     * by using the institution BIC code and a type of financial product
     *
     * @param bic the BIC code
     * @param productType the type
     * @return the list of financial products found or an empty list
     */
    @Query("SELECT r FROM FinancialProduct r where r.financialInstitution.BIC=:b and r.productType =:t ")
    List<FinancialProduct> findProductsByInstitutionAndProductType(@Param("b")String bic, @Param("t") FinancialProduct.PRODUCT_TYPE productType);

    /** Find the financial product of a specific type related to a financial institution in the database
     * by using the institution BIC code, a type of financial product and its IBAN
     *
     * @param bic the BIC code
     * @param iban the IBAN
     * @param accountProductType the type
     * @return the financial product found or null
     */
    @Query("SELECT r FROM BankAccount r where r.financialInstitution.BIC=:b and r.productType =:t and r.iban=:i ")
    FinancialProduct findAccountByInstitutionAndIBAN(@Param("b")String bic, @Param("i")String iban, @Param("t") FinancialProduct.PRODUCT_TYPE accountProductType);

    /** Find the financial product of a specific type in the database
     * by using the institution BIC code, a type of financial product and its IBAN
     *
     * @param iban the IBAN
     * @param accountProductType the type
     * @return the financial product found or null
     */
    @Query("SELECT r FROM BankAccount r where r.iban=:i and r.productType =:t")
    FinancialProduct findBankAccountByIBAN(@Param("i")String iban, @Param("t") FinancialProduct.PRODUCT_TYPE accountProductType);

    /** Find financial products of a specific type related to a wallet in the database
     * by using the wallet id and a type of financial product
     *
     * @param idWallet the wallet id
     * @param accountProductType the type
     * @return the list of financial products found or an empty list
     */
    @Query("SELECT f FROM FinancialProduct f, WalletFinancialProduct w WHERE w.wallet.id=:id and w.financialProduct.id = f.id and f.productType=:t ")
    List<FinancialProduct> findBankAccountsByWallet(@Param("id") Long idWallet,  @Param("t") FinancialProduct.PRODUCT_TYPE accountProductType);

    /** Find financial products related to a wallet in the database
     * by using the wallet id
     *
     * @param idWallet the wallet id
     * @return the list of financial products found or an empty list
     */
    @Query("SELECT f FROM FinancialProduct f, WalletFinancialProduct w WHERE w.wallet.id=:id and w.financialProduct.id = f.id")
    List<FinancialProduct> findProductsByWallet(@Param("id") Long idWallet);

    /** Find financial products related to an application client in the database
     * by using the application client national register number
     *
     * @param rn the national register number
     * @return the list of financial products found or an empty list
     */
    @Query("SELECT f FROM FinancialProduct f, WalletFinancialProduct w WHERE w.wallet.applicationClient.nationalRegister = :rn and w.financialProduct.id = f.id")
    List<FinancialProduct> findProductsByClient(@Param("rn") String rn);

    /** Find bank accounts
     *
     * @param accountProductType
     * @return the list of bank accounts found or an empty list
     */
    @Query("SELECT f FROM BankAccount f WHERE f.productType = :t")
    List<BankAccount> findBankAccountByNationalRegister(@Param("t") FinancialProduct.PRODUCT_TYPE accountProductType);

}
