package com.pgl.services;

import com.pgl.controllers.AccountController;
import com.pgl.models.*;
import com.pgl.repositories.ApplicationClientRepository;
import com.pgl.repositories.CardRepository;
import com.pgl.utils.Code;
import com.pgl.utils.LoginRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service()
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public CardRepository getRepository(){
        return cardRepository;
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
}
