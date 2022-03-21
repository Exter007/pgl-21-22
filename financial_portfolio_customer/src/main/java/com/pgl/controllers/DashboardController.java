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
import java.util.Optional;
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
        loadUserConnected();
    }

    /**
     * Change the user name in the welcome label
     */
    public void loadUserConnected(){
        welcome.setText(UserService.getCurrentUser().getLogin());
    }

    /**
     * Open a window allowing you to modify your personal data
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void edit_profil(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-ModifyPersonnalData.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Disconnects the user from the application
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void diconnect(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la déconnexion ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            userService.logout();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Client-login.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);

            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Change the language to French
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageFR(ActionEvent event) {
        //TODO
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        //TODO
    }

    /**
     * Open a window allowing you to request access to a wallet from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_wallet(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Dashboard-AskWalletToInstitution.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to request access to the transfer functionality from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void transfer(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Dashboard-Transfer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redirect to the wallet 1 window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void wallet_1(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Wallet.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redirect to the wallet 2 window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void wallet_2(MouseEvent event) {

    }

    /**
     * Redirect to the wallet 3 window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void wallet_3(MouseEvent event) {

    }

    /**
     * Redirect to the wallet 4 window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void wallet_4(MouseEvent event) {

    }

    /**
     * Redirect to the wallet 5 window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void wallet_5(MouseEvent event) {

    }

    /**
     * Redirect to the wallet 6 window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void wallet_6(MouseEvent event) {

    }

    /**
     * Change the time granularity to day
     * @param event the click of the mouse on the button
     */
    @FXML
    private void day(MouseEvent event) {

    }

    /**
     * Change the time granularity to week
     * @param event the click of the mouse on the button
     */
    @FXML
    private void week(MouseEvent event) {

    }

    /**
     * Change the time granularity to month
     * @param event the click of the mouse on the button
     */
    @FXML
    private void month(MouseEvent event) {

    }

    /**
     * Change the time granularity to year
     * @param event the click of the mouse on the button
     */
    @FXML
    private void year(MouseEvent event) {

    }

    /**
     * Display data in graphical form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void graph(MouseEvent event) {
        products_tableview.visibleProperty().setValue(false);
        products_listview.visibleProperty().setValue(false);
        products_linechart.visibleProperty().setValue(true);
    }

    /**
     * Display data in list form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void list(MouseEvent event) {
        products_tableview.visibleProperty().setValue(false);
        products_listview.visibleProperty().setValue(true);
        products_linechart.visibleProperty().setValue(false);
    }

    /**
     * Display data in table form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void tableview(MouseEvent event) {
        products_tableview.visibleProperty().setValue(true);
        products_listview.visibleProperty().setValue(false);
        products_linechart.visibleProperty().setValue(false);
    }

    /**
     * Export all data from the client's wallets
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export(MouseEvent event) {

    }
}
