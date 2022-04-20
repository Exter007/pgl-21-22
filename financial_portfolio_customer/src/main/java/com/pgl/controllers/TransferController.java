package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.*;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransferController implements Initializable {

    ApplicationClientService clientService = new ApplicationClientService();
    WalletService walletService = new WalletService();
    TransactionService transactionService = new TransactionService();
    BankAccountService bankAccountService = new BankAccountService();
    Wallet currentWallet = walletService.getCurrentWallet();
    BankAccount bankAccount;
    float fAmount;
    static ResourceBundle bundle;

    @FXML
    private Label Label_SEPA;
    @FXML
    private Label my_institution;
    @FXML
    private Label institutionLabel;
    @FXML
    private Label amount_label;
    @FXML
    private Label structured_comm;
    @FXML
    private Label free_comm;
    @FXML
    private Label or_label;
    @FXML
    private Label institutionFrom_label;
    @FXML
    private Label accountTo_label;
    @FXML
    private Label recipientNameLabel;
    @FXML
    private ComboBox accountFromCB;
    @FXML
    private TextField accountTo;
    @FXML
    private TextField amount;
    @FXML
    private TextField structuredCommunication;
    @FXML
    private TextField freeCommunication;
    @FXML
    private TextField recipientName;
    @FXML
    private PasswordField password;
    @FXML
    private Button transferButton;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        Label_SEPA.setText(bundle.getString("SEPA_label"));
        my_institution.setText(bundle.getString("MyInstitution_label"));
        institutionFrom_label.setText(bundle.getString("InstitutionFrom_label"));
        accountTo_label.setText(bundle.getString("AccountTo_label"));
        accountTo.setPromptText(bundle.getString("AccountTo_field"));
        amount_label.setText(bundle.getString("Amount_label"));
        amount.setPromptText(bundle.getString("Amount_field"));
        structured_comm.setText(bundle.getString("Structured_label"));
        structuredCommunication.setPromptText(bundle.getString("Structured_field"));
        or_label.setText(bundle.getString("OR_label"));
        free_comm.setText(bundle.getString("Free_label"));
        freeCommunication.setPromptText(bundle.getString("Free_field"));
        password.setPromptText(bundle.getString("Password_field"));
        transferButton.setText(bundle.getString("Transfer_btn"));
        recipientNameLabel.setText(bundle.getString("RecipientName_label"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        setText();
        institutionLabel.setText(currentWallet.getName());
        loadAccountFromCB();
//        accountFromCB.setItems(FXCollections.observableArrayList("BE68 5390 0754 7034", "BE87 2345 9864 0181", "BE02 8929 2456 0186"));
    }

    private void loadAccountFromCB(){
        List<String> accountFromList = new ArrayList<>();
        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByWallet();
        bankAccounts.forEach( bankAccount -> {
            if ( bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT)
                    || bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT)
                    && bankAccount.getTransferAccess().equals(FinancialProduct.TRANSFER_ACCESS.AUTHORIZED))
            {
                accountFromList.add(bankAccount.getIban());
            }
        });

        accountFromCB.getItems().addAll(accountFromList);
    }

    /**
     * Make a transfer
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if(accountFromCB.getValue() == null
                || accountTo.getText().isEmpty()
                || recipientName.getText().isEmpty()
                || amount.getText().isEmpty()
                || (structuredCommunication.getText().isEmpty() && freeCommunication.getText().isEmpty())
                || password.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        } else if(!Validators.check_IBAN(accountTo.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error23"));
            alert.showAndWait();
        } else if(!structuredCommunication.getText().isEmpty() && !freeCommunication.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error10"));
            alert.showAndWait();
        }else if((fAmount = Validators.convertToFloat(amount.getText())) == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error24"));
            alert.showAndWait();
        } else if(((bankAccount = bankAccountService.getBankAccountByIBAN(accountFromCB.getValue().toString())) != null)
                && (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT))
                && (fAmount > ((YoungAccount) bankAccount).getMaxTransactionAmount()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(
                    bundle.getString("error25") + " "
                    + ((YoungAccount) bankAccount).getMaxTransactionAmount()
            );
            alert.showAndWait();
            amount.setText(String.valueOf(fAmount));
        }else if (!clientService.checkPassword(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        }else{
            Transaction transaction = transactionService.save(buildTransaction());
            if (transaction != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes5"));
                alert.showAndWait();

                DynamicViews.loadBorderCenter("Client-Wallet");
            }
        }
//        else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error6"));
//            alert.showAndWait();
//        }
    }

    private Transaction buildTransaction(){
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER);
//        FinancialProduct product = productService.getAccountByIBAN(accountFromCB.getValue().toString());
        transaction.setBankAccount(bankAccount);
        transaction.setDestinationIBAN(accountTo.getText());
        transaction.setAmount(fAmount);
        if (!freeCommunication.getText().isEmpty() ){
            transaction.setCommunication_type(Transaction.COMMUNICATION_TYPE.FREE);
            transaction.setCommunication(freeCommunication.getText());
        }else if (!structuredCommunication.getText().isEmpty()){
            transaction.setCommunication_type(Transaction.COMMUNICATION_TYPE.STRUCTURED);
            transaction.setCommunication(structuredCommunication.getText());
        }
        transaction.setStatus(Request.REQUEST_STATUS.PENDING);
        transaction.setDestinationName(recipientName.getText());
        transaction.setDate(new Date());

        return transaction;

    }
}
