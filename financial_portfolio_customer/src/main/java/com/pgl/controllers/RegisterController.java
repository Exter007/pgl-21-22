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
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField nationalRegisterNumber;
    @FXML
    private TextField email;
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
     * Vérifie que le numéro de registre national n'est composé que de nombres et si il fait 11 caractères
     * @param nationalRegisterNumber
     * @return true ou false
     */
    private boolean check_nationalRegisterNumber(String nationalRegisterNumber){
        boolean isNumeric =  nationalRegisterNumber.matches("[+-]?\\d*(\\.\\d+)?");
        isNumeric = (nationalRegisterNumber.length() == 11);
        return isNumeric;
    }

    /**
     * Vérifie que l'e-mail est au bon format (@ et .)
     * @param email
     * @return true ou false
     */
    private boolean check_email(String email){
        boolean hasArobase =  email.contains("@");
        boolean hasPoint =  email.contains(".");
        return hasArobase && hasPoint;
    }

    /**
     * Vérifie si le mot de passe est au bon format (1 lettre et 1 chiffre au minimum)
     * @param password
     * @return true ou false
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

    public ApplicationClient build_user(){
        ApplicationClient user = new ApplicationClient(nationalRegisterNumber.getText(),
                firstName.getText(), lastName.getText(), password.getText(),
                email.getText(), null,false);
        user.toUpdate = false;

        return user;
    }

    @FXML
    private void register(MouseEvent event) {
        if(email.getText().isEmpty() ||
                nationalRegisterNumber.getText().isEmpty() ||
                firstName.getText().isEmpty() ||
                lastName.getText().isEmpty() ||
                password.getText().isEmpty() ||
                password2.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Un ou plusieurs champs sont invalides, veuillez réessayer");
            alert.showAndWait();

        }else if(!check_nationalRegisterNumber(nationalRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre n° de registre national n'est pas au bon format ! \n - 11 chiffres\n - Pas de lettres");
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

            user = userService.register(user);
            UserService.setCurrentUser(user);

            if (user != null){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-AccountValidation.fxml"));
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

    @FXML
    private void login(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void languageFR(ActionEvent event) {
        //TODO
    }

    @FXML
    private void languageEN(ActionEvent event) {
        //TODO
    }
}
