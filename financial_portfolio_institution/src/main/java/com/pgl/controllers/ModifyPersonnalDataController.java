package com.pgl.controllers;

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

    @Inject
    static UserService userService = new UserService();
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
        if(password.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error7"));
            alert.showAndWait();

        }else if (true) {
            //TODO


            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-ModifyPersonnalData2.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);

            } catch (IOException ex) {
                Logger.getLogger(ModifyPersonnalDataController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = (Stage) password.getScene().getWindow();
            stage.close();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        }
    }
}
