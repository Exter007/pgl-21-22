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
     * Change the institution name in the welcome label
     */
    public void loadUserConnected(){
        welcome.setText(UserService.getCurrentUser().getName());
    }

    /**
     * Open a window allowing you to modify your personal data
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void edit_profil(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Institution-ModifyPersonnalData.fxml"));
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
    private void disconnect(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la déconnexion ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            userService.logout();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-login.fxml"));
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
     * Open a window showing all notifications
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_Notifications(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Institution-Dashboard-Notifications.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Search for products according to the chosen filters
     * @param event the click of the mouse on the button
     */
    @FXML
    private void search_Product(MouseEvent event) {
        //TODO
    }

    /**
     * Open a window allowing you to add a client to the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void add_Client(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Institution-Dashboard-AddClient.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to delete a client to the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_User(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes vous sûr de vouloir supprimer ce client ?");
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Export all clients' data from the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export_ClientData(MouseEvent event) {
        //TODO
    }

    /**
     * Import clients' data
     * @param event the click of the mouse on the button
     */
    @FXML
    private void import_ClientData(MouseEvent event) {
        //TODO
    }

    /**
     * Open a window allowing you to add product to a client of the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void add_Product(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Institution-Dashboard-AddProduct.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to edit a product of a client of the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void edit_Product(MouseEvent event) {
        //TODO
    }

    /**
     * Open a window asking for a delete confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_Product(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes vous sûr de vouloir supprimer ce produit ?");
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Export all clients' products data
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export_ProductsData(MouseEvent event) {
        //TODO
    }

    /**
     * Import clients' products data
     * @param event the click of the mouse on the button
     */
    @FXML
    private void import_ProductsData(MouseEvent event) {
        //TODO
    }
}
