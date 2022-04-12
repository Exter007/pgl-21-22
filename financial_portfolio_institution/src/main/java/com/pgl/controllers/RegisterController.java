/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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

import javax.inject.Inject;


public class RegisterController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;
    static String lang;

    @FXML
    private Menu menu;
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
    @FXML
    private Hyperlink login_link;
    @FXML
    private Label Password_label;
    @FXML
    private Button register_button;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        menu.setText(bundle.getString("Language_menu"));
        BIC.setPromptText(bundle.getString("BIC_field"));
        institutionName.setPromptText(bundle.getString("InstitutionName_field"));
        number.setPromptText(bundle.getString("Number_field"));
        street.setPromptText(bundle.getString("Street_field"));
        zipCode.setPromptText(bundle.getString("ZipCode_field"));
        city.setPromptText(bundle.getString("City_field"));
        country.setPromptText(bundle.getString("Country_field"));
        email.setPromptText(bundle.getString("Email_field"));
        password.setPromptText(bundle.getString("Password_field"));
        Password_label.setText(bundle.getString("Password_label"));
        password2.setPromptText(bundle.getString("Password2_field"));
        login_link.setText(bundle.getString("Login_link"));
        register_button.setText(bundle.getString("Register_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = LoginController.bundle;
        setText();
        lang = LoginController.lang;
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
            alert.setHeaderText(bundle.getString("error3"));
            alert.showAndWait();

        }else if(!Validators.check_BIC(BIC.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error2"));
            alert.showAndWait();

        }else if(!Validators.check_email(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error4"));
            alert.showAndWait();

        }else if(!Validators.check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error5"));
            alert.showAndWait();

        }else if(!password.getText().equals(password2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("error6"));
            alert.showAndWait();

        }else {
            FinancialInstitution user = build_user();
            user.setLanguage(lang);

            user = userService.register(user);
            userService.setCurrentUser(user);

            if (user != null){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-AccountValidation.fxml"));
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Change the language to French
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageFR(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        setText();
        lang = "fr";
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        setText();
        lang = "en";
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