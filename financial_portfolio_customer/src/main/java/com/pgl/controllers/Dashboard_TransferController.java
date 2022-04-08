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
import java.util.Locale;
import java.util.ResourceBundle;

public class Dashboard_TransferController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label Label_SEPA;
    @FXML
    private Label my_institution;
    @FXML
    private Label amount_label;
    @FXML
    private Label structured_comm;
    @FXML
    private Label free_comm;
    @FXML
    private Label or_label;
    @FXML
    private Label institutionFrom_label;
    @FXML
    private Label accountTo_label;
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
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        Label_SEPA.setText(bundle.getString("Label_SEPA"));
        my_institution.setText(bundle.getString("my_institution"));
        institutionFrom_label.setText(bundle.getString("institutionFrom_label"));
        accountTo_label.setText(bundle.getString("accountTo_label"));
        accountTo.setPromptText(bundle.getString("accountTo"));
        amount_label.setText(bundle.getString("amount_label"));
        amount.setPromptText(bundle.getString("amount"));
        structured_comm.setText(bundle.getString("structured_comm"));
        structuredCommunication.setPromptText(bundle.getString("structuredCommunication"));
        or_label.setText(bundle.getString("or_label"));
        free_comm.setText(bundle.getString("free_comm"));
        freeCommunication.setPromptText(bundle.getString("freeCommunication"));
        password.setPromptText(bundle.getString("Mot.de.passe_field"));
        transferButton.setText(bundle.getString("transferButton"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(UserService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(UserService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        setText();
        
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
            alert.setHeaderText(bundle.getString("error10"));
            alert.showAndWait();

        }else if(institutionFrom.getValue() != null &&
                institutionFrom.getValue() != null &&
                accountTo.getText() != "" &&
                amount.getText() != "" &&
                (structuredCommunication.getText() != "" || freeCommunication.getText() != "") &&
                password.getText() != ""){

            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes5"));
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
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error6"));
            alert.showAndWait();
        }
    }
}
