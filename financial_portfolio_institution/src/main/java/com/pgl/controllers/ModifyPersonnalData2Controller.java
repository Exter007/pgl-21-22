package com.pgl.controllers;

import com.pgl.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPersonnalData2Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField email;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPassword2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadUserConnected();
    }

    public void loadUserConnected(){
        //welcome.setText(UserService.getCurrentUser().getLogin());
    }

    @FXML
    private void edit_personnalData(MouseEvent event) {
        //TODO

        Stage stage = (Stage) newPassword.getScene().getWindow();
        stage.close();
    }
}
