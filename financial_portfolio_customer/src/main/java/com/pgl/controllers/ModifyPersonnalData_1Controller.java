package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.ApplicationClient;
import com.pgl.services.ApplicationClientService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModifyPersonnalData_1Controller implements Initializable {

    ApplicationClientService clientService = new ApplicationClientService();

    static ResourceBundle bundle;

    @FXML
    private Button checkPassword;
    @FXML
    private PasswordField password;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        password.setPromptText(bundle.getString("Password_field"));
        checkPassword.setText(bundle.getString("Check_btn"));
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
     * Check if the password entered is correct
     * @param event the click of the mouse on the button
     */
    @FXML
    private void check_Password(MouseEvent event) {
        if(password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        }else if(!Validators.check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error4"));
            alert.showAndWait();
        }else{
            boolean result = clientService.checkPassword(password.getText());
            if (result) {
                DynamicViews.loadBorderCenter("Client-ModifyPersonnalData2");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error8"));
                alert.showAndWait();
            }
        }
    }
}
