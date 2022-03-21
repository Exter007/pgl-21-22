package com.pgl.controllers;

import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardNotificationsController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private Label name;
    @FXML
    private Label subject;
    @FXML
    private Label date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    /**
     * Accept a client request
     * @param event the click of the mouse on the button
     */
    @FXML
    private void accept_request(ActionEvent event) {
        //TODO
    }

    /**
     * Refuse a client request
     * @param event the click of the mouse on the button
     */
    @FXML
    private void refuse_request(ActionEvent event) {
        //TODO
    }
}
