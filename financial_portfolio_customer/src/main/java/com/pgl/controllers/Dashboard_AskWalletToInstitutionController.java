package com.pgl.controllers;

import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard_AskWalletToInstitutionController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label askWalletTitle;
    @FXML
    private Label askWalletLabel;
    @FXML
    private ChoiceBox institutionChoice;
    @FXML
    private Button sendButton;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        sendButton.setText(bundle.getString("SendRequest"));
        askWalletLabel.setText(bundle.getString("askWalletLabel"));
        askWalletTitle.setText(bundle.getString("askWalletTitle"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        institutionChoice.setItems(FXCollections.observableArrayList("ING", "AXA", "KBC", "CRELAN", "BNP PARIBAS", "BELFIUS"));
        bundle = DashboardController.bundle;
        setText();
    }

    /**
     * Sends a request to access the transfer functionality
     * @param event the click of the mouse on the button
     */
    @FXML
    private void send_request(MouseEvent event) {
        if(institutionChoice.getValue() != null){

            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes3"));
            alert.showAndWait();

            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();

        }else if(institutionChoice.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error3"));
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error6"));
            alert.showAndWait();
        }
    }
}
