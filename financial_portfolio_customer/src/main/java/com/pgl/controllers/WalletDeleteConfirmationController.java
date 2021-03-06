package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class WalletDeleteConfirmationController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label confirmDelete_label;


    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        confirmButton.setText(bundle.getString("Yes_btn"));
        cancelButton.setText(bundle.getString("No_btn"));
        confirmDelete_label.setText(bundle.getString("ValidationDelete_label"));
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
     * Delete confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_confirm(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(bundle.getString("succes9"));
        alert.showAndWait();

        DynamicViews.loadBorderCenter("Client-Wallet");
    }

    /**
     * Cancellation of the deletion
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_cancel(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Wallet");
    }
}
