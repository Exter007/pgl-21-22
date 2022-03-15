package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private Label welcome;

    @FXML
    private TableView wallet_tableview;

    @FXML
    private DatePicker from_date;

    @FXML
    private DatePicker to_date;

    @FXML
    private ChoiceBox export_format;

    @FXML
    private TableView products_tableview;

    @FXML
    private ListView products_listview;

    @FXML
    private LineChart products_linechart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void edit_profil(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-ModifyPersonnalData.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void diconnect(ActionEvent event) {
        //TODO
    }

    @FXML
    private void languageFR(ActionEvent event) {
        //TODO
    }

    @FXML
    private void languageEN(ActionEvent event) {
        //TODO
    }

    @FXML
    private void ask_wallet(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Dashboard-AskWalletToInstitution.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void transfer(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Dashboard-Transfer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void wallet_1(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Wallet.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        products_tableview.visibleProperty().setValue(false);
        products_listview.visibleProperty().setValue(false);
        products_linechart.visibleProperty().setValue(true);
    }

    @FXML
    private void list(MouseEvent event) {
        products_tableview.visibleProperty().setValue(false);
        products_listview.visibleProperty().setValue(true);
        products_linechart.visibleProperty().setValue(false);
    }

    @FXML
    private void tableview(MouseEvent event) {
        products_tableview.visibleProperty().setValue(true);
        products_listview.visibleProperty().setValue(false);
        products_linechart.visibleProperty().setValue(false);
    }

    @FXML
    private void export(MouseEvent event) {

    }
}
