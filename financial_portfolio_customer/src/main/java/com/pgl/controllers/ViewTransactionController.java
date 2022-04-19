package com.pgl.controllers;

import com.pgl.models.Transaction;
import com.pgl.services.TransactionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ViewTransactionController implements Initializable {

    static ResourceBundle bundle;

    @FXML
    private Label transactionNumber_label;
    @FXML
    private Label transactionNumber;
    @FXML
    private Label operation_label;
    @FXML
    private Label operation;
    @FXML
    private Label date_label;
    @FXML
    private Label date;
    @FXML
    private Label senderAccount_label;
    @FXML
    private Label senderAccount;
    @FXML
    private Label recipientAccount_label;
    @FXML
    private Label recipientAccount;
    @FXML
    private Label recipientName_label;
    @FXML
    private Label recipientName;
    @FXML
    private Label amount_label;
    @FXML
    private Label amount;
    @FXML
    private Label communication_label;
    @FXML
    private Label communication;
    @FXML
    private Label status_label;
    @FXML
    private Label status;
    @FXML
    private Label formulation_label;
    @FXML
    private Label formulation;

    TransactionService transactionService = new TransactionService();

    Transaction transaction;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadTransaction();
    }

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        transactionNumber_label.setText(bundle.getString("TransactionNumber_label"));
        date_label.setText(bundle.getString("Date_label"));
        operation_label.setText(bundle.getString("Operation_label"));
        senderAccount_label.setText(bundle.getString("SenderAccount_label"));
        recipientAccount_label.setText(bundle.getString("RecipientAccount_label"));
        recipientName_label.setText(bundle.getString("RecipientName_label"));
        amount_label.setText(bundle.getString("Amount_label"));
        communication_label.setText(bundle.getString("Communication_label"));
        status_label.setText(bundle.getString("Status_label"));
        formulation_label.setText(bundle.getString("Formulation_label"));
    }

    /**
     * Load Transaction data to the interface
     */
    public void loadTransaction(){
        transaction = transactionService.getCurrentTransaction();
        transactionNumber.setText(transaction.getTransactionNumber());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(dateFormat.format(transaction.getDate()));
        operation.setText(transaction.getType().name());
        senderAccount.setText((transaction.getBankAccount().getIban() != null
                ? transaction.getBankAccount().getIban()
                : transaction.getBankAccount().getNature().name()));
        recipientAccount.setText(transaction.getDestinationIBAN());
        recipientName.setText(transaction.getDestinationName());
        amount.setText(String.valueOf(transaction.getAmount()));
        communication.setText(transaction.getCommunication());
        status.setText(transaction.getStatus().name());
        formulation.setText(transaction.getFormulation());
    }
}
