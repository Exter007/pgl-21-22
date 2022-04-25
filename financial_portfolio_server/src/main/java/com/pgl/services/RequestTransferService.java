package com.pgl.services;

import com.pgl.models.*;
import com.pgl.repositories.BankAccountRepository;
import com.pgl.repositories.RequestTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service()
public class RequestTransferService {
    @Autowired
    RequestTransferRepository requestTransferRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    NotificationService notificationService;

    /**
     * Get Request Transfer Repository
     * @return repository
     */
    public RequestTransferRepository getRepository(){
        return requestTransferRepository;
    }

    /**
     * Save Request Transfer
     * @param requestTransfer
     * @return Request Transfer saved
     */
    public RequestTransfer saveRequestTransfer(RequestTransfer requestTransfer){
        RequestTransfer rt = getRepository().
                findRequestTransferByClientAndInstitution(
                        requestTransfer.getApplicationClient(),
                        requestTransfer.getBankAccount().getFinancialInstitution()
                );
        if(rt != null) {
            if(rt.getStatus().equals(Request.REQUEST_STATUS.REFUSED)) {
                rt.setStatus(Request.REQUEST_STATUS.PENDING);
                return getRepository().save(rt);
            } else if (rt.getStatus().equals(Request.REQUEST_STATUS.PENDING)){
                return null;
            } else {
                return rt;
            }
        } else{
            return getRepository().save(requestTransfer);
        }
    }

    /**
     * Accept Request Transfer
     * @param requestTransfer
     * @return request transfer accepted
     */
    public RequestTransfer acceptRequestTransfer(RequestTransfer requestTransfer){
        RequestTransfer reqTransfer = getRepository().findById(requestTransfer.getId()).get();
        reqTransfer.setStatus(Request.REQUEST_STATUS.ACCEPTED);
        reqTransfer.setModificationDate(new Date());

        BankAccount bankAccount = reqTransfer.getBankAccount();
        bankAccount.setTransferAccess(FinancialProduct.TRANSFER_ACCESS.AUTHORIZED);

        String message = "Transfer access request accepted by the "
                + reqTransfer.getBankAccount().getFinancialInstitution().getName();

        notificationService.saveNotification(
                reqTransfer.getApplicationClient(),
                reqTransfer.getBankAccount().getFinancialInstitution(),
                message
        );

        bankAccountRepository.save(bankAccount);

        return getRepository().save(reqTransfer);
    }

    /**
     * Refuse Request Transfer
     * @param requestTransfer
     * @return Request Transfer refused
     */
    public RequestTransfer refuseRequestTransfer(RequestTransfer requestTransfer){
        requestTransfer.setStatus(Request.REQUEST_STATUS.REFUSED);
        requestTransfer.setModificationDate(new Date());

        BankAccount bankAccount = requestTransfer.getBankAccount();
        bankAccount.setTransferAccess(FinancialProduct.TRANSFER_ACCESS.DENIED);

        String message = "Transfer access request refused by the "
                + requestTransfer.getBankAccount().getFinancialInstitution().getName();
        notificationService.saveNotification(
                requestTransfer.getApplicationClient(),
                requestTransfer.getBankAccount().getFinancialInstitution(),
                message
        );

        bankAccountRepository.save(bankAccount);

        return getRepository().save(requestTransfer);
    }

}
