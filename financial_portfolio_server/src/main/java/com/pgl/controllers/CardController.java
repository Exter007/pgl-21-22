package com.pgl.controllers;

import com.pgl.models.*;
import com.pgl.models.extension1.Card;
import com.pgl.models.extension1.RequestCard;
import com.pgl.repositories.extension1.CardRepository;
import com.pgl.repositories.extension1.RequestCardRepository;
import com.pgl.security.UserDetailsImpl;
import com.pgl.services.CardService;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.UserService;
import com.pgl.utils.JwtResponse;
import com.pgl.utils.JwtUtils;
import com.pgl.utils.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="card/")
public class CardController {
    protected Logger logger;

    private final AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    @Autowired
    FinancialInstitutionService financialInstitutionService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RequestCardRepository requestCardRepository;

    public CardController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Create Card
     * @param card
     * @return
     */
    @PostMapping(value = "create")
    public ResponseEntity<?> createCard(@RequestBody Card card){
        logger.debug("Call : Create Card");
        return ResponseEntity.ok(cardService.saveCard(card));
    }

    /**
     * Update Card
     * @param newCard
     * @return
     */
    @PostMapping(value = "update")
    public ResponseEntity<?> updateCard(@RequestBody Card newCard){
        logger.debug("Call : Update Card");
        return ResponseEntity.ok(cardService.updateCard(newCard));
    }

    /**
     * User login
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginRequest login = cardService.loginWithCard(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), "a1"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByLogin(login.getUsername());
        if(!user.getActive()){
            throw new RuntimeException("Account not activated");
        }
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles, user));
    }

    /** Find Cards by client ID
     * @param idClient A Client ID
     * @return List of all the Cards
     */
    @RequestMapping("findcards-by-client/{idClient}")
    public ResponseEntity<?> findCardsByClient(@PathVariable String idClient) {
        List<Card> cards = cardRepository.findCardsByClient(idClient);
        return ResponseEntity.ok(cards);
    }

    /** Find Cards by client ID and institution ID
     * @param bic A BIC (institution ID)
     * @param idClient A Client ID
     * @return List of all the Cards
     */
    @RequestMapping("findcards-by-client-and-institution/{bic}/{idClient}")
    public ResponseEntity<?> findCardsByClientAndInstitution(@PathVariable String idClient, @PathVariable String bic) {
        List<Card> cards = cardRepository.findCardsByClientAndInstitution(idClient, bic);
        return ResponseEntity.ok(cards);
    }

    /** Find Cards by card number
     * @param cardNumber A card number
     * @return List of all the Cards
     */
    @RequestMapping("findcards-by-cardnumber/{cardNumber}")
    public ResponseEntity<?> findCardByCardNumber(@PathVariable String cardNumber) {
        Card card = cardRepository.findCardByCardNumber(cardNumber);
        return ResponseEntity.ok(card);
    }

    /** Find Cards by wallet ID
     * @param walletId A wallet ID
     * @return List of all the Cards
     */
    @RequestMapping("findcards-by-wallet/{walletId}")
    public ResponseEntity<?> findCardsByWallet(@PathVariable Long walletId) {
        List<Card> cards = cardRepository.findCardsByWallet(walletId);
        return ResponseEntity.ok(cards);
    }


    // Ressources from Request Wallet

    /**
     * @param requestCard RequestCard to be created
     * @return The created RequestCard or null if already exists
     */
    @PostMapping("request-card/save")
    public ResponseEntity<?> requestCard(@RequestBody RequestCard requestCard) {
        logger.debug("Call : Create RequestCard");
        return ResponseEntity.ok(cardService.saveRequestCard(requestCard));
    }

    /** Find Cards by client and institution
     * @param applicationClient
     * @param financialInstitution
     * @return List of all the requests Cards
     */
    @RequestMapping("request-card-by-client-and-institution/{applicationClient}/{financialInstitution}")
    public ResponseEntity<?> findRequestCardByClientAndInstitution(@PathVariable ApplicationClient applicationClient, @PathVariable FinancialInstitution financialInstitution) {
        RequestCard card = requestCardRepository.findRequestCardByClientAndInstitution(applicationClient, financialInstitution);
        return ResponseEntity.ok(card);
    }

    /** Find Cards by client and institution
     * @param bic
     * @return List of all the requests Cards
     */
    @RequestMapping("request-card-by-institution/{bic}")
    public ResponseEntity<?> findAllByFinancialInstitution(@PathVariable String bic) {
        List<RequestCard> cards = requestCardRepository.findAllByFinancialInstitution(bic);
        return ResponseEntity.ok(cards);
    }

    /** Find Cards by client and institution
     * @param bic
     * @return List of all the requests Cards
     */
    @RequestMapping("request-card-pending-by-institution/{bic}")
    public ResponseEntity<?> findPendingRequestCardsByInstitution(@PathVariable String bic, Request.REQUEST_STATUS pendingRequestStatus) {
        List<RequestCard> cards = requestCardRepository.findPendingRequestCardsByInstitution(bic, pendingRequestStatus);
        return ResponseEntity.ok(cards);
    }

    /** Find Cards by client and institution
     * @param bic
     * @return List of all the requests Cards
     */
    @RequestMapping("request-card-by-client-and-institution/{nationalRegister}/{bic}")
    public ResponseEntity<?> findByApplicationClientAndFinancialInstitution(@PathVariable String nationalRegister, String bic) {
        RequestCard card = requestCardRepository.findByApplicationClientAndFinancialInstitution(nationalRegister, bic);
        return ResponseEntity.ok(card);
    }
}
