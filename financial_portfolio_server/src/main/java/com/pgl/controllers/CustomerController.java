package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.repositories.FinancialInstitutionRepository;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.repositories.RequestWalletRepository;
import com.pgl.repositories.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value="customer/")
public class CustomerController {
    protected Logger logger;

    @Autowired
    private FinancialProductRepository financialProductRepository;

    @Autowired
    private FinancialInstitutionRepository financialInstitutionRepository;

    @Autowired
    private RequestWalletRepository requestWalletRepository;

    @Autowired
    private WalletRepository walletRepository;

    public CustomerController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    // Ressources for Wallet

    @GetMapping(value = "wallet/find-by-id/{id}")
    public ResponseEntity<?> findWalletById(@PathVariable Long id){
        logger.debug("Call : Get Wallet by id");
        Wallet entity = walletRepository.findById(id).get();
        return ResponseEntity.ok(entity);
    }

//    @RolesAllowed("APPLICATION_CLIENT")
    @GetMapping(value = "wallet/get-by-client/{idClient}")
    public ResponseEntity<?> getWalletByClient(@PathVariable String idClient){
        logger.debug("Call : Get Wallet by Client national number");
        List<Wallet> entities = walletRepository.findWalletsByClient(idClient);
        return ResponseEntity.ok(entities);
    }

//    @RolesAllowed("APPLICATION_CLIENT")
    @DeleteMapping(value = "wallet/delete-by-id/{id}")
    public ResponseEntity<?> deleteWalletById(@PathVariable Long id){
        logger.debug("Call : delete Wallet by id");
        walletRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    // Ressources for Financial Product

    /**
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProducts held by a certain FinancialInstitution
     */
    @GetMapping("product/gett-financial-products-by-institution/{bic}")
    public ResponseEntity<?>  getFinancialProductsByInstitutionBIC(@PathVariable String bic) {
        List<FinancialProduct> entities = financialProductRepository.findProductsByInstitution(bic);
        return ResponseEntity.ok(entities);
    }

    /**
     * @return List of all the FinancialProducts of all FinancialInstitutions
     */
    @GetMapping("product/list")
    public ResponseEntity<?> getAllFinancialProducts() {
        List<FinancialProduct> entities = (List<FinancialProduct>) financialProductRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    /**
     * @return List of all the FinancialInstitutions
     */
    @GetMapping("institution/list")
    public ResponseEntity<?> getAllFinancialInstitutions() {
        List<FinancialInstitution> entities = (List<FinancialInstitution>) financialInstitutionRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    // Ressources from Request Wallet

    /**
     * Request Wallet
     * @param applicationClient
     * @param financialInstitution
     */
    @RequestMapping("requestWallet/{applicationClient}/{financialInstitution}")
    public ResponseEntity<?> requestWallet(@PathVariable ApplicationClient applicationClient, @PathVariable FinancialInstitution financialInstitution) {
        RequestWallet rqw = new RequestWallet(Request.REQUEST_STATUS.PENDING, applicationClient, financialInstitution);
        requestWalletRepository.save(rqw);

        return ResponseEntity.ok(rqw);
    }

}
