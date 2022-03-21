package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
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

public class ForgotPassword_1Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Check that the e-mail is in the right format (@ and .)
     * @param email institution email
     * @return true or false
     */
    private boolean check_email(String email){
        boolean hasArobase =  email.contains("@");
        boolean hasPoint =  email.contains(".");
        return hasArobase && hasPoint;
    }

    /**
     * Check if the e-mail entered is the correct one
     * @param event the click of the mouse on the button
     */
    @FXML
    private void validate(MouseEvent event) {
        if(email.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez rentrer votre e-mail");
            alert.showAndWait();

        }else if(!check_email(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre e-mail n'est pas au bon format");
            alert.showAndWait();

        }else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-ForgotPassword_2.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);

            } catch (IOException ex) {
                Logger.getLogger(ForgotPassword_1Controller.class.getName()).log(Level.SEVERE, null, ex);
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
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(ForgotPassword_1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
