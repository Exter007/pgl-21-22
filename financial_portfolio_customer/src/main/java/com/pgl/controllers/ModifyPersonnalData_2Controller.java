package com.pgl.controllers;

import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPersonnalData_2Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField email;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPassword2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void modify(MouseEvent event) {
        if(email.getText() == "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez entrez vos nouvelles données !");
            alert.showAndWait();

        }else if(email.getText() != "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Votre email a bien été changé !");
            alert.showAndWait();

        }else if(email.getText() == "" &&
                ((newPassword.getText() != "" && newPassword2.getText() == "") ||
                        (newPassword.getText() == "" && newPassword2.getText() != ""))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez remplir les 2 champs mot de passe !");
            alert.showAndWait();

        }else if(email.getText() == "" &&
                (newPassword.getText() != newPassword2.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Les mots de passes ne correspondent pas !");
            alert.showAndWait();

        }else{
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Vos données ont bien été changées !");
            alert.showAndWait();
        }
    }
}
