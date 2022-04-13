package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPersonnalDataController implements Initializable {

    UserService userService = new UserService();
    FinancialInstitutionService institutionService = new FinancialInstitutionService();
    static ResourceBundle bundle;

    @FXML
    private PasswordField password;
    @FXML
    private Button Next_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        password.setPromptText(bundle.getString("Password_field"));
        Next_btn.setText(bundle.getString("Next_btn"));
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
            alert.setHeaderText(bundle.getString("error7"));
            alert.showAndWait();
        }else{
            boolean result = institutionService.checkPassword(password.getText());
            if (result) {
                DynamicViews.loadBorderCenter("Institution-ModifyPersonnalData2");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error8"));
                alert.showAndWait();
            }
        }
    }
}
