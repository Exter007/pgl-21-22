package com.pgl.controllers.extension1;

import com.pgl.models.*;
import com.pgl.models.extension1.Card;
import com.pgl.models.extension1.CreditCard;
import com.pgl.models.extension1.DebitCard;
import com.pgl.models.extension1.RequestCard;
import com.pgl.services.BankAccountService;
import com.pgl.services.extension1.RequestCardService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletAskCardController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;


    RequestCardService requestCardService = new RequestCardService();
    BankAccountService bankAccountService = new BankAccountService();

    @FXML
    private Label askCard_label;
    @FXML
    private ChoiceBox currentAccount_choicebox;
    @FXML
    private Label currentAccount_label;
    @FXML
    private ChoiceBox cardType_choicebox;
    @FXML
    private Label cardType_label;
    @FXML
    private ChoiceBox distribution_choicebox;
    @FXML
    private Label distribution_label;
    @FXML
    private Button send_btn;
    @FXML

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        askCard_label.setText(bundle.getString("AskCard_label"));
        currentAccount_label.setText(bundle.getString("CurrentAccount_label"));
        cardType_label.setText(bundle.getString("CardType_label"));
        distribution_label.setText(bundle.getString("Distribution_label"));
        send_btn.setText(bundle.getString("Send_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(userService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(userService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        setText();
        loadChoicebox();
    }

    private void loadChoicebox(){
        cardType_choicebox.getItems().add(Card.CARD_TYPE.DEBIT_CARD);
        cardType_choicebox.getItems().add(Card.CARD_TYPE.CREDIT_CARD);
        List<BankAccount> bankAccounts = bankAccountService.findBankAccountByNationalRegister();
        ObservableList<String> accounts = FXCollections.observableArrayList();
        for(BankAccount f : bankAccounts){
            accounts.add(f.getIban());
        }
        currentAccount_choicebox.setItems(accounts);
        distribution_choicebox.getItems().add(CreditCard.CREDIT_CARD_TYPE.AMERICAN_EXPRESS);
        distribution_choicebox.getItems().add(CreditCard.CREDIT_CARD_TYPE.MASTERCARD);
        distribution_choicebox.getItems().add(CreditCard.CREDIT_CARD_TYPE.VISA);
        distribution_choicebox.getItems().add(DebitCard.DEBIT_CARD_TYPE.BANCONTACT);
        distribution_choicebox.getItems().add(DebitCard.DEBIT_CARD_TYPE.MAESTRO);
    }

    /**
     * Sends a request to access the transfer functionality
     * @param event the click of the mouse on the button
     */
    @FXML
    private void send_request(MouseEvent event) {
        if(currentAccount_choicebox.getValue() == null || cardType_choicebox.getValue() == null || distribution_choicebox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        }else{
            BankAccount bankAccount = bankAccountService.getBankAccountByIBAN("12345678910112");
            RequestCard requestCard = new RequestCard(
                    Request.REQUEST_STATUS.PENDING,
                    userService.getCurrentUser(),
                    bankAccount);
            if(cardType_choicebox.getValue() == Card.CARD_TYPE.DEBIT_CARD){
                requestCard.setCardType(Card.CARD_TYPE.DEBIT_CARD);
                if(distribution_choicebox.getValue() == DebitCard.DEBIT_CARD_TYPE.BANCONTACT){
                    requestCard.setDebitCardType(DebitCard.DEBIT_CARD_TYPE.BANCONTACT);
                }if(distribution_choicebox.getValue() == DebitCard.DEBIT_CARD_TYPE.MAESTRO){
                    requestCard.setDebitCardType(DebitCard.DEBIT_CARD_TYPE.MAESTRO);
                }else{
                    requestCard.setDebitCardType(DebitCard.DEBIT_CARD_TYPE.BANCONTACT);
                }
            }
            if(cardType_choicebox.getValue() == Card.CARD_TYPE.CREDIT_CARD){
                requestCard.setCardType(Card.CARD_TYPE.CREDIT_CARD);
                if(distribution_choicebox.getValue() == CreditCard.CREDIT_CARD_TYPE.AMERICAN_EXPRESS){
                    requestCard.setCreditCardType(CreditCard.CREDIT_CARD_TYPE.AMERICAN_EXPRESS);
                }if(distribution_choicebox.getValue() == CreditCard.CREDIT_CARD_TYPE.MASTERCARD){
                    requestCard.setCreditCardType(CreditCard.CREDIT_CARD_TYPE.MASTERCARD);
                }if(distribution_choicebox.getValue() == CreditCard.CREDIT_CARD_TYPE.VISA){
                    requestCard.setCreditCardType(CreditCard.CREDIT_CARD_TYPE.VISA);
                }else{
                    requestCard.setCreditCardType(CreditCard.CREDIT_CARD_TYPE.VISA);
                }
            }

            /*
            RequestCard rc = requestCardService.save(requestCard);
            if(rc != null && rc.getStatus() == Request.REQUEST_STATUS.PENDING){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("success11"));
                alert.showAndWait();
            }else if(rc != null && rc.getStatus() == Request.REQUEST_STATUS.ACCEPTED){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("error21"));
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error22"));
                alert.showAndWait();
            }
            */
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(bundle.getString("success11"));
            alert.showAndWait();

            try{
                Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = (Stage) send_btn.getScene().getWindow();
            stage.close();
        }
    }
}