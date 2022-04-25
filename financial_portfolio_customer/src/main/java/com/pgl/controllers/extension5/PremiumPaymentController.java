package com.pgl.controllers.extension5;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.ApplicationClientService;
import com.pgl.services.BankAccountService;
import com.pgl.services.TransactionService;
import com.pgl.services.WalletService;
import com.pgl.services.extension5.InsuranceContractService;
import com.pgl.utils.Validators;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PremiumPaymentController implements Initializable {

    ApplicationClientService clientService = new ApplicationClientService();
    WalletService walletService = new WalletService();
    TransactionService transactionService = new TransactionService();
    InsuranceContractService insuranceService = new InsuranceContractService();
    Wallet currentWallet = walletService.getCurrentWallet();
    BankAccount bankAccount;
    float fAmount;
    static ResourceBundle bundle;

    @FXML
    private Label PremiumPayment_label;
    @FXML
    private Label my_institution;
    @FXML
    private Label institutionLabel;
    @FXML
    private Label paymentMode_label;
    @FXML
    private ComboBox paymentModeCB;
    @FXML
    private Label insuranceNumber_label;
    @FXML
    private Label insuranceNumber;
    @FXML
    private Label amount_label;
    @FXML
    private Label amount;
    @FXML
    private Label structured_comm;
    @FXML
    private Label free_comm;
    @FXML
    private Label or_label;
    @FXML
    private Label iban_label;
    @FXML
    private Label holder_label;
    @FXML
    private TextField structuredCommunication;
    @FXML
    private TextField freeCommunication;
    @FXML
    private PasswordField password;
    @FXML
    private Button paymentButton;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        PremiumPayment_label.setText(bundle.getString("PremiumPayment_label"));
        institutionLabel.setText(bundle.getString("MyInstitution_label"));
        insuranceNumber_label.setText(bundle.getString("InstitutionFrom_label"));
        amount_label.setText(bundle.getString("AccountTo_label"));
        iban_label.setText(bundle.getString("AccountTo_field"));
        holder_label.setText(bundle.getString("Amount_label"));
        free_comm.setText(bundle.getString("Amount_field"));
        structured_comm.setText(bundle.getString("Structured_label"));
        structuredCommunication.setPromptText(bundle.getString("Structured_field"));
        or_label.setText(bundle.getString("OR_label"));
        password.setPromptText(bundle.getString("Password_field"));
        paymentButton.setText(bundle.getString("PaymentButton_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        setText();
        institutionLabel.setText(currentWallet.getName());
        loadPaymentModeCB();
    }

    private void loadPaymentModeCB(){
        List<String> PaymentModeList = new ArrayList<>();
        PaymentModeList.add("Manuel");
        PaymentModeList.add("Domiciliation");
        paymentModeCB.getItems().addAll(PaymentModeList);
        paymentModeCB.getSelectionModel().selectFirst();
    }

    /**
     * Make a Payement
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
//        if(iban.getText().isEmpty()
//                || amount.getText().isEmpty()
//                || (structuredCommunication.getText().isEmpty() && freeCommunication.getText().isEmpty())
//                || password.getText().isEmpty())
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error1"));
//            alert.showAndWait();
//        } else if(!Validators.check_IBAN(accountTo.getText())){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error23"));
//            alert.showAndWait();
//        } else if(!structuredCommunication.getText().isEmpty() && !freeCommunication.getText().isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error10"));
//            alert.showAndWait();
//        }else if((fAmount = Validators.convertToFloat(amount.getText())) == 0){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error24"));
//            alert.showAndWait();
//        } else if(((bankAccount = bankAccountService.getBankAccountByIBAN(accountFromCB.getValue().toString())) != null)
//                && (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT))
//                && (fAmount > ((YoungAccount) bankAccount).getMaxTransactionAmount()))
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(
//                    bundle.getString("error25") + " "
//                    + ((YoungAccount) bankAccount).getMaxTransactionAmount()
//            );
//            alert.showAndWait();
//            amount.setText(String.valueOf(fAmount));
//        }else if (!clientService.checkPassword(password.getText())){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error8"));
//            alert.showAndWait();
//        }else{
//            Transaction transaction = transactionService.save(buildTransaction());
//            if (transaction != null) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setHeaderText(bundle.getString("succes5"));
//                alert.showAndWait();
//
//                DynamicViews.loadBorderCenter("Client-Wallet");
//            }
//        }
//        else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(bundle.getString("error6"));
//            alert.showAndWait();
//        }
    }

    private Transaction buildTransaction(){
        Transaction transaction = new Transaction();
//        transaction.setType(Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER);
////        FinancialProduct product = productService.getAccountByIBAN(accountFromCB.getValue().toString());
//        transaction.setBankAccount(bankAccount);
//        transaction.setDestinationIBAN(accountTo.getText());
//        transaction.setAmount(fAmount);
//        if (!freeCommunication.getText().isEmpty() ){
//            transaction.setCommunication_type(Transaction.COMMUNICATION_TYPE.FREE);
//            transaction.setCommunication(freeCommunication.getText());
//        }else if (!structuredCommunication.getText().isEmpty()){
//            transaction.setCommunication_type(Transaction.COMMUNICATION_TYPE.STRUCTURED);
//            transaction.setCommunication(structuredCommunication.getText());
//        }
//        transaction.setStatus(Request.REQUEST_STATUS.PENDING);
//        transaction.setDestinationName(recipientName.getText());
//        transaction.setDate(new Date());
//
        return transaction;
//
    }
}
