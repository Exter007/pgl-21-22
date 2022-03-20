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

public class LoginController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField name;
    @FXML
    private TextField BIC;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    /**
     * Remove spaces and concatenated
     * @param name the financial institution name
     * @param BIC the financial institution BIC
     * @return name and BIC concatenated
     */
    private String username(String name, String BIC){
        String clearSpaceName = name.replaceAll("\\s+","");
        return clearSpaceName+BIC;
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
     * Connect the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void login(MouseEvent event) {
        if(name.getText().isEmpty() || BIC.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez remplir tout les champs");
            alert.showAndWait();
        }else if(!check_BIC(BIC.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre BIC n'est pas au bon format ! \n - 8 caractères");
            alert.showAndWait();
        }else{
            ApplicationClient user = new ApplicationClient();
            user.setNationalRegister(BIC.getText());
            user.setFirstName(name.getText());
            //String login = user.buildLogin();
            //boolean response = userService.login(login, password.getText());
            if (true){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Les données que vous avez renseigné ne sont pas correct");
                alert.showAndWait();
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
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-ForgotPassword_1.fxml"));
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
