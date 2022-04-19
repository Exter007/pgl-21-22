package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.repositories.*;
import com.pgl.services.FinancialInstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "institution/")
public class InstitutionController {
    protected Logger logger;

    @Autowired
    FinancialProductRepository financialProductRepository;

    @Autowired
    FinancialProductHolderRepository productHolderRepository;

    @Autowired
    FinancialInstitutionService financialInstitutionService;

    @Autowired
    RequestWalletRepository requestWalletRepository;

    @Autowired
    RequestTransferRepository requestTransferRepository;


    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    WalletRepository walletRepository;

    public InstitutionController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }


    // Ressources for Financial Product Holder

    /**
     * Save Financial Product Holder
     * @param holder
     * @return
     */
    @Transactional()
    @PostMapping(value = "holder/save")
    public ResponseEntity<?> saveHolder(@RequestBody FinancialProductHolder holder){
        return ResponseEntity.ok(productHolderRepository.save(holder));
    }

    /**
     * Find Financial Product Holder by ID
     * @param id
     * @return
     */
//    @RolesAllowed("INSTITUTION")
    @GetMapping(value = "holder/find-by-id/{id}")
    public ResponseEntity<?> findHolderById(@PathVariable Long id){
        logger.debug("Call : Get holder by id");
        Optional<FinancialProductHolder> result = productHolderRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(null);
    }

    /**
     * Delete Financial Product Holder by ID
     * @param id
     * @return
     */
//    @RolesAllowed("INSTITUTION")
    @DeleteMapping(value = "holder/delete-by-id/{id}")
    public ResponseEntity<?> deleteHolderById(@PathVariable Long id){
        logger.debug("Call : delete holder by id");
        productHolderRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    /** Retrieve Financial products holder by Financial institution BIC
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProductHolders of a certain FinancialInstitution
     */
    @GetMapping(value = "holder/get-by-institution/{bic}")
    public ResponseEntity<?> getHoldersByInstitution(@PathVariable String bic) {
        return ResponseEntity.ok(productHolderRepository.findHoldersByInstitution(bic));
    }

    /** Retrieve Financial product holder by Financial institution BIC and National register of a Client
     * @param bic A FinancialInstitution's BIC
     * @param nationalRegister National register of a Client
     * @return A FinancialProductHolder of a certain FinancialInstitution
     */
    @GetMapping("holder/get-by-institution-and-client/{bic}/{nationalRegister}")
    public ResponseEntity<?> getHolderByInstitutionAndClient(@PathVariable String bic, @PathVariable String nationalRegister) {
        return ResponseEntity.ok(productHolderRepository
                .findHolderByInstitutionAndClient(bic, nationalRegister));
    }


    // Ressources for Financial Institution

    /**
     * Update financial institution
     * @param institution
     * @return
     */
    @PostMapping(value = "update")
    public ResponseEntity<?> updateInstitution(@RequestBody FinancialInstitution institution){
        logger.debug("Call : Update financial institution");

        return ResponseEntity.ok(financialInstitutionService
                .updateInstitution(institution));
    }

    /**
     * Find Financial Institution by BIC
     * @param bic
     * @return
     */
    @GetMapping(value = "find-by-id/{bic}")
    public ResponseEntity<?> findInstitutionById(@PathVariable String bic){
        logger.debug("Call : Get Institution by id");
        Optional<FinancialInstitution> result = financialInstitutionService
                .getRepository().findById(bic);
        return result.map(ResponseEntity::ok).orElse(null);
    }

    /**
     * Check if the password provided by the financial institution is correct
     * @param institution
     * @return
     */
    @PostMapping(value = "check-password")
    public ResponseEntity<?> checkPassword(@RequestBody FinancialInstitution institution){

        return ResponseEntity.ok(financialInstitutionService.checkPassword(institution));
    }

    /**
     * Update financial institution password
     * @param institution
     * @return
     */
    @PostMapping(value = "password/update")
    public ResponseEntity<?> updatePassword(@RequestBody FinancialInstitution institution){
        logger.debug("Call : Update password");

        return ResponseEntity.ok(financialInstitutionService
                .updatePassword(institution));
    }

    /**
     * Update financial institution preferred language
     * @param institution
     * @return
     */
    @PostMapping(value = "language/update")
    public ResponseEntity<?> updateLanguage(@RequestBody FinancialInstitution institution){
        logger.debug("Call : Update language");

        return ResponseEntity.ok(financialInstitutionService
                .updateLanguage(institution));
    }


    // Ressources for Financial Product

    /** Find Financial Products by Institution BIC
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProducts held by a certain FinancialInstitution
     */
    @RequestMapping("product/get-by-institution/{bic}")
    public ResponseEntity<?> getProductsByInstitution(@PathVariable String bic) {
        List<FinancialProduct> products = financialProductRepository.findProductsByInstitution(bic);
        return ResponseEntity.ok(products);
    }

    // Ressources for Bank Account

    /**
     * Save Current Account
     * @param account
     * @return
     */
    @PostMapping(value = "current-account/save")
    public ResponseEntity<?> saveCurrentAccount(@RequestBody CurrentAccount account){
        return ResponseEntity.ok(bankAccountRepository.save(account));
    }

    /**
     * Save Saving Account
     * @param account
     * @return
     */
    @PostMapping(value = "saving-account/save")
    public ResponseEntity<?> saveAccount(@RequestBody SavingsAccount account){
        return ResponseEntity.ok(bankAccountRepository.save(account));
    }

    /**
     * Save Young Account
     * @param account
     * @return
     */
    @PostMapping(value = "young-account/save")
    public ResponseEntity<?> saveYoungAccount(@RequestBody YoungAccount account){
        return ResponseEntity.ok(bankAccountRepository.save(account));
    }

    /**
     * Save Term Account
     * @param account
     * @return
     */
    @PostMapping(value = "term-account/save")
    public ResponseEntity<?> saveTermAccount(@RequestBody TermAccount account){
        return ResponseEntity.ok(bankAccountRepository.save(account));
    }

    /**
     * Find Bank Account by ID
     * @param id
     * @return
     */
    @GetMapping(value = "account/find-by-id/{id}")
    public ResponseEntity<?> findAccountById(@PathVariable Long id){
        logger.debug("Call : Get Bank Account by id");
        Optional<BankAccount> result = bankAccountRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(null);
    }

    /**
     * Delete Bank Account by ID
     * @param id
     * @return
     */
    @DeleteMapping(value = "account/delete-by-id/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable Long id){
        logger.debug("Call : delete Bank Account by id");
        bankAccountRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    /** Retrieve Bank Account by Financial institution BIC
     * @param bic A FinancialInstitution's BIC
     * @return List of all the BankAccount of a certain FinancialInstitution
     */
    @GetMapping(value = "account/get-by-institution/{bic}")
    public ResponseEntity<?> getAccountByInstitution(@PathVariable String bic) {
        List<FinancialProduct> accounts = financialProductRepository.findProductsByInstitutionAndProductType(bic, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT);
        return ResponseEntity.ok(accounts);
    }

    /** Retrieve Bank Account by IBAN for a Financial Holder
     * @param bic A FinancialInstitution's BIC
     * @param iban A BankAccount IBAN
     * @return List of all the BankAccount of a certain FinancialInstitution
     */
    @GetMapping(value = "account/get-by-institution-iban/{bic}/{iban}")
    public ResponseEntity<?> getProductByInstitutionAndIBAN(@PathVariable String bic, @PathVariable String iban) {
        FinancialProduct account = financialProductRepository.findProductByInstitutionAndIBAN(bic, iban, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT);
        return ResponseEntity.ok(account);
    }

    @RequestMapping("request-wallet/update")
    public ResponseEntity<?> updateRequestWallet(@RequestBody RequestWallet requestWallet){
        requestWalletRepository.deleteById(requestWallet.getId());
        requestWallet.setModificationDate(new Date());
        return ResponseEntity.ok(requestWalletRepository.save(requestWallet));
    }


    // Ressources for Request Wallet

    /**
     * @return List of all the requested wallets
     */
    @GetMapping("request-wallet/list/{bic}")
    public ResponseEntity<?> getAllFinancialInstitutions(@PathVariable String bic){
        List<RequestWallet> entities = (List<RequestWallet>) requestWalletRepository.findAllByFinancialInstitution(bic);
        return ResponseEntity.ok(entities);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("request-wallet/find-by-id/{id}")
    public ResponseEntity<?> findRequestWalletById(@PathVariable Long id){
        if(requestWalletRepository.findById(id).isPresent()){
            RequestWallet entity = requestWalletRepository.findById(id).get();
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    /**
     * @param wallet The wallet that must be created
     * @return The created wallet
     */
    @PostMapping("wallet/save")
    public ResponseEntity<?> saveWallet(@RequestBody Wallet wallet) {
        logger.debug("Call : Create Wallet");
        if(walletRepository.existsByApplicationClientAndFinancialInstitution(wallet.getApplicationClient(), wallet.getFinancialInstitution()) != null) {
            return ResponseEntity.ok(null);
        } else{
            return ResponseEntity.ok(walletRepository.save(wallet));
        }
    }

    @RequestMapping("product/update")
    public ResponseEntity<?> updateFinancialProduct(@RequestBody FinancialProduct financialProduct){
        financialProductRepository.deleteById(financialProduct.getId());
        financialProduct.setModificationDate(new Date());
        return ResponseEntity.ok(financialProductRepository.save(financialProduct));
    }

    // TRANSFER

    @RequestMapping("request-transfer/update")
    public ResponseEntity<?> updateRequestTransfer(@RequestBody RequestTransfer requestTransfer){
        requestTransferRepository.deleteById(requestTransfer.getId());
        requestTransfer.setModificationDate(new Date());
        return ResponseEntity.ok(requestTransferRepository.save(requestTransfer));
    }

    /**
     * @return List of all the requested transfers by institution
     */
    @GetMapping("request-transfer/list/{bic}")
    public ResponseEntity<?> getAllTransferByFinancialInstitutions(@PathVariable String bic){
        List<RequestTransfer> entities = (List<RequestTransfer>) requestTransferRepository.findAllByFinancialInstitution(bic);
        return ResponseEntity.ok(entities);
    }

    /**
     * @param id RequestTransfer's id
     * @return RequestTransfer by id
     */
    @GetMapping("request-transfer/find-by-id/{id}")
    public ResponseEntity<?> findRequestTransferById(@PathVariable Long id){
        if(requestTransferRepository.findById(id).isPresent()){
            RequestTransfer entity = requestTransferRepository.findById(id).get();
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    /*
    @GetMapping("financial-product/find-by-client/list")
    public ResponseEntity<?> findFinancialProductsOfClient(@RequestBody ApplicationClient applicationClient){
        List<FinancialProduct> entities = (List<FinancialProduct>) financialProductRepository.findAllByApplicationClient(applicationClient);
        return ResponseEntity.ok(entities);
    }
     */
}
