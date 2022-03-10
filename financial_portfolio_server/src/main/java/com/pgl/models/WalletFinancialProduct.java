package com.pgl.models;

import javax.persistence.*;
import java.io.Serializable;

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


    public PRODUCT_VISIBILITY getVisibility() {
        return visibility;
    }

    public void setVisibility(PRODUCT_VISIBILITY visibility) {
        this.visibility = visibility;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public FinancialProduct getFinancialProduct() {
        return financialProduct;
    }

    public void setFinancialProduct(FinancialProduct financialProduct) {
        this.financialProduct = financialProduct;
    }


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
