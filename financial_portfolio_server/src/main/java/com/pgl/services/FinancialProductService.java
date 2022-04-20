package com.pgl.services;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProduct;
import com.pgl.models.Wallet;
import com.pgl.models.WalletFinancialProduct;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.repositories.WalletFinancialProductRepository;
import com.pgl.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class FinancialProductService {

    @Autowired
    FinancialProductRepository financialProductRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletFinancialProductRepository walletFinancialProductRepository;


    public FinancialProductRepository getRepository(){
        return financialProductRepository;
    }

    public FinancialProduct saveProduct(FinancialProduct financialProduct){

        FinancialProduct product = getRepository().save(financialProduct);

        FinancialInstitution institution = financialProduct.getFinancialInstitution();

        // Verifier si un portefeuille existe deja pour chaque titulaire du produit pour l'institution
        financialProduct.getFinancialProductHolders().forEach(holder -> {
            Wallet wallet = walletRepository
            .findByClientAndInstitution(holder.getNationalRegister(), institution.getBIC());
            // Si un portefeuille existe deja, rajouter le produit financier au portefeuille du client pour l'institution
            if (wallet != null){
                WalletFinancialProduct walletFinancialProduct = new WalletFinancialProduct(
                        wallet, product, WalletFinancialProduct.PRODUCT_VISIBILITY.UNARCHIVED
                );
                walletFinancialProductRepository.save(walletFinancialProduct);
            }
        });

        return financialProduct;
    }
}
