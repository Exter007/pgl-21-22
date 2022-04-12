package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label welcome_label;
    @FXML
    private Label filters_label;
    @FXML
    private ChoiceBox productInstitutionName;
    @FXML
    private TextField productCode;
    @FXML
    private DatePicker productCreateDate;
    @FXML
    private Button Search_btn;
    @FXML
    private Label AllFinancialProduct_label;
    @FXML
    private Button export_btn;
    @FXML
    private Button import_btn;
    @FXML
    private BorderPane border_pane;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        filters_label.setText(bundle.getString("Filters_label"));
        productCode.setPromptText(bundle.getString("ProductCode_field"));
        productCreateDate.setPromptText(bundle.getString("ProductCreateDate_field"));
        Search_btn.setText(bundle.getString("Search_btn"));
        AllFinancialProduct_label.setText(bundle.getString("AllFinancialProduct_label"));
        export_btn.setText(bundle.getString("Export_btn"));
        import_btn.setText(bundle.getString("Import_btn"));
        welcome_label.setText(bundle.getString("Welcome_label") + ' ' + userService.getCurrentUser().getName());
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
        DynamicViews.border_pane = border_pane;
    }


    /**
     * Open a window allowing you to modify your personal data
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void edit_profil(ActionEvent event) {
        DynamicViews.loadBorderCenter(border_pane,"Institution-ModifyPersonnalData");
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
                Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Login.fxml"));
                Stage newWindow = new Stage();
                Scene scene = new Scene(root);
                newWindow.setScene(scene);
                GlobalStage.setStage(newWindow);

            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Change the language to French
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageFR(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        setText();
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        setText();
    }

    /**
     * Access the financial products management interface
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void on_product(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Access the Client management interface
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void on_client(ActionEvent event){
        DynamicViews.loadBorderCenter(border_pane,"Institution-Dashboard-Client");
    }

    @FXML
    private void on_accounts(ActionEvent event){
        DynamicViews.loadBorderCenter(border_pane,"Institution-Dashboard-BankAccount");
    }

    /**
     * Access the Insurance management interface
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void on_insurances(ActionEvent event){
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
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
     * Open a window allowing you to add product to a client of the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void add_Product(MouseEvent event) {
        DynamicViews.loadBorderCenter(border_pane,"Institution-Dashboard-AddProduct");
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
