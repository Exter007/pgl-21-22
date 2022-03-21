package com.pgl.controllers;

import com.pgl.services.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard_TransferController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private ChoiceBox institutionFrom;
    @FXML
    private ChoiceBox accountFrom;
    @FXML
    private TextField accountTo;
    @FXML
    private TextField amount;
    @FXML
    private TextField structuredCommunication;
    @FXML
    private TextField freeCommunication;
    @FXML
    private PasswordField password;
    @FXML
    private Button transferButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        institutionFrom.setItems(FXCollections.observableArrayList("ING", "AXA", "KBC", "CRELAN", "BNP PARIBAS", "BELFIUS"));
        accountFrom.setItems(FXCollections.observableArrayList("BE68 5390 0754 7034", "BE87 2345 9864 0181", "BE02 8929 2456 0186"));
    }

    /**
     * Make a transfer
     * @param event the click of the mouse on the button
     */
    @FXML
    private void transfer(MouseEvent event) {
        if(structuredCommunication.getText() != "" && freeCommunication.getText() != ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Veuillez choisir un seul type de communication");
            alert.showAndWait();

        }else if(institutionFrom.getValue() != null &&
                institutionFrom.getValue() != null &&
                accountTo.getText() != "" &&
                amount.getText() != "" &&
                (structuredCommunication.getText() != "" || freeCommunication.getText() != "") &&
                password.getText() != ""){

            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation");
            alert.setHeaderText("Votre virement a bien été efféctué !");
            alert.showAndWait();

            Stage stage = (Stage) transferButton.getScene().getWindow();
            stage.close();

        }else if(institutionFrom.getValue() == null ||
                institutionFrom.getValue() == null ||
                accountTo.getText() == "" ||
                amount.getText() == "" ||
                (structuredCommunication.getText() == "" && freeCommunication.getText() == "") ||
                password.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Veuillez remplir tout les champs");
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Il s'est produit une erreur lors de votre demande, veuillez réessayer");
            alert.showAndWait();
        }
    }
}
