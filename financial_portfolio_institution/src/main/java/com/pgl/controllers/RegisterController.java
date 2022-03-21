/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pgl.models.Address;
import com.pgl.models.FinancialInstitution;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class RegisterController implements Initializable {

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

        }else if(!Validators.check_BIC(BIC.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre BIC n'est pas au bon format ! \n - 8 chiffres");
            alert.showAndWait();

        }else if(!Validators.check_email(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre e-mail n'est pas au bon format");
            alert.showAndWait();

        }else if(!Validators.check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre mot de passe doit comporter au moins 1 lettre et 1 chiffre");
            alert.showAndWait();

        }else if(!password.getText().equals(password2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setContentText("Les mots de passes ne correspondent pas");
            alert.showAndWait();

        }else {
            FinancialInstitution user = build_user();

            user = userService.register(user);
            UserService.setCurrentUser(user);

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

    /**
     * Build a new user
     * @return the user
     */
    public FinancialInstitution build_user(){

        Address address = new Address(
                street.getText(), number.getText(), city.getText(), zipCode.getText() , country.getText()
        );

        FinancialInstitution user = new FinancialInstitution(
                BIC.getText(), institutionName.getText(), password.getText(), email.getText(),address,false,null
        );

        user.toUpdate = false;

        return user;
    }
}
