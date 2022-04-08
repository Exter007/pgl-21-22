package com.pgl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pgl.models.ApplicationClient;
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

public class LoginController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Menu menu;
    @FXML
    private TextField name;
    @FXML
    private TextField nationalRegisterNumber;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink forgot_password_link;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink create_account_link;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        menu.setText(bundle.getString("Langue_menu"));
        name.setPromptText(bundle.getString("Nom.complet_field"));
        nationalRegisterNumber.setPromptText(bundle.getString("N.de.registre.national_field"));
        password.setPromptText(bundle.getString("Mot.de.passe_field"));
        forgot_password_link.setText(bundle.getString("Mot.de.passe.oublié_link"));
        login_btn.setText(bundle.getString("Connexion_btn"));
        create_account_link.setText(bundle.getString("Creer.un.compte_link"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        setText();
    }

    /**
     * Connect the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void login(MouseEvent event) {
        if(name.getText().isEmpty() || nationalRegisterNumber.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        }else if(!Validators.check_nationalRegisterNumber(nationalRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error2"));
            alert.showAndWait();
        }else{
            ApplicationClient user = new ApplicationClient();
            user.setNationalRegister(nationalRegisterNumber.getText());
            user.setFirstName(name.getText());
            String login = user.buildLogin();
            boolean response = userService.login(login, password.getText());

            if (response){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Open the registration window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void register(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Register.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open the password reset window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void password_reset(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-ForgotPassword_1.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        setText();
    }
}