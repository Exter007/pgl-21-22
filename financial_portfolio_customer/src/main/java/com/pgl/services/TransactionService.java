package com.pgl.services;

import com.pgl.models.Transaction;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class TransactionService extends HttpClientService<Transaction>{
    private static final String referencePath = "/transaction";
    UserService userService = new UserService();
    private static Transaction currentTransaction;

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Transaction
     * Transaction.class for deserializing JSON received from Rest API to Transaction
     */
    public TransactionService() {
        super(referencePath, Transaction.class,
                new ParameterizedTypeReference<List<Transaction>>() {});
    }

    /**
     * Get the current transaction selected in the list
     * @return
     */
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    /**
     * Set the current transaction selected in the list
     * @param currentTransaction
     */
    public void setCurrentTransaction(Transaction currentTransaction) {
        TransactionService.currentTransaction = currentTransaction;
    }

    /**
     * Remove the currently selected element
     */
    public void moveCurrentTransaction(){
        setCurrentTransaction(null);
    }

    public List<Transaction> getTransactionsByClient(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-client/"
                + userService.getCurrentUser().getNationalRegister();

        return getListByURL(url);
    }
}
