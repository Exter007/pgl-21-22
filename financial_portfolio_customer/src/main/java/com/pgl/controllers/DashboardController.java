package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.collections.ObservableList;
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
import javax.swing.table.TableColumn;
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
    private Menu menu;
    @FXML
    private Menu menu2;
    @FXML
    private MenuItem menu21;
    @FXML
    private MenuItem menu22;
    @FXML
    private Label welcome;
    @FXML
    private Label YourWallet_label;
    @FXML
    private Label Wallet_label1;
    @FXML
    private Label Wallet_label2;
    @FXML
    private Label Wallet_label3;
    @FXML
    private Label Wallet_label4;
    @FXML
    private Label Wallet_label5;
    @FXML
    private Label Wallet_label6;
    @FXML
    private Label Institution_label1;
    @FXML
    private Label Institution_label2;
    @FXML
    private Label Institution_label3;
    @FXML
    private Label Institution_label4;
    @FXML
    private Label Institution_label5;
    @FXML
    private Label Institution_label6;
    @FXML
    private Label FinancialProduct_label1;
    @FXML
    private Label FinancialProduct_label2;
    @FXML
    private Label FinancialProduct_label3;
    @FXML
    private Label FinancialProduct_label4;
    @FXML
    private Label FinancialProduct_label5;
    @FXML
    private Label FinancialProduct_label6;
    @FXML
    private TableView wallet_tableview;
    @FXML
    private Button Day;
    @FXML
    private Button Week;
    @FXML
    private Button Month;
    @FXML
    private Button Year;
    @FXML
    private Label from;
    @FXML
    private Label to;
    @FXML
    private Button Graph;
    @FXML
    private Button List;
    @FXML
    private Button Tab;
    @FXML
    private Button Export;
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
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        menu.setText(bundle.getString("Langue_menu"));
        menu2.setText(bundle.getString("Compte_menu"));
        menu21.setText(bundle.getString("EditProfil_menu"));
        menu22.setText(bundle.getString("Disconnect_menu"));
        welcome.setText(bundle.getString("Welcome"));
        YourWallet_label.setText(bundle.getString("YourWallet"));
        Wallet_label1.setText(bundle.getString("Wallet"));
        Wallet_label2.setText(bundle.getString("Wallet"));
        Wallet_label3.setText(bundle.getString("Wallet"));
        Wallet_label4.setText(bundle.getString("Wallet"));
        Wallet_label5.setText(bundle.getString("Wallet"));
        Wallet_label6.setText(bundle.getString("Wallet"));
        Institution_label1.setText(bundle.getString("Institution"));
        Institution_label2.setText(bundle.getString("Institution"));
        Institution_label3.setText(bundle.getString("Institution"));
        Institution_label4.setText(bundle.getString("Institution"));
        Institution_label5.setText(bundle.getString("Institution"));
        Institution_label6.setText(bundle.getString("Institution"));
        FinancialProduct_label1.setText(bundle.getString("FinancialProduct_number"));
        FinancialProduct_label2.setText(bundle.getString("FinancialProduct_number"));
        FinancialProduct_label3.setText(bundle.getString("FinancialProduct_number"));
        FinancialProduct_label4.setText(bundle.getString("FinancialProduct_number"));
        FinancialProduct_label5.setText(bundle.getString("FinancialProduct_number"));
        FinancialProduct_label6.setText(bundle.getString("FinancialProduct_number"));
        //TODO : Les nom des collums du tableau
        Day.setText(bundle.getString("Day"));
        Week.setText(bundle.getString("Week"));
        Month.setText(bundle.getString("Month"));
        Year.setText(bundle.getString("Year"));
        from.setText(bundle.getString("From"));
        to.setText(bundle.getString("To"));
        Graph.setText(bundle.getString("Graph"));
        List.setText(bundle.getString("List"));
        Tab.setText(bundle.getString("Tab"));
        Export.setText(bundle.getString("Export"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO: Récupérer la préférence de langue dans la BDD
        bundle = LoginController.bundle;
        setText();
        loadUserConnected();
    }

    /**
     * Change the user name in the welcome label
     */
    public void loadUserConnected(){
        welcome.setText(UserService.getCurrentUser().getFirstName());
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("ConfirmDisconnection"));
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
        //TODO: sauvegarder dans la BDD

        bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        setText();
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        //TODO: sauvegarder dans la BDD

        bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        setText();
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
