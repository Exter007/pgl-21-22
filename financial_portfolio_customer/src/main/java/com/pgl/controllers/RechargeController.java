package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.*;
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

public class RechargeController implements Initializable {

    ApplicationClientService clientService = new ApplicationClientService();
    WalletService walletService = new WalletService();
    TransactionService transactionService = new TransactionService();
    BankAccountService bankAccountService = new BankAccountService();
    Wallet currentWallet = walletService.getCurrentWallet();
    BankAccount currentBankAccount = bankAccountService.getCurrentBankAccount();
    float fAmount;
    static ResourceBundle bundle;

    @FXML
    private Label recharge_label;
    @FXML
    private Label my_institution;
    @FXML
    private Label institutionLabel;
    @FXML
    private Label account_label;
    @FXML
    private Label account;
    @FXML
    private Label amountAccount_label;
    @FXML
    private Label amountAccount;
    @FXML
    private Label linkedAccount_label;
    @FXML
    private Label linkedAccount;
    @FXML
    private Label amountLinkedAccount_label;
    @FXML
    private Label amountLinkedAccount;
    @FXML
    private Label operation_label;
    @FXML
    private ComboBox operationCB;
    @FXML
    private Label amount_label;
    @FXML
    private TextField amount;
    @FXML
    private PasswordField password;
    @FXML
    private Button rechargeButton;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        recharge_label.setText(bundle.getString("Recharge_label"));
        my_institution.setText(bundle.getString("MyInstitution_label"));
        account_label.setText(bundle.getString("Account_label"));
        amountAccount_label.setText(bundle.getString("AmountAccount_label"));
        linkedAccount_label.setText(bundle.getString("LinkedAccount_label"));
        amountLinkedAccount_label.setText(bundle.getString("AmountLinkedAccount_label"));
        operation_label.setText(bundle.getString("Operation_label"));
        amount_label.setText(bundle.getString("Amount_label"));
        password.setPromptText(bundle.getString("Password_field"));
        rechargeButton.setText(bundle.getString("rechargeButton"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        setText();
        setData();
    }

    private void setData() {
        institutionLabel.setText(currentWallet.getName());
        amountAccount.setText(String.valueOf(currentBankAccount.getAmount()));
        account.setText(currentBankAccount.getNature().name());
        linkedAccount.setText(currentBankAccount.getJointIban().getIban());
        amountLinkedAccount.setText(String.valueOf(currentBankAccount.getJointIban().getAmount()));
        loadOperationCB();
    }

    private void loadOperationCB() {
        List<String> operationList = new ArrayList<>();
        operationList.add(bundle.getString("Recharge"));
        operationList.add(bundle.getString("Unload"));

        operationCB.getItems().addAll(operationList);
    }

    /**
     * Make a Recharge or Unload
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if (operationCB.getValue() == null
                || amount.getText().isEmpty()
                || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        } else if ((fAmount = Validators.convertToFloat(amount.getText())) == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error19"));
            alert.showAndWait();
        } else if (!clientService.checkPassword(password.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        } else {
            Transaction transaction = transactionService.save(buildTransaction());
            if (transaction != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes5"));
                alert.showAndWait();

                DynamicViews.loadBorderCenter("Client-Wallet");
            }
        }
    }

    private Transaction buildTransaction() {
        Transaction transaction = new Transaction();
        transaction.setBankAccount(currentBankAccount);
        transaction.setDestinationIBAN(currentBankAccount.getJointIban().getIban());
        FinancialProductHolder holder = currentBankAccount.getJointIban().getFinancialProductHolders().get(0);
        transaction.setDestinationName(holder.getName() + holder.getFirstName());
        if (operationCB.getSelectionModel().getSelectedItem().equals(bundle.getString("Recharge"))) {
            transaction.setType(Transaction.TRANSACTION_TYPE.INCOMING_TRANSFER);
        } else if (operationCB.getSelectionModel().getSelectedItem().equals(bundle.getString("Unload"))) {
            transaction.setType(Transaction.TRANSACTION_TYPE.OUTGOING_TRANSFER);
        }
        transaction.setAmount(fAmount);
        transaction.setStatus(Request.REQUEST_STATUS.PENDING);
        transaction.setDate(new Date());

        return transaction;
    }
}