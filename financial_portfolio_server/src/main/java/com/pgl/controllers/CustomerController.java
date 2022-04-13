package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.repositories.*;
import com.pgl.services.ApplicationClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="customer/")
public class CustomerController {
    protected Logger logger;

    @Autowired
    private ApplicationClientService applicationClientService;

    @Autowired
    private FinancialProductRepository financialProductRepository;

    @Autowired
    private FinancialInstitutionRepository financialInstitutionRepository;

    @Autowired
    private RequestWalletRepository requestWalletRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private RequestTransferRepository requestTransferRepository;

    public CustomerController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }


    // Ressources for Application Client
    /**
     * Check if the password provided by the application client is correct
     * @param applicationClient
     * @return
     */
    @PostMapping(value = "check-password")
    public ResponseEntity<?> checkPassword(@RequestBody ApplicationClient applicationClient){

        return ResponseEntity.ok(applicationClientService.checkPassword(applicationClient));
    }


    // Ressources for Wallet

    @GetMapping(value = "wallet/find-by-id/{id}")
    public ResponseEntity<?> findWalletById(@PathVariable Long id){
        logger.debug("Call : Get Wallet by id");
        Optional<Wallet> result = walletRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(null);
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
     * @param name A FinancialInstitution's name
     * @return The financial institution with the given name
     */
    @GetMapping("financialInstitution/{name}")
    public ResponseEntity<?> getFinancialInstitutionByName(@PathVariable String name) {
        logger.debug("Call : Get FinancialInstitution by name");
        FinancialInstitution entity = financialInstitutionRepository.findByName(name);
        return ResponseEntity.ok(entity);
    }

    /**
     * Request Wallet
     * @param requestWallet RequestWallet to be created
     */
    @PostMapping("request-wallet/save")
    public ResponseEntity<?> requestWallet(@RequestBody RequestWallet requestWallet) {
        logger.debug("Call : Create RequestWallet");
        if(requestWalletRepository.existsByApplicationClientAndFinancialInstitution(requestWallet.getApplicationClient(), requestWallet.getFinancialInstitution()) != null) {
            return ResponseEntity.ok(null);
        } else{
            return ResponseEntity.ok(requestWalletRepository.save(requestWallet));
        }
    }

    @DeleteMapping("request-wallet/delete/{applicationClientID}/{financialInstitutionBIC}")
    public ResponseEntity<?> deleteRequestWallet(@PathVariable String applicationClientID, @PathVariable String financialInstitutionBIC) {
        logger.debug("Call : delete RequestWallet by id");
        RequestWallet rq = requestWalletRepository.findByApplicationClientAndFinancialInstitution(applicationClientID, financialInstitutionBIC);
        requestWalletRepository.deleteById(rq.getId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("request-transfer/save")
    public ResponseEntity<?> requestTransfer(@RequestBody RequestTransfer requestTransfer) {
        logger.debug("Call : Create RequestTransfer");
        if(requestTransferRepository.existsByApplicationClientAndFinancialInstitution(requestTransfer.getApplicationClient(), requestTransfer.getBankAccount().getFinancialInstitution()) != null) {
            return ResponseEntity.ok(null);
        } else{
            return ResponseEntity.ok(requestTransferRepository.save(requestTransfer));
        }
    }

}
