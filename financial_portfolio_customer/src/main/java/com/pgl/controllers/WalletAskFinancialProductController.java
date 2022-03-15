package com.pgl.controllers;

import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletAskFinancialProductController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private ChoiceBox institution;

    @FXML
    private ChoiceBox productType;

    @FXML
    private Button sendButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void send_request(MouseEvent event) {
        if(institution.getValue() != null && productType.getValue() != null){

            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation");
            alert.setHeaderText("Votre demande a bien été envoyé !");
            alert.showAndWait();

            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();

        }else if(institution.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Veuillez choisir une institution");
            alert.showAndWait();

        }else if(productType.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Veuillez choisir un produit");
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Il s'est produit une erreur lors de votre demande, veuillez réessayer");
            alert.showAndWait();
        }
    }
}
