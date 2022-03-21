package com.pgl.models;

import javax.persistence.*;
import java.io.Serializable;

/** Class that represent a wallet financial product
 *
 */
@Entity
@Table(name = "WALLET_FINANCIAL_PRODUCT")
public class WalletFinancialProduct {

    @EmbeddedId
    WalletFinancialProductKey invitationKey;

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

}
