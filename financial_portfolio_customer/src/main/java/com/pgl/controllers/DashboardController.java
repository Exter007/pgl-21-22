package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField code;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void edit_profil(MouseEvent event) {

    }

    @FXML
    private void diconnect(MouseEvent event) {

    }

    @FXML
    private void languageFR(MouseEvent event) {

    }

    @FXML
    private void languageEN(MouseEvent event) {

    }

    @FXML
    private void ask_wallet(MouseEvent event) {

    }

    @FXML
    private void transfer(MouseEvent event) {

    }

    @FXML
    private void wallet_1(MouseEvent event) {

    }

    @FXML
    private void wallet_2(MouseEvent event) {

    }

    @FXML
    private void wallet_3(MouseEvent event) {

    }

    @FXML
    private void wallet_4(MouseEvent event) {

    }

    @FXML
    private void wallet_5(MouseEvent event) {

    }

    @FXML
    private void wallet_6(MouseEvent event) {

    }

    @FXML
    private void day(MouseEvent event) {

    }

    @FXML
    private void week(MouseEvent event) {

    }

    @FXML
    private void month(MouseEvent event) {

    }

    @FXML
    private void year(MouseEvent event) {

    }

    @FXML
    private void graph(MouseEvent event) {

    }

    @FXML
    private void list(MouseEvent event) {

    }

    @FXML
    private void tableview(MouseEvent event) {

    }

    @FXML
    private void export(MouseEvent event) {

    }
}
