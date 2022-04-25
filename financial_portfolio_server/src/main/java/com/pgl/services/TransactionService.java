package com.pgl.services;

import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProduct;
import com.pgl.models.Request;
import com.pgl.models.Transaction;
import com.pgl.repositories.FinancialProductRepository;
import com.pgl.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FinancialProductRepository productRepository;

    /**
     * Get Transaction Repository
     * @return repository
     */
    TransactionRepository getRepository(){
        return transactionRepository;
    }

    /**
     * Save Transaction
     * @param transaction
     * @return Transaction saved
     */
    public Transaction saveTransaction(Transaction transaction){

        BankAccount senderAccount = (BankAccount) productRepository
                .findById(transaction.getBankAccount().getId()).get();

        BankAccount recipientAccount = (BankAccount) productRepository.findBankAccountByIBAN(
                transaction.getDestinationIBAN(), FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT
        );

        if (recipientAccount == null){
            throw new RuntimeException("Recipient not found");
        }else if(transaction.getType().equals(Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER)){
            // Check Sender account with amount debited
            float amount = senderAccount.getAmount() - transaction.getAmount();
            if(amount < 0){
                throw new RuntimeException("Insufficient amount");
            }
            saveTransactionAccount(transaction, senderAccount, recipientAccount, amount);

            transaction.setBankAccount(senderAccount);
            transaction.setStatus(Request.REQUEST_STATUS.ACCEPTED);
            transaction.setFormulation(buildFormulation(transaction, senderAccount, recipientAccount,""));

        }else if (transaction.getType().equals(Transaction.TRANSACTION_TYPE.INCOMING_TRANSFER)){
            // Check recipient account with amount debited
            float amount = recipientAccount.getAmount() - transaction.getAmount();
            if(amount < 0){
                transaction.setStatus(Request.REQUEST_STATUS.REFUSED);
                String comment = "Insufficient amount in recipient account";
                transaction.setFormulation(buildFormulation
                        (transaction, senderAccount, recipientAccount, comment)
                );
                getRepository().save(transaction);
                throw new RuntimeException("Insufficient amount");
            }
            saveTransactionAccount(transaction, recipientAccount, senderAccount, amount);

            transaction.setBankAccount(senderAccount);
            transaction.setStatus(Request.REQUEST_STATUS.ACCEPTED);
            transaction.setFormulation(buildFormulation(transaction, senderAccount, recipientAccount,""));
        }

        return getRepository().save(transaction);
    }

    /**
     * Build message formulation
     * @param transaction
     * @param senderAccount
     * @param recipientAccount
     * @param comment
     * @return message formulated
     */
    private String buildFormulation(Transaction transaction, BankAccount senderAccount, BankAccount recipientAccount, String comment){
        String formulation = transaction.getDate() + " "
                + transaction.getType().name() + " "
                + transaction.getStatus().name()
                + "\nFrom " + (senderAccount.getIban() != null ? senderAccount.getIban() : "") + " "
                + senderAccount.getNature().name()
                + " To " + transaction.getDestinationName() + " "
                + transaction.getDestinationIBAN() + " "
                + comment ;

        return formulation;
    }

    /**
     * Save Account Transaction
     * @param transaction
     * @param accountDebited
     * @param accountCredited
     * @param amountDebited
     */
    private void saveTransactionAccount(Transaction transaction, BankAccount accountDebited, BankAccount accountCredited, float amountDebited){
        accountDebited.setAmount(amountDebited);
        accountDebited.setModificationDate(new Date());

        // Save account to be credited with amount added
        float amount = accountCredited.getAmount() + transaction.getAmount();
        accountCredited.setAmount(amount);
        accountCredited.setModificationDate(new Date());
        productRepository.save(accountCredited);
        productRepository.save(accountDebited);
    }

    /**
     * Find Transaction by Applicaton Client
     * @param registerNumber
     * @return list of transactions retrieved
     */
    public List<Transaction> findTransactionByClient(String registerNumber){
        List<Transaction> transactions = new ArrayList<>();
        Iterable<Transaction> allTransactions = getRepository().findAll();
        allTransactions.forEach(transaction -> {
            transaction.getBankAccount().getFinancialProductHolders()
                    .forEach(holder -> {
                if(holder.getNationalRegister().equals(registerNumber)){
                    transactions.add(transaction);
                }
            });
        });

        return transactions;
    }

}
