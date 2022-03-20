package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
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

public class ForgotPassword_2Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField nationalRegisterNumber;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField newPassword2;
    @FXML
    private TextField code;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Checks that the national registry number is composed of numbers only and if it is 11 characters long
     * @param nationalRegisterNumber the user national register number
     * @return true or false
     */
    private boolean check_nationalRegisterNumber(String nationalRegisterNumber){
        boolean isNumeric =  nationalRegisterNumber.matches("[+-]?\\d*(\\.\\d+)?");
        isNumeric = (nationalRegisterNumber.length() == 11);
        return isNumeric;
    }

    /**
     * Checks if the password is in the right format (at least 1 letter and 1 number)
     * @param password the user password
     * @return true or false
     */
    private boolean check_password(String password){
        char c;
        boolean alpha = false;
        boolean number = false;
        for(int i=0; i < password.length(); i++) {
            c = password.charAt(i);
            if( Character.isDigit(c)) {
                number = true;
            }
            if (Character.isUpperCase(c) || Character.isLowerCase(c)) {
                alpha = true;
            }
            if(number && alpha)
                return true;
        }
        return false;
    }

    /**
     * Reset the password
     * @param event the click of the mouse on the button
     */
    @FXML
    private void reset(MouseEvent event) {
        if(nationalRegisterNumber.getText().isEmpty() ||
                newPassword.getText().isEmpty() ||
                newPassword2.getText().isEmpty() ||
                code.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez remplir tout les champs");
            alert.showAndWait();

        }else if(!check_nationalRegisterNumber(nationalRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre n° de registre national n'est pas correct");
            alert.showAndWait();

        }else if(!check_password(newPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre mot de passe doit comporter au moins 1 lettre et 1 chiffre");
            alert.showAndWait();

        }else if(!newPassword.getText().equals(newPassword2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setContentText("Les mots de passes ne correspondent pas");
            alert.showAndWait();

        }else{
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Votre mot de passe a bien été changé !");
            alert.showAndWait();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);

            } catch (IOException ex) {
                Logger.getLogger(ForgotPassword_2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Back to previous window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(ForgotPassword_2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Change the language to French
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageFR(ActionEvent event) {
        //TODO
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        //TODO
    }
}
