package com.pgl.services.extension3;

import com.pgl.models.ApplicationClient;
import com.pgl.models.ScheduledTransaction;
import com.pgl.models.Transaction;
import com.pgl.services.HttpClientService;
import com.pgl.services.UserService;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class ScheduleTransactionService extends HttpClientService<ScheduledTransaction>  {
    private static ScheduledTransaction currentScheduledTransaction;
    private static final String referencePath = "/transaction";

    /**
     * Empty constructor
     * ParameterizedTypeReference for deserializing JSON received from Rest API to List of Application Client
     * ApplicationClient.class for deserializing JSON received from Rest API to Application Client
     */
    public ScheduleTransactionService() {
        super(referencePath, ScheduledTransaction.class,
                new ParameterizedTypeReference<List<Transaction>>() {});
    }

    /**
     * Set the current scheduled transaction
     * @param scheduledTransaction the scheduled transaction
     */
    public void setScheduledTransaction(ScheduledTransaction scheduledTransaction){
        currentScheduledTransaction = scheduledTransaction;
    }

    public ScheduledTransaction getCurrentScheduledTransaction() {
        return currentScheduledTransaction;
    }

}
