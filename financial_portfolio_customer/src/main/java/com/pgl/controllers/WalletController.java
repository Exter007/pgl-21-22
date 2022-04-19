package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.User;
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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label Wallet_label;
    @FXML
    private Label YourFinancialProducts_label;
    @FXML
    private Button Day_btn;
    @FXML
    private Button Week_btn;
    @FXML
    private Button Month_btn;
    @FXML
    private Button Year_btn;
    @FXML
    private Label From_label;
    @FXML
    private Label To_label;
    @FXML
    private Button Graph_btn;
    @FXML
    private Button List_btn;
    @FXML
    private Button Tab_btn;
    @FXML
    private Button Export_btn;

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
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        Wallet_label.setText(bundle.getString("WalletTitle_label"));
        YourFinancialProducts_label.setText(bundle.getString("YourFinancialProducts_label"));
        Day_btn.setText(bundle.getString("Day_btn"));
        Week_btn.setText(bundle.getString("Week_btn"));
        Month_btn.setText(bundle.getString("Month_btn"));
        Year_btn.setText(bundle.getString("Year_btn"));
        From_label.setText(bundle.getString("From_label"));
        To_label.setText(bundle.getString("To_label"));
        Graph_btn.setText(bundle.getString("Graph_btn"));
        List_btn.setText(bundle.getString("List_btn"));
        Tab_btn.setText(bundle.getString("Tab_btn"));
        Export_btn.setText(bundle.getString("Export_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(userService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(userService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        setText();
    }

    /**
     * Back to previous window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Disconnects the user from the application
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void diconnect(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("ConfirmDisconnection_text"));
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
                Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Open a window for make transfer
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_transfer(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Dashboard-Transfer");
    }

    /**
     * Open a window allowing you to request a financial product from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_financialProduct(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-AskFinancialProduct.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window showing the hidden products
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_hideProducts(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Wallet-HideProducts.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window showing notifications of the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_notifications(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-Notifications.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window asking for a hiding confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void hide_product(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-HideConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to request transfer from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_transfer(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-AskTransferConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window asking for a delete confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_financialProduct(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-DeleteConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Changes the time granularity to day
     * @param event the click of the mouse on the button
     */
    @FXML
    private void day(MouseEvent event) {

    }

    /**
     * Changes the time granularity to week
     * @param event the click of the mouse on the button
     */
    @FXML
    private void week(MouseEvent event) {

    }

    /**
     * Changes the time granularity to month
     * @param event the click of the mouse on the button
     */
    @FXML
    private void month(MouseEvent event) {

    }

    /**
     * Changes the time granularity to year
     * @param event the click of the mouse on the button
     */
    @FXML
    private void year(MouseEvent event) {

    }

    /**
     * Displays data in graphical form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void graph(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(false);
        financialProduct_listview.visibleProperty().setValue(false);
        financialProduct_linechart.visibleProperty().setValue(true);
    }

    /**
     * Displays data in list form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void list(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(false);
        financialProduct_listview.visibleProperty().setValue(true);
        financialProduct_linechart.visibleProperty().setValue(false);
    }

    /**
     * Displays data in table form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void tableview(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(true);
        financialProduct_listview.visibleProperty().setValue(false);
        financialProduct_linechart.visibleProperty().setValue(false);
    }

    /**
     * Export all data from the wallet
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export(MouseEvent event) {
        //TODO
    }
}
