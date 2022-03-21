package com.pgl.controllers;

import com.pgl.models.FinancialProduct;
import com.pgl.models.FinancialProductHolder;
import com.pgl.repositories.FinancialProductHolderRepository;
import com.pgl.repositories.FinancialProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "institution")
public class InstitutionController {
    protected Logger logger;

    @Autowired
    FinancialProductRepository financialProductRepository;

    @Autowired
    FinancialProductHolderRepository financialProductHolderRepository;

    public InstitutionController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProducts held by a certain FinancialInstitution
     */
    @RequestMapping("financialProducts/{bic}")
    public List<FinancialProduct> getFinancialProducts(@PathVariable String bic) {
        return financialProductRepository.findAllByFinancialInstitution_BIC(bic);
    }

    /**
     * @param bic A FinancialInstitution's BIC
     * @return List of all the FinancialProductHolders of a certain FinancialInstitution
     */
    @RequestMapping("financialProductsHolders/{bic}")
    public List<FinancialProductHolder> getFinancialProductHolders(@PathVariable String bic) {
        return (List<FinancialProductHolder>) financialProductHolderRepository.findAll();
    }

    /**
     * @param nationalRegister National register of a Customer
     * @param bic A FinancialInstitution's BIC
     * @return A FinancialProductHolder of a certain FinancialInstitution
     */
    @RequestMapping("financialProductHolder/{bic}/{nationalRegister}")
    public FinancialProductHolder getFinancialProductHolder(@PathVariable String nationalRegister, @PathVariable String bic) {
        return financialProductHolderRepository.getFinancialProductHolderByID_BIC(nationalRegister,bic);
    }
}
