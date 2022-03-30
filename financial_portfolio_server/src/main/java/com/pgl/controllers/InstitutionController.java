package com.pgl.controllers;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProduct;
import com.pgl.models.FinancialProductHolder;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.FinancialProductHolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "institution/")
public class InstitutionController {
    protected Logger logger;

    @Autowired
    FinancialProductRepository financialProductRepository;

    @Autowired
    FinancialProductHolderService financialProductHolderService;

    @Autowired
    FinancialInstitutionService financialInstitutionService;

    public InstitutionController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }


    // Ressources for Financial Product Holder

    /**
     * Save Financial Product Holder
     * @param holder
     * @return
     */
    @PostMapping(value = "holder/save")
    public ResponseEntity<?> saveHolder(@RequestBody FinancialProductHolder holder){
        boolean check = financialInstitutionService.checkPassword(holder.getFinancialInstitution());
        if (!check){
            throw new RuntimeException("Password mismatch");
        }
        holder = financialProductHolderService.saveHolder(holder);
        return ResponseEntity.ok(holder);
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
        FinancialProductHolder entity = financialProductHolderService.getRepository().findById(id).get();
        return ResponseEntity.ok(entity);
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
        financialProductHolderService.getRepository().deleteById(id);
        return ResponseEntity.ok(true);
    }

    /** Retrieve Financial products holder by Financial institution BIC
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProductHolders of a certain FinancialInstitution
     */
    @GetMapping(value = "holder/get-by-institution/{bic}")
    public ResponseEntity<?> getHoldersByInstitution(@PathVariable String bic) {
        List<FinancialProductHolder> holders = financialProductHolderService
                .getRepository().findHoldersByInstitution(bic);
        return ResponseEntity.ok(holders);
    }

    /** Retrieve Financial product holder by Financial institution BIC and National register of a Client
     * @param bic A FinancialInstitution's BIC
     * @param nationalRegister National register of a Client
     * @return A FinancialProductHolder of a certain FinancialInstitution
     */
    @GetMapping("holder/get-by-institution-and-client/{bic}/{nationalRegister}")
    public ResponseEntity<?> getHolderByInstitutionAndClient(@PathVariable String bic, @PathVariable String nationalRegister) {
        FinancialProductHolder holder = financialProductHolderService.getRepository()
                .findHolderByInstitutionAndClient(bic, nationalRegister);
        return ResponseEntity.ok(holder);
    }

    // Ressources for Financial Institution
    /**
     * Check if the password provided by the financial institution is correct
     * @param institution
     * @return
     */
    @PostMapping(value = "institution/check-password")
    public ResponseEntity<?> checkPassword(@RequestBody FinancialInstitution institution){

        return ResponseEntity.ok(institution);
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
}
