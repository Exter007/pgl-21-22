package com.pgl.controllers;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.Request;
import com.pgl.models.RequestWallet;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.RequestWalletService;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard_AskWalletToInstitutionController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    WalletService walletService = new WalletService();
    RequestWalletService requestWalletService = new RequestWalletService();
    FinancialInstitutionService financialInstitutionService = new FinancialInstitutionService();

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
        List<FinancialInstitution> financialInstitutions = financialInstitutionService.getAllFinancialInstitutions();
        ObservableList<String> institutions = FXCollections.observableArrayList();
        for(FinancialInstitution f : financialInstitutions){
            institutions.add(f.getName());
        }
        institutionChoice.setItems(institutions);
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

            FinancialInstitution f = financialInstitutionService.getFinancialInstitutionByName((String) institutionChoice.getValue());

            RequestWallet requestWallet = new RequestWallet(Request.REQUEST_STATUS.PENDING, UserService.getCurrentUser(), f);
            try {
                if(requestWalletService.createRequestWallet(requestWallet) != null){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(bundle.getString("succes3"));
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(bundle.getString("errorAlready"));
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


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
