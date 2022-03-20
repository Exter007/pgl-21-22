package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountActivationController implements Initializable {

    @Inject
    static UserService userService = new UserService();

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
     * Activate the account that has just been created
     * @param event the click of the mouse on the button
     */
    @FXML
    private void validate(MouseEvent event) {

        if(code.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez rentrer le code que vous avez reçu par e-mail");
            alert.showAndWait();
        }else {
            User user = new User();
            /*
            user.setLogin(UserService.getCurrentUser().getLogin());
            user.setPassword(UserService.getCurrentUser().getPassword());
            user.setEmail(UserService.getCurrentUser().getEmail());
            user.setToken(UserService.getCurrentUser().getToken());
            user.setActive(UserService.getCurrentUser().getActive());
            user.setRole(UserService.getCurrentUser().getRole());
            */
            boolean result = true;

            if(result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Compte activé avec sucés");
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-login.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);

                } catch (IOException ex) {
                    Logger.getLogger(AccountActivationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur de validation");
                alert.setHeaderText("Le code renseigné est incorrect");
                alert.showAndWait();
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
            Logger.getLogger(AccountActivationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}