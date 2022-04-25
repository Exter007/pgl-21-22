package com.pgl.services;

import com.pgl.models.*;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.repositories.RequestWalletRepository;
import com.pgl.repositories.WalletFinancialProductRepository;
import com.pgl.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class RequestWalletService {

    @Autowired
    RequestWalletRepository requestWalletRepository;

    @Autowired
    FinancialProductRepository financialProductRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletFinancialProductRepository walletFinancialProductRepository;

    @Autowired
    NotificationService notificationService;

    /**
     * Get Request Wallet Repository
     * @return repository
     */
    public RequestWalletRepository getRepository(){
        return requestWalletRepository;
    }

    /**
     * Save Request Wallet
     * @param requestWallet
     * @return Request Wallet saved
     */
    public RequestWallet saveRequestWallet(RequestWallet requestWallet){
        RequestWallet rq = getRepository()
                .findRequestWalletByClientAndInstitution(
                        requestWallet.getApplicationClient(), requestWallet.getFinancialInstitution()
                );
        if(rq != null) {
            if(rq.getStatus().equals(Request.REQUEST_STATUS.REFUSED)) {
                rq.setStatus(Request.REQUEST_STATUS.PENDING);
                return getRepository().save(rq);
            } else if (rq.getStatus().equals(Request.REQUEST_STATUS.PENDING)){
                return null;
            } else {
                return rq;
            }
        } else{
            return requestWalletRepository.save(requestWallet);
        }
    }

    /**
     * Accept Request Wallet
     * @param requestWallet
     * @return Request Wallet accepted
     */
    public RequestWallet acceptRequestWallet(RequestWallet requestWallet){
        requestWallet.setStatus(Request.REQUEST_STATUS.ACCEPTED);
        FinancialInstitution institution = requestWallet.getFinancialInstitution();
        ApplicationClient client = requestWallet.getApplicationClient();

        Wallet wallet = walletRepository.save(new Wallet(institution.getName(), institution, client));

        List<FinancialProduct> financialProducts = new ArrayList<>();
        List<FinancialProduct> productsFound = financialProductRepository
                .findProductsByInstitution(institution.getBIC());

        // Retrieve all financial products for Application Client of Financial Institution
        productsFound.forEach(financialProduct -> {
            financialProduct.getFinancialProductHolders().forEach(holder -> {
                if (holder.getNationalRegister()
                        .equals(client.getNationalRegister())
                ){
                    financialProducts.add(financialProduct);
                }
            });
        });

//        Sauvegarder les produits financiers du portefeuille dans la table de jointure WalletFinancialProduct
        financialProducts.forEach(product -> {
            WalletFinancialProduct walletFinancialProduct = new WalletFinancialProduct(
                    wallet, product, WalletFinancialProduct.PRODUCT_VISIBILITY.UNARCHIVED
            );
            walletFinancialProductRepository.save(walletFinancialProduct);
        });

        String message = "Wallet access request accepted by the "
                + requestWallet.getFinancialInstitution().getName();

        notificationService.saveNotification(
                requestWallet.getApplicationClient(),
                requestWallet.getFinancialInstitution(),
                message
        );

        return getRepository().save(requestWallet);
    }

    /**
     * Refuse Request Wallet
     * @param requestWallet
     * @return Request Wallet refused
     */
    public RequestWallet refuseRequestWallet(RequestWallet requestWallet){
        requestWallet.setStatus(Request.REQUEST_STATUS.REFUSED);

        String message = "Wallet access request refused by the "
                + requestWallet.getFinancialInstitution().getName();

        notificationService.saveNotification(
                requestWallet.getApplicationClient(),
                requestWallet.getFinancialInstitution(),
                message
        );

        return getRepository().save(requestWallet);
    }
}
