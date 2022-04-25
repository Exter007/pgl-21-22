package com.pgl.repositories;

import com.pgl.models.BankAccount;
import com.pgl.models.extension1.Card;
import com.pgl.models.FinancialProduct;
import com.pgl.models.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    @Query(value = "SELECT r FROM Card r where :n in r.financialProductHolders AND r.productType = FinancialProduct.PRODUCT_TYPE.CARD", nativeQuery = true)
    public List<Card> findCardsByClient(@Param("n") String clientNumber);

    @Query(value = "SELECT r FROM Card r where :n in r.financialProductHolders AND r.productType = FinancialProduct.PRODUCT_TYPE.CARD AND r.financialInstitution.BIC=:b", nativeQuery = true)
    public List<Card> findCardsByClientAndInstitution(@Param("n") String clientNumber, @Param("b") String bic);

    @Query(value = "SELECT r FROM Card r where r.cardNumber=:i and r.productType = FinancialProduct.PRODUCT_TYPE.CARD", nativeQuery = true)
    public Card findCardByCardNumber(@Param("i")String cardNumber);

    @Query(value = "SELECT r FROM BankAccount r where r.iban = (SELECT c.bankAccount FROM Card c where c.cardNumber=:i) and r.pin_code=:p", nativeQuery = true)
    public BankAccount findLinkedAccount(@Param("i")String cardNumber, @Param("p")String pin);

    @Query(value = "SELECT f FROM FinancialProduct f, WalletFinancialProduct w WHERE w.wallet.id=:id and w.financialProduct.id = f.id and f.productType = FinancialProduct.PRODUCT_TYPE.CARD", nativeQuery = true)
    public List<Card> findCardsByWallet(@Param("id") Long id);
}
