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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TableView products_tableview;

    @FXML
    private DatePicker from_date;

    @FXML
    private DatePicker to_date;

    @FXML
    private ChoiceBox export_format;

    @FXML
    private TableView financialProduct_tableview;

    @FXML
    private ListView financialProduct_listview;

    @FXML
    private LineChart financialProduct_linechart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void ask_financialProduct(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-AskFinancialProduct.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void show_hideProducts(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Wallet-HideProducts.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void show_notifications(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-Notifications.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void hide_product(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-HideConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ask_transfer(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-AskTransferConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void delete_financialProduct(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-DeleteConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void day(MouseEvent event) {
        //TODO
    }

    @FXML
    private void week(MouseEvent event) {
        //TODO
    }

    @FXML
    private void month(MouseEvent event) {
        //TODO
    }

    @FXML
    private void year(MouseEvent event) {
        //TODO
    }

    @FXML
    private void graph(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(false);
        financialProduct_listview.visibleProperty().setValue(false);
        financialProduct_linechart.visibleProperty().setValue(true);
    }

    @FXML
    private void list(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(false);
        financialProduct_listview.visibleProperty().setValue(true);
        financialProduct_linechart.visibleProperty().setValue(false);
    }

    @FXML
    private void tableview(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(true);
        financialProduct_listview.visibleProperty().setValue(false);
        financialProduct_linechart.visibleProperty().setValue(false);
    }

    @FXML
    private void export(MouseEvent event) {
        //TODO
    }
}
