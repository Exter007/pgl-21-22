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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletAskFinancialProductController implements Initializable {

    static ResourceBundle bundle;

    @FXML
    private ChoiceBox institution;
    @FXML
    private ChoiceBox productType;
    @FXML
    private Button sendButton;
    @FXML
    private Label AskProduct_label;
    @FXML
    private Label Institution_label;
    @FXML
    private Label Product_label;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        AskProduct_label.setText(bundle.getString("AskProduct_label"));
        Institution_label.setText(bundle.getString("Institution_label"));
        Product_label.setText(bundle.getString("Product_label"));
        sendButton.setText(bundle.getString("Send_btn"));
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
     * Send a request to add a financial product
     * @param event the click of the mouse on the button
     */
    @FXML
    private void send_request(MouseEvent event) {
       if(institution.getValue() != null && productType.getValue() != null){

            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes3"));
            alert.showAndWait();

            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();

        }else if(institution.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error3"));
            alert.showAndWait();

        }else if(productType.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error15"));
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error6"));
            alert.showAndWait();
        }
    }
}
