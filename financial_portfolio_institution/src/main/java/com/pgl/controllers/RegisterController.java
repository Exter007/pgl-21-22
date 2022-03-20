/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pgl.models.ApplicationClient;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;


public class RegisterController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField institutionName;
    @FXML
    private TextField BIC;
    @FXML
    private TextField email;
    @FXML
    private TextField number;
    @FXML
    private TextField street;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField city;
    @FXML
    private TextField country;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    /**
     * Checks that the BIC is composed of 8 characters long
     * @param BIC the financial institution BIC
     * @return true or false
     */
    private boolean check_BIC(String BIC){
        boolean isOK = (BIC.length() == 8);
        return isOK;
    }

    /**
     * Check that the e-mail is in the right format (@ and .)
     * @param email the user email
     * @return true or false
     */
    private boolean check_email(String email){
        boolean hasArobase =  email.contains("@");
        boolean hasPoint =  email.contains(".");
        return hasArobase && hasPoint;
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
     * Create a new user
     * @return the user
     */
    public ApplicationClient build_user(){
        /*
        String token = String.valueOf(10000 + (int) (Math.random()*(99999-10000))) ;
        ApplicationClient user = new ApplicationClient(nationalRegisterNumber.getText(),
                firstName.getText(), lastName.getText(), password.getText(),
                email.getText(), token,false);
        user.toUpdate = false;

        return user;
        */
        ApplicationClient user = new ApplicationClient();
        return user;
    }

    /**
     * Register the new user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void register(MouseEvent event) {
        if(email.getText().isEmpty() ||
                BIC.getText().isEmpty() ||
                institutionName.getText().isEmpty() ||
                number.getText().isEmpty() ||
                street.getText().isEmpty() ||
                zipCode.getText().isEmpty() ||
                city.getText().isEmpty() ||
                country.getText().isEmpty() ||
                password.getText().isEmpty() ||
                password2.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Un ou plusieurs champs sont invalides, veuillez réessayer");
            alert.showAndWait();

        }else if(!check_BIC(BIC.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre BIC n'est pas au bon format ! \n - 8 chiffres");
            alert.showAndWait();

        }else if(!check_email(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre e-mail n'est pas au bon format");
            alert.showAndWait();

        }else if(!check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre mot de passe doit comporter au moins 1 lettre et 1 chiffre");
            alert.showAndWait();

        }else if(!password.getText().equals(password2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setContentText("Les mots de passes ne correspondent pas");
            alert.showAndWait();

        }else {
            ApplicationClient user = build_user();
            //TODO
            /*
            user = userService.register(user);
            UserService.setCurrentUser(user);
            */

            if (user != null){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-AccountValidation.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur");
                alert.setHeaderText("Les informations que vous avez renseigné ne sont pas correct ou sont déjà utilisées");
                alert.showAndWait();
            }
        }
    }

    /**
     * Open the login window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void login(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
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