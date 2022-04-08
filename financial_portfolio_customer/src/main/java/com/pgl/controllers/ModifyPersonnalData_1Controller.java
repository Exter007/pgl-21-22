package com.pgl.controllers;

import com.pgl.services.FinancialProductHolderService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
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

    @Inject
    static UserService userService = new UserService();
    static FinancialProductHolderService financialProductHolderService = new FinancialProductHolderService();
    static ResourceBundle bundle;

    @FXML
    private Button checkPassword;
    @FXML
    private PasswordField password;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        password.setPromptText(bundle.getString("Mot.de.passe_field"));
        checkPassword.setText(bundle.getString("check_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(UserService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(UserService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        setText();
    }

    /**
     * Check if the password entered is correct
     * @param event the click of the mouse on the button
     */
    @FXML
    private void check_Password(MouseEvent event) {
        if(password.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error9"));
            alert.showAndWait();
        }else if (financialProductHolderService.checkPassword(UserService.getCurrentUser(), password.getText())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-ModifyPersonnalData2.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ModifyPersonnalData_1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = (Stage) checkPassword.getScene().getWindow();
            stage.close();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        }
    }
}
