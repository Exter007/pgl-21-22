package com.pgl.controllers;

import com.pgl.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAddClientController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField customerNationalRegisterNumber;

    @FXML
    private PasswordField password;

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
    private void add_customer(ActionEvent event) {
        //TODO
    }
}
