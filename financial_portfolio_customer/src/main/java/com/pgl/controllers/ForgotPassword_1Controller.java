package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
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

    static UserService userService = new UserService();

    @FXML
    private TextField name;

    @FXML
    private TextField nationalRegisterNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Vérifie que l'e-mail est au bon format (@ et .)
     * @param email
     * @return true ou false
     */
    private boolean check_email(String email){
        boolean hasArobase =  email.contains("@");
        boolean hasPoint =  email.contains(".");
        return hasArobase && hasPoint;
    }

    @FXML
    private void validate(MouseEvent event) {
        if(name.getText().isEmpty() || nationalRegisterNumber.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez remplir tout les champs");
            alert.showAndWait();
        }else if(!Validators.check_nationalRegisterNumber(nationalRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre n° de registre national n'est pas au bon format ! \n - 11 chiffres\n - Pas de lettres");
            alert.showAndWait();
        }else{
            ApplicationClient client = new ApplicationClient();
            client.setNationalRegister(nationalRegisterNumber.getText());
            client.setFirstName(name.getText());

            User user = new User();
            user.setLogin(client.buildLogin());

            User result = userService.sendPasswordResetCode(user);

            if(result != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Un mail de réinitialisation vous a été envoyé");
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-ForgotPassword_2.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);

                } catch (IOException ex) {
                    Logger.getLogger(ForgotPassword_1Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
