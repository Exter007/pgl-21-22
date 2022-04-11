package com.pgl.controllers;

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
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;
    static String lang = "fr";


    @FXML
    private Menu Menu;
    @FXML
    private TextField Name_field;
    @FXML
    private TextField BIC_field;
    @FXML
    private PasswordField Password_field;
    @FXML
    private Hyperlink PasswordForget_link;
    @FXML
    private Button Connexion_btn;
    @FXML
    private Hyperlink CreateAccount_link;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        Menu.setText(bundle.getString("Language_menu"));
        Name_field.setPromptText(bundle.getString("Name_field"));
        BIC_field.setPromptText(bundle.getString("BIC_field"));
        Password_field.setPromptText(bundle.getString("Password_field"));
        PasswordForget_link.setText(bundle.getString("PasswordForget_link"));
        Connexion_btn.setText(bundle.getString("Connexion_btn"));
        CreateAccount_link.setText(bundle.getString("CreateAccount_link"));
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
        if(Name_field.getText().isEmpty() || BIC_field.getText().isEmpty() || Password_field.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        }else if(!Validators.check_BIC(BIC_field.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error2"));
            alert.showAndWait();
        }else{
            FinancialInstitution user = new FinancialInstitution();
            user.setBIC(BIC_field.getText());
            user.setName(Name_field.getText());
            String login = user.buildLogin();
            boolean response = userService.login(login, Password_field.getText());

            if (response){
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard.fxml"));
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
     * Open the registration window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void register(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Register.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open the password reset window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void password_reset(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-ForgotPassword_1.fxml"));
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
}
