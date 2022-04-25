package com.pgl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgl.models.*;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BankAccountService extends HttpClientService<LinkedHashMap> {
    WalletService walletService = new WalletService();

    private static BankAccount currentBankAccount;

    /**
     * To define the action to perform on an object
     * false if creation of a new object
     * true if modifying an existing object
     */
    private static boolean edit = false;

    private static final String referencePath = "/bank-account";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Bank Account
     * BankAccount.class for deserializing JSON received from Rest API to Bank Account
     */
    public BankAccountService() {
        super(referencePath, LinkedHashMap.class,
                new ParameterizedTypeReference<List<LinkedHashMap>>() {});
    }

    /**
     * Get the current Bank Account selected in the list
     * @return
     */
    public BankAccount getCurrentBankAccount() {
        return currentBankAccount;
    }

    /**
     * Set the current Bank Account selected in the list
     * @param currentBankAccount
     */
    public void setCurrentBankAccount(BankAccount currentBankAccount) {
        BankAccountService.currentBankAccount = currentBankAccount;
    }

    /**
     * Determine interface access mode
     * @return Mode of access to the interface represented by a boolean
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * Define the mode of access to the interface
     * @param edit
     */
    public void setEdit(boolean edit) {
        BankAccountService.edit = edit;
    }

    /**
     * Remove the currently selected element
     */
    public void moveCurrentBankAccount(){
        setCurrentBankAccount(null);
    }


    /**
     * Retrieve Bank Account from a IBAN
     * @param iban for Bank account
     * @return
     */
    public BankAccount getBankAccountByIBAN(String iban){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/find-by-iban/"
                + iban;

        LinkedHashMap result = getByURL(url);
        BankAccount bankAccount = null;
        ObjectMapper objectMapper = new ObjectMapper();
        if (result.get("classe").equals(CurrentAccount.class.getSimpleName()) ){
            bankAccount = objectMapper.convertValue(result, CurrentAccount.class);
        }else if (result.get("classe").equals(SavingsAccount.class.getSimpleName()) ){
            bankAccount = objectMapper.convertValue(result, SavingsAccount.class);
        }else if (result.get("classe").equals(YoungAccount.class.getSimpleName()) ){
            bankAccount = objectMapper.convertValue(result, YoungAccount.class);
        }else if (result.get("classe").equals(TermAccount.class.getSimpleName()) ){
            bankAccount = objectMapper.convertValue(result, TermAccount.class);
        }

        return bankAccount;
    }

    /**
     * Retrieve Bank Account from a Wallet
     * @return
     */
    public List<BankAccount> getBankAccountsByWallet(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-wallet/"
                + walletService.getCurrentWallet().getId();

        List<LinkedHashMap> results = getListByURL(url);
        List<BankAccount> accounts = new ArrayList<>();
        results.forEach( result ->{
            ObjectMapper objectMapper = new ObjectMapper();
            if (result.get("classe").equals(CurrentAccount.class.getSimpleName()) ){
                accounts.add(objectMapper.convertValue(result, CurrentAccount.class));
            }else if (result.get("classe").equals(SavingsAccount.class.getSimpleName()) ){
                accounts.add(objectMapper.convertValue(result, SavingsAccount.class));
            }else if (result.get("classe").equals(YoungAccount.class.getSimpleName()) ){
                accounts.add(objectMapper.convertValue(result, YoungAccount.class));
            }else if (result.get("classe").equals(TermAccount.class.getSimpleName()) ){
                accounts.add(objectMapper.convertValue(result, TermAccount.class));
            }
        });

        return accounts;
    }

}
