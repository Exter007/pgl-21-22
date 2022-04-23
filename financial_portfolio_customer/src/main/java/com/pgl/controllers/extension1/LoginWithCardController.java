package com.pgl.controllers.extension1;

import com.pgl.controllers.LoginController;
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
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginWithCardController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;
    static String lang;

    @FXML
    private Menu menu;
    @FXML
    private TextField CardNumber;
    @FXML
    private PasswordField PINCode;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink login_with_id_link;
    @FXML
    private Hyperlink create_account_link;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        menu.setText(bundle.getString("Language_menu"));
        CardNumber.setPromptText(bundle.getString("CardNumber_field"));
        PINCode.setPromptText(bundle.getString("PINCode_field"));
        login_btn.setText(bundle.getString("Connexion_btn"));
        login_with_id_link.setText(bundle.getString("LoginWithID_link"));
        create_account_link.setText(bundle.getString("CreateAccount_link"));
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
     * Connect the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void login(MouseEvent event) {
        if(CardNumber.getText().isEmpty() || PINCode.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        }else if(!Validators.check_cardNumber(CardNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error18"));
            alert.showAndWait();
        }else if(!Validators.check_PINNumber(PINCode.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error19"));
            alert.showAndWait();
        }else{
            boolean response = userService.loginWithCard(CardNumber.getText(), PINCode.getText());

            if (response){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWithCardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Open the card login window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void loginWithID(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(LoginWithCardController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginWithCardController.class.getName()).log(Level.SEVERE, null, ex);
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