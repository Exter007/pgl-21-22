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


    @RolesAllowed("APPLICATION_CLIENT")
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

    @RolesAllowed("APPLICATION_CLIENT")
    @DeleteMapping(value = "wallet/delete-by-id/{id}")
    public ResponseEntity<?> deleteWalletById(@PathVariable Long id){
        logger.debug("Call : delete Wallet by id");
        walletRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    /**
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProducts held by a certain FinancialInstitution
     */
    @RequestMapping("financialProducts/{bic}")
    public List<FinancialProduct> getFinancialProductsByInstitutionBIC(@PathVariable String bic) {
        return financialProductRepository.findAllByFinancialInstitution_BIC(bic);
    }

    /**
     * @return List of all the FinancialProducts of all FinancialInstitutions
     */
    @RequestMapping("financialProduct/list")
    public List<FinancialProduct> getAllFinancialProducts() {
        return (List<FinancialProduct>) financialProductRepository.findAll();
    }

    /**
     * @return List of all the FinancialInstitutions
     */
    @RequestMapping("financialInstitutions")
    public List<FinancialInstitution> getAllFinancialInstitutions() {
        return (List<FinancialInstitution>) financialInstitutionRepository.findAll();
    }

    /**
     * Request Wallet
     * @param applicationClient
     * @param financialInstitution
     * @throws IllegalArgumentException
     */
    @RequestMapping("requestWallet/{applicationClient}/{financialInstitution}")
    public void requestWallet(@PathVariable ApplicationClient applicationClient, @PathVariable FinancialInstitution financialInstitution) throws IllegalArgumentException {
        RequestWallet rqw = new RequestWallet(Request.REQUEST_STATUS.PENDING, applicationClient, financialInstitution);
        requestWalletRepository.save(rqw);
    }

}
