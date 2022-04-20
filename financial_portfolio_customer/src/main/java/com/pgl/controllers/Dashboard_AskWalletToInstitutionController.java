package com.pgl.controllers;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.Request;
import com.pgl.models.RequestWallet;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.RequestWalletService;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import com.pgl.utils.GlobalStage;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard_AskWalletToInstitutionController implements Initializable {

    static UserService userService = new UserService();
    static ResourceBundle bundle;

    WalletService walletService = new WalletService();
    RequestWalletService requestWalletService = new RequestWalletService();
    FinancialInstitutionService financialInstitutionService = new FinancialInstitutionService();

    FinancialInstitution currentInstitution;

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
        sendButton.setText(bundle.getString("Send_Request"));
        askWalletTitle.setText(bundle.getString("AskWalletTitle"));
        askWalletLabel.setText(bundle.getString("AskWalletLabel"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        loadFinancialInstitutions();
        setText();
    }

    private void loadFinancialInstitutions(){
        List<FinancialInstitution> financialInstitutions = financialInstitutionService.getAllFinancialInstitutions();
        ObservableList<String> institutions = FXCollections.observableArrayList();
        for(FinancialInstitution f : financialInstitutions){
            institutions.add(f.getName());
        }

        institutionChoice.setItems(institutions);

        institutionChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            financialInstitutions.forEach(institution -> {
                if(institution.getName().equals(newValue)){
                    currentInstitution = institution;
                }
            });

        });
    }

    /**
     * Sends a request to access the transfer functionality
     * @param event the click of the mouse on the button
     */
    @FXML
    private void send_request(MouseEvent event) {
        if(institutionChoice.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error3"));
            alert.showAndWait();
        }else{
            RequestWallet requestWallet = new RequestWallet(
                    Request.REQUEST_STATUS.PENDING, userService.getCurrentUser(), currentInstitution
            );
            RequestWallet rq = requestWalletService.save(requestWallet);
            if(rq != null && rq.getStatus() == Request.REQUEST_STATUS.PENDING){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes3"));
                alert.showAndWait();
            }else if(rq != null && rq.getStatus() == Request.REQUEST_STATUS.ACCEPTED){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("error20"));
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error22"));
                alert.showAndWait();
            }

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
