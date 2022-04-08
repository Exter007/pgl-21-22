package com.pgl.controllers;

import com.pgl.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class WalletDeleteConfirmationController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;


    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){

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
     * Delete confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_confirm(MouseEvent event) {
        //TODO

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setHeaderText("Ce compte a bien été supprimer ! Il a également été archivé.");
        alert.showAndWait();

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Cancellation of the deletion
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_cancel(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
