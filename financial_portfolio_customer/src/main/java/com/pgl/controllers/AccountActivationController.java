package com.pgl.controllers;

import com.pgl.services.UserService;
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

    @FXML
    private void validate(MouseEvent event) {

//        if(code.getText().isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Le code ne peut pas etre vide");
//            alert.showAndWait();
//        }else {
////            User user = new User();
//            UserService.getCurrentUser().setValidateCode(Integer.parseInt(code.getText()));
////            UserService.currentUser = user;
//            boolean result = userService.accountActivation(UserService.getCurrentUser());
//
//            if(result == true){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setHeaderText("Compte activé avec sucés");
//                alert.showAndWait();
//
//                try {
//                    Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
//                    Stage newWindow = new Stage();
//                    Scene scene = new Scene(root);
//                    newWindow.setScene(scene);
//                    GlobalStage.setStage(newWindow);
//
//                } catch (IOException ex) {
//                    Logger.getLogger(AccountActivationController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText("Erreur de validation");
//                alert.setContentText("Le code entré est incorrect");
//                alert.showAndWait();
//            }
//        }

    }
}
