package com.pgl.controllers;

import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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

    @FXML
    private ChoiceBox institutionChoice;
    @FXML
    private Button sendButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        institutionChoice.setItems(FXCollections.observableArrayList("ING", "AXA", "KBC", "CRELAN", "BNP PARIBAS", "BELFIUS"));
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
            alert.setHeaderText("Confirmation");
            alert.setHeaderText("Votre demande a bien été envoyé !");
            alert.showAndWait();

            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();

        }else if(institutionChoice.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Veuillez choisir une institution");
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Il s'est produit une erreur lors de votre demande, veuillez réessayer");
            alert.showAndWait();
        }
    }
}
