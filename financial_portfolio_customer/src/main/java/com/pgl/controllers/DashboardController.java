package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.User;
import com.pgl.models.Wallet;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import com.pgl.utils.GlobalStage;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.swing.table.TableColumn;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    WalletService walletService = new WalletService();

    ObservableList list = FXCollections.observableArrayList();
    List<Wallet> walletList = new ArrayList<>();


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

    @FXML
    private BorderPane border_pane;

    @FXML
    private ListView<String> walletListView;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        menu.setText(bundle.getString("Language_menu"));
        menu2.setText(bundle.getString("Account_menu"));
        menu21.setText(bundle.getString("EditProfil_menu"));
        menu22.setText(bundle.getString("Disconnect_menu"));
        welcome.setText(bundle.getString("Welcome_label") + ' ' + UserService.getCurrentUser().getFirstName());
        YourWallet_label.setText(bundle.getString("YourWallet_label"));
//        Wallet_label1.setText(bundle.getString("Wallet_label"));
//        Wallet_label2.setText(bundle.getString("Wallet_label"));
//        Wallet_label3.setText(bundle.getString("Wallet_label"));
//        Wallet_label4.setText(bundle.getString("Wallet_label"));
//        Wallet_label5.setText(bundle.getString("Wallet_label"));
//        Wallet_label6.setText(bundle.getString("Wallet_label"));
//        Institution_label1.setText(bundle.getString("Institution_label"));
//        Institution_label2.setText(bundle.getString("Institution_label"));
//        Institution_label3.setText(bundle.getString("Institution_label"));
//        Institution_label4.setText(bundle.getString("Institution_label"));
//        Institution_label5.setText(bundle.getString("Institution_label"));
//        Institution_label6.setText(bundle.getString("Institution_label"));
//        FinancialProduct_label1.setText(bundle.getString("FinancialProductNumber_label"));
//        FinancialProduct_label2.setText(bundle.getString("FinancialProductNumber_label"));
//        FinancialProduct_label3.setText(bundle.getString("FinancialProductNumber_label"));
//        FinancialProduct_label4.setText(bundle.getString("FinancialProductNumber_label"));
//        FinancialProduct_label5.setText(bundle.getString("FinancialProductNumber_label"));
//        FinancialProduct_label6.setText(bundle.getString("FinancialProductNumber_label"));
        //TODO : Les nom des collums du tableau
        Day.setText(bundle.getString("Day_btn"));
        Week.setText(bundle.getString("Week_btn"));
        Month.setText(bundle.getString("Month_btn"));
        Year.setText(bundle.getString("Year_btn"));
        from.setText(bundle.getString("From_label"));
        to.setText(bundle.getString("To_label"));
        Graph.setText(bundle.getString("Graph_btn"));
        List.setText(bundle.getString("List_btn"));
        Tab.setText(bundle.getString("Tab_btn"));
        Export.setText(bundle.getString("Export_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(UserService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(UserService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        setText();

        DynamicViews.border_pane = border_pane;
        loadWallets();
    }

    public void loadWallets(){
        clear();
        walletList = walletService.getWalletsByClient();
        if (walletList != null && walletList.size()>0){
            walletList.forEach(wallet -> {
                String label = wallet.getFinancialInstitution().getBIC() + " : "
                        + wallet.getFinancialInstitution().getName();
                list.add(label);
            });

            walletListView.getItems().addAll(list);
        }
    }

    public void clear(){
        walletList.clear();
        list.clear();
        walletListView.getItems().clear();
        walletService.moveCurrentWallet();
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
    private void disconnect(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("ConfirmDisconnection_text"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            userService.logout();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
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
        bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        setText();
        User user = new User();
        user.setLanguage("fr");
        user.setLogin(UserService.getCurrentUser().getLogin());
        boolean result = userService.editUser(user);
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        setText();
        User user = new User();
        user.setLanguage("en");
        user.setLogin(UserService.getCurrentUser().getLogin());
        boolean result = userService.editUser(user);
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
     * Selectionner un element de la liste pour effectuer une action
     * @param event
     */
    @FXML
    private void selectedItem(MouseEvent event){
        String label = walletListView.getSelectionModel().getSelectedItem();
        int index = walletListView.getItems().indexOf(label);
        Long id = walletList.get(index).getId();

        Wallet wallet = walletService.findById(id);

        WalletService.setCurrentWallet(wallet);;
    }

    /**
     * Acceder aux details d'un element de la liste
     * @param event
     */
    @FXML
    private void on_display(MouseEvent event){
        if(WalletService.getCurrentWallet() != null){
            DynamicViews.loadBorderCenter(border_pane, "Client-Wallet");
        }else{
            walletService.not_selected_error();
        }
    }

    /**
     * Supprimer un element de la liste
     * @param event
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if(WalletService.getCurrentWallet() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la suppression du portefeuille ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = walletService.deleteById(WalletService.getCurrentWallet().getId());
                // if successful deletion
                if (status){
                    walletService.moveCurrentWallet();
                    loadWallets();
                }
            }
        }else{
            walletService.not_selected_error();
        }

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
