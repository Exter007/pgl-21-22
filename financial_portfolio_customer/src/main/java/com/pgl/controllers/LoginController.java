package com.pgl.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pgl.models.ApplicationClient;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
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

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void login(MouseEvent event) throws JsonProcessingException {

        if(email.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Les champs ne peuvent pas etre vides");
            alert.showAndWait();
        }else{
            boolean response = userService.login(email.getText(), password.getText());
            if (response){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/my_portfolios.fxml"));
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

    @FXML
    private void register(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void password_reset(MouseEvent event) {
        if(email.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez entrer votre email");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la réinitialisation du mot de passe?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
//                ApplicationClient user = new ApplicationClient();
//                user.setEmail(email.getText());
//                UserService.setCurrentUser(user);
//                boolean res = userService.sendPasswordResetCode(user);
//
//                if(res == true){
//                    try {
//                        Parent root = FXMLLoader.load(getClass().getResource("/views/resetPassword.fxml"));
//                        Stage newWindow = new Stage();
//                        Scene scene = new Scene(root);
//                        newWindow.setScene(scene);
//                        GlobalStage.setStage(newWindow);
//
//                    } catch (IOException ex) {
//                        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
            }
        }

    }

    @FXML
    private void account_activation(MouseEvent event) {
        if(email.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez entrer votre email");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez l'activation du compte?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
//                ApplicationClient user = new ApplicationClient();
//                user.setEmail(email.getText());
//                UserService.setCurrentUser(user);
//                boolean res = userService.sendAccountResetCode(user);
//
//                if(res == true){
//                    alert = new Alert(Alert.AlertType.INFORMATION);
////                alert.setTitle("Error");
//                    alert.setHeaderText("Un code vous a étè envoyé par mail");
//                    alert.showAndWait();
//
//                    try {
//                        Parent root = FXMLLoader.load(getClass().getResource("/views/accountActivation.fxml"));
//                        Stage newWindow = new Stage();
//                        Scene scene = new Scene(root);
//                        newWindow.setScene(scene);
//                        GlobalStage.setStage(newWindow);
//
//                    } catch (IOException ex) {
//                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
            }

        }

    }
}
