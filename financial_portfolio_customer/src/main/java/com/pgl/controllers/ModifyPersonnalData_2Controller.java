package com.pgl.controllers;

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
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPersonnalData_2Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Button edit_btn;
    @FXML
    private Label password_label;
    @FXML
    private Label title_label;
    @FXML
    private TextField email;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField newPassword2;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        email.setPromptText(bundle.getString("email"));
        newPassword.setPromptText(bundle.getString("New.Password"));
        newPassword2.setPromptText(bundle.getString("New.Password.2"));
        edit_btn.setText(bundle.getString("edit_btn"));
        password_label.setText(bundle.getString("Password.label"));
        title_label.setText(bundle.getString("title_label"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        setText();
    }

    /**
     * Reset filled fields
     * @param event the click of the mouse on the button
     */
    @FXML
    private void modify(MouseEvent event) {
        if(email.getText() == "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez entrez vos nouvelles données !");
            alert.showAndWait();

        }else if(email.getText() != "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Votre email a bien été changé !");
            alert.showAndWait();

        }else if(email.getText() == "" &&
                ((newPassword.getText() != "" && newPassword2.getText() == "") ||
                        (newPassword.getText() == "" && newPassword2.getText() != ""))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez remplir les 2 champs mot de passe !");
            alert.showAndWait();

        }else if(email.getText() == "" &&
                (newPassword.getText() != newPassword2.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Les mots de passes ne correspondent pas !");
            alert.showAndWait();

        }else{
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Vos données ont bien été changées !");
            alert.showAndWait();

            Stage stage = (Stage) edit_btn.getScene().getWindow();
            stage.close();
        }
    }
}
