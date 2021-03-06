package com.pgl.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/** Class that represent a wallet financial product
 *
 */
@Entity
@Table(name = "WALLET_FINANCIAL_PRODUCT")
public class WalletFinancialProduct implements Serializable {

    @EmbeddedId
    WalletFinancialProductKey invitationKey = new WalletFinancialProductKey();

    @Column(name = "visibility", nullable=false)
    private PRODUCT_VISIBILITY visibility;

    @ManyToOne()
    @MapsId("walletId")
    @JoinColumn(name = "wallet_id", nullable=false)
    private Wallet wallet;

    @ManyToOne()
    @MapsId("financialProductId")
    @JoinColumn(name = "financial_product_id", nullable=false)
    private FinancialProduct financialProduct;

    /**
     * Default constructor
     */
    public WalletFinancialProduct() {
    }

    /**
     * Class constructor
     * @param wallet a Wallet object
     * @param financialProduct a FinancialProduct object
     * @param visibility a PRODUCT_VISIBILITY enum
     */
    public WalletFinancialProduct(Wallet wallet, FinancialProduct financialProduct, PRODUCT_VISIBILITY visibility) {
        this.wallet = wallet;
        this.financialProduct = financialProduct;
        this.visibility = visibility;
    }

    /** Get the invitation key of this product
     *
     * @return the invitation key in the form of a WalletFinancialProductKey object
     */
    public WalletFinancialProductKey getInvitationKey() {
        return invitationKey;
    }

    /** Set the invitation key of this product
     *
     * @param invitationKey the invitation key in the form of a WalletFinancialProductKey object
     */
    public void setInvitationKey(WalletFinancialProductKey invitationKey) {
        this.invitationKey = invitationKey;
    }

    /** Get the visibility of this product
     *
     * @return the visibility in the form of a WalletFinancialProduct.PRODUCT_VISIBILITY enum
     */
    public PRODUCT_VISIBILITY getVisibility() {
        return visibility;
    }

    /** Set the visibility of this product
     *
     * @param visibility a WalletFinancialProduct.PRODUCT_VISIBILITY enum
     */
    public void setVisibility(PRODUCT_VISIBILITY visibility) {
        this.visibility = visibility;
    }

    /** Get the wallet
     *
     * @return the wallet in the form of a Wallet object
     */
    public Wallet getWallet() {
        return wallet;
    }

    /** Set the wallet
     *
     * @param wallet a Wallet object
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /** Get the financial product
     *
     * @return the financial product in the form of a FinancialProduct object
     */
    public FinancialProduct getFinancialProduct() {
        return financialProduct;
    }

    /** Set the financial product
     *
     * @param financialProduct a FinancialProduct object
     */
    public void setFinancialProduct(FinancialProduct financialProduct) {
        this.financialProduct = financialProduct;
    }

    /** Represent the product visibility
     */
    public enum PRODUCT_VISIBILITY{
        UNARCHIVED,
        ARCHIVED,
    }

}

@Embeddable
class WalletFinancialProductKey implements Serializable {

    @Column(name = "wallet_id")
    Long walletId;

    @Column(name = "financial_product_id")
    Long financialProductId;

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getFinancialProductId() {
        return financialProductId;
    }

    public void setFinancialProductId(Long financialProductId) {
        this.financialProductId = financialProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WalletFinancialProductKey)) return false;
        WalletFinancialProductKey that = (WalletFinancialProductKey) o;
        return walletId.equals(that.walletId) && financialProductId.equals(that.financialProductId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, financialProductId);
    }
}
