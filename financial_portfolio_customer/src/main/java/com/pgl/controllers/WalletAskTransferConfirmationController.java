package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.BankAccountService;
import com.pgl.services.FinancialProductService;
import com.pgl.services.RequestTransferService;
import com.pgl.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class WalletAskTransferConfirmationController implements Initializable {

    UserService userService = new UserService();
    static ResourceBundle bundle;

    BankAccountService bankAccountService = new BankAccountService();
    RequestTransferService requestTransferService = new RequestTransferService();

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label Validation_label;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        confirmButton.setText(bundle.getString("Yes_btn"));
        cancelButton.setText(bundle.getString("No_btn"));
        Validation_label.setText(bundle.getString("ValidationAsk_label"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = WalletController.bundle;
        setText();
    }

    /**
     * Confirm the request for access to transfers
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_confirm(MouseEvent event) {
        BankAccount bankAccount = bankAccountService.getCurrentBankAccount();
        RequestTransfer rqt = new RequestTransfer(
                RequestTransfer.REQUEST_STATUS.PENDING, userService.getCurrentUser(), bankAccount
        );
        try {
            RequestTransfer result = requestTransferService.save(rqt);
            if(result != null && result.getStatus() == RequestTransfer.REQUEST_STATUS.PENDING){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes8"));
                alert.showAndWait();
            } else if(result != null && result.getStatus() == RequestTransfer.REQUEST_STATUS.ACCEPTED){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("error17"));
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error11"));
                alert.showAndWait();
            }
        } catch (Exception e) {
            Logger.getLogger(WalletAskTransferConfirmationController.class.getName()).severe(e.getMessage());
        }

        DynamicViews.loadBorderCenter("Client-Wallet");
    }

    /**
     * Cancel the request for access to transfers
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_cancel(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Wallet");
    }
}
