package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.repositories.*;
import com.pgl.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private RequestWalletService requestWalletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private RequestTransferService requestTransferService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private NotificationService notificationService;


    public CustomerController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }


    // Ressources for Application Client

    /**
     * Update Application Client
     * @param client
     * @return
     */
    @PostMapping(value = "update")
    public ResponseEntity<?> updateClient(@RequestBody ApplicationClient client){
        logger.debug("Call : Update Application client");

        return ResponseEntity.ok(applicationClientService
                .updateClient(client));
    }

    /**
     * Check if the password provided by the application client is correct
     * @param applicationClient
     * @return
     */
    @PostMapping(value = "check-password")
    public ResponseEntity<?> checkPassword(@RequestBody ApplicationClient applicationClient){

        return ResponseEntity.ok(applicationClientService.checkPassword(applicationClient));
    }

    /**
     * Update Application Client password
     * @param client
     * @return
     */
    @PostMapping(value = "password/update")
    public ResponseEntity<?> updatePassword(@RequestBody ApplicationClient client){
        logger.debug("Call : Update password");

        return ResponseEntity.ok(applicationClientService
                .updatePassword(client));
    }

    /**
     * Update Application Client preferred language
     * @param client
     * @return
     */
    @PostMapping(value = "language/update")
    public ResponseEntity<?> updateLanguage(@RequestBody ApplicationClient client){
        logger.debug("Call : Update language");

        return ResponseEntity.ok(applicationClientService
                .updateLanguage(client));
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

    /** Find Financial Products by Wallet ID
     * @param idWallet A Wallet ID
     * @return List of all the FinancialProducts held by a certain Wallet
     */
    @RequestMapping("product/get-by-wallet/{idWallet}")
    public ResponseEntity<?> getProductsByWallet(@PathVariable Long idWallet) {
        List<FinancialProduct> products = financialProductRepository
                .findProductsByWallet(idWallet);
        return ResponseEntity.ok(products);
    }

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

    // RESSOURCES FOR BANK ACCOUNT

    /** Find Bank Accounts by Wallet ID
     * @param idWallet A Wallet ID
     * @return List of all the BankAccount held by a certain Wallet
     */
    @RequestMapping("bank-account/get-by-wallet/{idWallet}")
    public ResponseEntity<?> getBankAccountsByWallet(@PathVariable Long idWallet) {
        List<FinancialProduct> products = financialProductRepository
                .findBankAccountsByWallet(idWallet, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT);
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "bank-account/find-by-iban/{iban}")
    public ResponseEntity<?> findBankAccountByIBAN(@PathVariable String iban){
        logger.debug("Call : Find Bank Account by IBAN");
        return ResponseEntity.ok(financialProductRepository
                .findBankAccountByIBAN(iban, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT));
    }


    // Ressources from Financial Institution

    /**
     * @return List of all the FinancialInstitutions
     */
    @GetMapping("institution/list")
    public ResponseEntity<?> getAllFinancialInstitutions() {
        List<FinancialInstitution> entities = (List<FinancialInstitution>) financialInstitutionRepository.findAll();
        return ResponseEntity.ok(entities);
    }

//    /**
//     * @param name A FinancialInstitution's name
//     * @return The financial institution with the given name
//     */
//    @GetMapping("institution/{name}")
//    public ResponseEntity<?> getFinancialInstitutionByName(@PathVariable String name) {
//        logger.debug("Call : Get FinancialInstitution by name");
//        FinancialInstitution entity = financialInstitutionRepository.findByName(name);
//        return ResponseEntity.ok(entity);
//    }


    // Ressources from Request Wallet

    /**
     * @param requestWallet RequestWallet to be created
     * @return The created RequestWallet or null if already exists
     */
    @PostMapping("request-wallet/save")
    public ResponseEntity<?> requestWallet(@RequestBody RequestWallet requestWallet) {
        logger.debug("Call : Create RequestWallet");
        return ResponseEntity.ok(requestWalletService.saveRequestWallet(requestWallet));
    }

    /**
     * @param applicationClientID ApplicationClient's ID to be used to find the RequestWallet
     * @param financialInstitutionBIC FinancialInstitution's BIC to be used to find the RequestWallet
     * @return ResponseEntity with the value trueif the RequestWallet has been deleted
     */
    @DeleteMapping("request-wallet/delete/{applicationClientID}/{financialInstitutionBIC}")
    public ResponseEntity<?> deleteRequestWallet(@PathVariable String applicationClientID, @PathVariable String financialInstitutionBIC) {
        logger.debug("Call : delete RequestWallet by id");
        RequestWallet rq = requestWalletService.getRepository()
                .findByApplicationClientAndFinancialInstitution(
                        applicationClientID, financialInstitutionBIC
                );
        requestWalletService.getRepository().deleteById(rq.getId());
        return ResponseEntity.ok(true);
    }

    // Ressources from RequestTransfer

    /**
     * @param requestTransfer RequestTransfer to be created
     * @return The created RequestTransfer or null if already exists
     */
    @PostMapping("request-transfer/save")
    public ResponseEntity<?> requestTransfer(@RequestBody RequestTransfer requestTransfer) {
        logger.debug("Call : Create RequestTransfer");
        return ResponseEntity.ok(requestTransferService.saveRequestTransfer(requestTransfer));
    }

    /** Get the financial products of a wallet by using id
     *
     * @param id a wallet id
     * @return a ReponseEntity
     */
    @GetMapping(value = "wallet/{id}/product/list")
    public ResponseEntity<?> getAllFinancialProductsOfAWallet(@PathVariable Long id){
        logger.debug("Call : Get Financial Products by id");
        List<FinancialProduct> entities = financialProductRepository.findProductsByWallet(id);
        return ResponseEntity.ok(entities);
    }


    // Ressources from Transaction

    /**
     * Save Transaction
     * @param transaction
     * @return
     */
    @PostMapping(value = "transaction/save")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction){
        logger.debug("Call : Save Transaction");

        return ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }

    /**
     * Retrieve Transaction by national register number for Application client
     * @param registerNumber A Application Client national register number
     * @return The transaction list of Application Client
     */
    @GetMapping("transaction/get-by-client/{registerNumber}")
    public ResponseEntity<?> getTransactionByClient(@PathVariable String registerNumber) {
        logger.debug("Call : Get Transaction By Client");
        List<Transaction> entities = transactionService.findTransactionByClient(registerNumber);

        return ResponseEntity.ok(entities);
    }


    // RESSOURCES FOR NOTIFICATION

    /**
     * Save notification
     * @param notification
     * @return
     */
    @PostMapping(value = "notification/save")
    public ResponseEntity<?> saveNotification(@RequestBody Notification notification){
        logger.debug("Call : Save Notification");

        return ResponseEntity.ok(notificationService.getRepository().save(notification));
    }

    /**
     * Find All Notification by Application client
     * @param registerNumber for Application Client
     * @return
     */
    @GetMapping(value = "notification/get-by-client/{registerNumber}")
    public ResponseEntity<?> findNotificationsByClient(@PathVariable String registerNumber){
        logger.debug("Call : Find All Notifications by Application Client");

        return ResponseEntity.ok(notificationService.getRepository()
                .findNotificationsByClient(registerNumber));
    }

}
