package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.models.extension5.*;
import com.pgl.repositories.*;
import com.pgl.repositories.extension5.InsuranceContractRepository;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.FinancialProductService;
import com.pgl.services.RequestTransferService;
import com.pgl.services.RequestWalletService;
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
    FinancialProductService productService;

    @Autowired
    FinancialProductHolderRepository productHolderRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    FinancialInstitutionService financialInstitutionService;

    @Autowired
    RequestWalletService requestWalletService;

    @Autowired
    RequestTransferService requestTransferService;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    InsuranceContractRepository insuranceRepository;


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
        List<FinancialProduct> products = productService
                .getRepository().findProductsByInstitution(bic);
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
        return ResponseEntity.ok(productService.saveProduct(account));
    }

    /**
     * Save Saving Account
     * @param account
     * @return
     */
    @PostMapping(value = "saving-account/save")
    public ResponseEntity<?> saveAccount(@RequestBody SavingsAccount account){
        return ResponseEntity.ok(productService.saveProduct(account));
    }

    /**
     * Save Young Account
     * @param account
     * @return
     */
    @PostMapping(value = "young-account/save")
    public ResponseEntity<?> saveYoungAccount(@RequestBody YoungAccount account){
        return ResponseEntity.ok(productService.saveProduct(account));
    }

    /**
     * Save Term Account
     * @param account
     * @return
     */
    @PostMapping(value = "term-account/save")
    public ResponseEntity<?> saveTermAccount(@RequestBody TermAccount account){
        return ResponseEntity.ok(productService.saveProduct(account));
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
    public ResponseEntity<?> getBankAccountsByInstitution(@PathVariable String bic) {
        List<FinancialProduct> accounts = productService
                .getRepository().findProductsByInstitutionAndProductType(bic, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT);
        return ResponseEntity.ok(accounts);
    }

    /** Retrieve Bank Account by IBAN
     * @param iban A BankAccount IBAN
     * @return Bank Account retrieved
     */
    @GetMapping(value = "account/get-by-iban/{iban}")
    public ResponseEntity<?> getBankAccountByIBAN(@PathVariable String iban) {
        FinancialProduct account = productService
                .getRepository().findBankAccountByIBAN(
                        iban, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT
                );
        return ResponseEntity.ok(account);
    }

    /** Retrieve Bank Account by IBAN for a Financial Holder
     * @param bic A FinancialInstitution's BIC
     * @param iban A BankAccount IBAN
     * @return List of all the BankAccount of a certain FinancialInstitution
     */
    @GetMapping(value = "account/get-by-institution-iban/{bic}/{iban}")
    public ResponseEntity<?> getProductByInstitutionAndIBAN(@PathVariable String bic, @PathVariable String iban) {
        FinancialProduct account = productService
                .getRepository().findAccountByInstitutionAndIBAN(
                        bic, iban, FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT
                );
        return ResponseEntity.ok(account);
    }


    // Ressources for Request Wallet

    /**
     * Accept a request wallet
     * @param requestWallet
     * @return the request wallet accepted
     */
    @PostMapping("request-wallet/accept")
    public ResponseEntity<?> acceptRequestWallet(@RequestBody RequestWallet requestWallet){
        return ResponseEntity.ok(requestWalletService.acceptRequestWallet(requestWallet));
    }

    /**
     * Refuse a request wallet
     * @param requestWallet
     * @return the request wallet refused
     */
    @PostMapping("request-wallet/refuse")
    public ResponseEntity<?> refuseRequestWallet(@RequestBody RequestWallet requestWallet){
        return ResponseEntity.ok(requestWalletService.refuseRequestWallet(requestWallet));
    }


    /**
     * List of pending requested wallets
     * @param bic of Financial Institution
     * @return
     */
    @GetMapping("request-wallet/pending/list/{bic}")
    public ResponseEntity<?> getPendingRequestWalletByInstitution(@PathVariable String bic){
        logger.debug("Call : Find pending request wallets by institution ID");
        List<RequestWallet> entities = requestWalletService
                .getRepository()
                .findPendingRequestWalletsByInstitution(bic, Request.REQUEST_STATUS.PENDING);

        return ResponseEntity.ok(entities);
    }

    /**
     * @return List of all the requested wallets
     * @param bic of Financial Institution
     * @return
     */
    @GetMapping("request-wallet/list/{bic}")
    public ResponseEntity<?> getAllRequestWalletByInstitution(@PathVariable String bic){
        logger.debug("Call : Find all request wallet by ID");
        List<RequestWallet> entities = requestWalletService
                .getRepository().findAllByFinancialInstitution(bic);
        return ResponseEntity.ok(entities);
    }

//    /**
//     * @param id
//     * @return
//     */
//    @GetMapping("request-wallet/find-by-id/{id}")
//    public ResponseEntity<?> findRequestWalletById(@PathVariable Long id){
//        logger.debug("Call : Request Wallet by ID");
//        Optional<RequestWallet> result = requestWalletService.getRepository().findById(id);
//        return result.map(ResponseEntity::ok).orElse(null);
//    }

//    /**
//     * @param wallet The wallet that must be created
//     * @return The created wallet
//     */
//    @PostMapping("wallet/save")
//    public ResponseEntity<?> saveWallet(@RequestBody Wallet wallet) {
//        logger.debug("Call : Create Wallet");
//        if(walletRepository.existsByApplicationClientAndFinancialInstitution(wallet.getApplicationClient(), wallet.getFinancialInstitution()) != null) {
//            return ResponseEntity.ok(null);
//        } else{
//            return ResponseEntity.ok(walletRepository.save(wallet));
//        }
//    }

//    @RequestMapping("product/update")
//    public ResponseEntity<?> updateFinancialProduct(@RequestBody FinancialProduct financialProduct){
//        financialProductRepository.deleteById(financialProduct.getId());
//        financialProduct.setModificationDate(new Date());
//        return ResponseEntity.ok(financialProductRepository.save(financialProduct));
//    }


    // RESSOURCES FOR REQUEST TRANSFER

    /**
     * Accept a request transfer
     * @param requestTransfer
     * @return the request transfer accepted
     */
    @PostMapping("request-transfer/accept")
    public ResponseEntity<?> acceptRequestTransfer(@RequestBody RequestTransfer requestTransfer){
        return ResponseEntity.ok(requestTransferService.acceptRequestTransfer(requestTransfer));
    }

    /**
     * Refuse a request transfer
     * @param requestTransfer
     * @return the request transfer refused
     */
    @PostMapping("request-transfer/refuse")
    public ResponseEntity<?> refuseRequestTransfer(@RequestBody RequestTransfer requestTransfer){
        return ResponseEntity.ok(requestTransferService.refuseRequestTransfer(requestTransfer));
    }

    /**
     * List of pending requested transfers
     * @param bic of Financial Institution
     * @return
     */
    @GetMapping("request-transfer/pending/list/{bic}")
    public ResponseEntity<?> getPendingRequestTransferByInstitution(@PathVariable String bic){
        logger.debug("Call : Find pending request transfers by institution ID");
        List<RequestTransfer> entities = requestTransferService
                .getRepository()
                .findPendingRequestTransfersByInstitution(bic, Request.REQUEST_STATUS.PENDING);

        return ResponseEntity.ok(entities);
    }

    /**
     * @return List of all the requested transfers by institution
     */
    @GetMapping("request-transfer/list/{bic}")
    public ResponseEntity<?> getAllRequestTransferByFinancialInstitution(@PathVariable String bic){
        List<RequestTransfer> entities = requestTransferService.getRepository()
                .findAllByFinancialInstitution(bic);
        return ResponseEntity.ok(entities);
    }


    // Ressources for Insurance Contract

    /** Retrieve Insurance Contract by number
     * @param insuranceNumber A Insurance Contract Number
     * @return Insurance Contract retrieved
     */
    @GetMapping(value = "insurance-contract/get-by-number/{insuranceNumber}")
    public ResponseEntity<?> getInsuranceContractByNumber(@PathVariable String insuranceNumber) {
        InsuranceContract insurance = insuranceRepository.findInsuranceContractByNumber(
                        insuranceNumber, FinancialProduct.PRODUCT_TYPE.INSURANCE_CONTRACT
                );
        return ResponseEntity.ok(insurance);
    }

    /** Retrieve Insurance Contracts by Financial institution
     * @param bic A Financial Institution Number
     * @return Insurance Contracts retrieved
     */
    @GetMapping(value = "insurance-contract/get-by-institution/{bic}")
    public ResponseEntity<?> getInsuranceContractsByBIC(@PathVariable String bic) {
        List<InsuranceContract> insurances = insuranceRepository.findInsuranceContractByInstitution(
                bic, FinancialProduct.PRODUCT_TYPE.INSURANCE_CONTRACT
        );
        return ResponseEntity.ok(insurances);
    }


    /**
     * Save Family Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "family-insurance/save")
    public ResponseEntity<?> saveFamilyInsurance(@RequestBody FamilyInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Habitation Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "habitation-insurance/save")
    public ResponseEntity<?> saveHabitationInsurance(@RequestBody HabitationInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Vehicle Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "vehicle-insurance/save")
    public ResponseEntity<?> saveVehicleInsurance(@RequestBody VehicleInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Assistance Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "assistance-insurance/save")
    public ResponseEntity<?> saveAssistanceInsurance(@RequestBody AssistanceInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Hospitalization Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "hospitalization-insurance/save")
    public ResponseEntity<?> saveHospitalizationInsurance(@RequestBody HospitalizationInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Travel Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "travel-insurance/save")
    public ResponseEntity<?> saveTravelInsurance(@RequestBody TravelInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Life Branch 21 Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "life21branch-insurance/save")
    public ResponseEntity<?> saveLifeBranch21Insurance(@RequestBody LifeBranch21Insurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Life Branch 23 Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "life23branch-insurance/save")
    public ResponseEntity<?> saveLifeBranch23Insurance(@RequestBody LifeBranch23Insurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Save Pension Savings Insurance
     * @param insurance
     * @return
     */
    @PostMapping(value = "pension-savings-insurance/save")
    public ResponseEntity<?> savePensionSavingsInsurance(@RequestBody PensionSavingsInsurance insurance){
        return ResponseEntity.ok(productService.saveProduct(insurance));
    }

    /**
     * Delete Bank Account by ID
     * @param id
     * @return
     */
    @DeleteMapping(value = "insurance-contract/delete-by-id/{id}")
    public ResponseEntity<?> deleteInsuranceById(@PathVariable Long id){
        logger.debug("Call : delete Insurance Contract by id");
        insuranceRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

//    /**
//     * @param id RequestTransfer's id
//     * @return RequestTransfer by id
//     */
//    @GetMapping("request-transfer/find-by-id/{id}")
//    public ResponseEntity<?> findRequestTransferById(@PathVariable Long id){
//        if(requestTransferRepository.findById(id).isPresent()){
//            RequestTransfer entity = requestTransferRepository.findById(id).get();
//            return ResponseEntity.ok(entity);
//        } else {
//            return ResponseEntity.ok(null);
//        }
//    }

    /*
    @GetMapping("financial-product/find-by-client/list")
    public ResponseEntity<?> findFinancialProductsOfClient(@RequestBody ApplicationClient applicationClient){
        List<FinancialProduct> entities = (List<FinancialProduct>) financialProductRepository.findAllByApplicationClient(applicationClient);
        return ResponseEntity.ok(entities);
    }
     */
}
