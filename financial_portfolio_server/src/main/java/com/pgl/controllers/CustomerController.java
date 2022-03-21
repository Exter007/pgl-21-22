package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.repositories.FinancialInstitutionRepository;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.repositories.RequestWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    public CustomerController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
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
    @RequestMapping("financialProducts")
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

    @RequestMapping("requestWallet/{applicationClient}/{financialInstitution}")
    public void requestWallet(@PathVariable ApplicationClient applicationClient, @PathVariable FinancialInstitution financialInstitution) throws IllegalArgumentException {
        RequestWallet rqw = new RequestWallet(Request.REQUEST_STATUS.PENDING, applicationClient, financialInstitution);
        requestWalletRepository.save(rqw);
    }

}
