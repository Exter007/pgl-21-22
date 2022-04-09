package com.pgl.controllers;

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

public class WalletAskTransferConfirmationController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

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
        //TODO

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(bundle.getString("succes8"));
        alert.showAndWait();

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Cancel the request for access to transfers
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_cancel(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
