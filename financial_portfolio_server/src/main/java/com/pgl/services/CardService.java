package com.pgl.services;

import com.pgl.models.*;
import com.pgl.models.extension1.Card;
import com.pgl.models.extension1.RequestCard;
import com.pgl.repositories.extension1.CardRepository;
import com.pgl.repositories.extension1.RequestCardRepository;
import com.pgl.utils.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service()
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public CardRepository getRepository(){
        return cardRepository;
    }

    @Autowired
    private RequestCardRepository requestCardRepository;

    public RequestCardRepository getRequestRepository(){
        return requestCardRepository;
    }

    /**
     * Create a new card
     * @param newCard
     * @return
     */
    public Card saveCard(Card newCard){

        FinancialProduct card = getRepository().findCardByCardNumber(newCard.getCardNumber());

        if(card == null){
            newCard.setCreationDate(new Date());
        }else {
            throw new RuntimeException("This card already exists");
        }

        return getRepository().save(newCard);
    }

    /**
     * Update a Card
     * @param newCard
     * @return
     */
    public Card updateCard(Card newCard){

        FinancialProduct cardFound = getRepository().findCardByCardNumber(newCard.getCardNumber());

        if(cardFound != null){
            newCard.setModificationDate(new Date());
        }else {
            throw new RuntimeException("This card doesn't exist");
        }

        return getRepository().save(newCard);
    }

    /**
     * Login with card
     * @param cardNumber
     * @param pin
     * @return
     */
    public LoginRequest loginWithCard(String cardNumber, String pin){
        LoginRequest loginRequest = new LoginRequest();
        FinancialProduct bankAccount = getRepository().findLinkedAccount(cardNumber, pin);

        if(bankAccount != null){
            FinancialProductHolder productHolder = bankAccount.getFinancialProductHolders().get(0);
            String username = productHolder.getApplicationClient().getLogin();
            String password = productHolder.getApplicationClient().getPassword();

            loginRequest.setUsername(username);
            loginRequest.setPassword(password);

        }else {
            throw new RuntimeException("This card doesn't exist or the PIN is wrong");
        }

        return loginRequest;
    }

    /**
     * Create a new request card
     * @param requestCard
     * @return
     */
    public RequestCard saveRequestCard(RequestCard requestCard){
        return getRequestRepository().save(requestCard);
    }

    public RequestCard findRequestCardByClientAndInstitution(ApplicationClient applicationClient, FinancialInstitution financialInstitution){
        RequestCard card = getRequestRepository().findRequestCardByClientAndInstitution(applicationClient, financialInstitution);

        if(card == null){
            throw new RuntimeException("No cards");
        }

        return card;
    }

    public List<RequestCard> findAllByFinancialInstitution(String bic){
        List<RequestCard> cards = getRequestRepository().findAllByFinancialInstitution(bic);

        if(cards == null){
            throw new RuntimeException("No cards");
        }

        return cards;
    }

    public List<RequestCard> findPendingRequestCardsByInstitution(String bic, Request.REQUEST_STATUS pendingRequestStatus){
        List<RequestCard> cards = getRequestRepository().findPendingRequestCardsByInstitution(bic, pendingRequestStatus);

        if(cards == null){
            throw new RuntimeException("No cards");
        }

        return cards;
    }

    public RequestCard findByApplicationClientAndFinancialInstitution(String nationalRegister, String bic){
        RequestCard card = getRequestRepository().findByApplicationClientAndFinancialInstitution(nationalRegister, bic);

        if(card == null){
            throw new RuntimeException("No cards");
        }

        return card;
    }
}
