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
        Label_SEPA.setText(bundle.getString("SEPA_label"));
        my_institution.setText(bundle.getString("MyInstitution_label"));
        institutionFrom_label.setText(bundle.getString("InstitutionFrom_label"));
        accountTo_label.setText(bundle.getString("AccountTo_label"));
        accountTo.setPromptText(bundle.getString("AccountTo_field"));
        amount_label.setText(bundle.getString("Amount_label"));
        amount.setPromptText(bundle.getString("Amount_field"));
        structured_comm.setText(bundle.getString("Structured_label"));
        structuredCommunication.setPromptText(bundle.getString("Structured_field"));
        or_label.setText(bundle.getString("OR_label"));
        free_comm.setText(bundle.getString("Free_label"));
        freeCommunication.setPromptText(bundle.getString("Free_field"));
        password.setPromptText(bundle.getString("Password_field"));
        transferButton.setText(bundle.getString("Transfer_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
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
