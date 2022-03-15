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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;


public class RegisterController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker date_of_birth;
    @FXML
    private TextField nationalRegister;
    @FXML
    private TextField email;
    @FXML
    private TextField city;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password_confirm;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    private void on_register(MouseEvent event) throws JsonProcessingException {

        if(email.getText().isEmpty() || name.getText().isEmpty() || password.getText().isEmpty()
                || password_confirm.getText().isEmpty()){
            List<String> errors = new ArrayList<>();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Les champs suivants sont invalides");
            String errorString = "- email\n - Nom\n - Mot de passe\n - Confirmation de mot de passe";

            alert.setContentText(errorString);
            alert.showAndWait();

        }else if(!password.getText().equals(password_confirm.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//            alert.setHeaderText("Error");
            alert.setContentText("Les mots de passes ne correspondent pas");
            alert.showAndWait();
        } else {
            ApplicationClient user = build_user();

            user = userService.register(user);
//            user.toUpdate = true;
            UserService.setCurrentUser(user);

            if (user != null){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/accountActivation.fxml"));
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
    private void on_login(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ApplicationClient build_user(){
        int code = 10000 + (int) (Math.random()*(99999-10000));
        ApplicationClient user = new ApplicationClient(nationalRegister.getText(),
                                surname.getText(), name.getText(), password.getText(),
                                email.getText(), String.valueOf(code),false);
        user.toUpdate = false;

//        user.setName(name.getText());
//        user.setFirstName(surname.getText());
//        user.setEmail(email.getText());
//        user.setPassword(password.getText());
//        user.setNationalRegister(nationalRegister.getText());

//        user.setActive(false);

        return user;
    }

    public String buildLogin(String firstName, String name, String nationalRegister) {
        return  (firstName != null ? firstName : "")
                .concat(name != null ? name: "")
                .concat(nationalRegister != null ? nationalRegister: "");
    }
}
