package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialProduct;
import com.pgl.models.Transaction;
import com.pgl.models.Wallet;
import com.pgl.services.*;
import com.pgl.utils.Exporter;
import com.pgl.utils.GlobalStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    UserService userService = new UserService();
    ApplicationClientService clientService = new ApplicationClientService();
    FinancialProductService financialProductService = new FinancialProductService();
    TransactionService transactionService = new TransactionService();
    public static ResourceBundle bundle;

    WalletService walletService = new WalletService();
    RequestWalletService requestWalletService = new RequestWalletService();

    ObservableList list = FXCollections.observableArrayList();
    List<Wallet> walletList = new ArrayList<>();


    @FXML
    private Menu language_menu;
    @FXML
    private Menu account_menu;
    @FXML
    private MenuItem edit_item;
    @FXML
    private MenuItem editPassword_menu;
    @FXML
    private Menu home_menu;
    @FXML
    private MenuItem wallet_menu;
    @FXML
    private Menu manage_menu;
    @FXML
    private MenuItem transaction_menu;
    @FXML
    private MenuItem logout_item;
    @FXML
    private MenuItem card_menu;
    @FXML
    private MenuItem mycard_menu;
    @FXML
    private Label welcome;
    @FXML
    private Label YourWallet_label;
    @FXML
    private TableView wallet_tableview;
    @FXML
    private Button consult_btn;
    @FXML
    private Button delete_btn;
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
    private Button Export;
    @FXML
    private DatePicker from_date;
    @FXML
    private DatePicker to_date;
    @FXML
    private ChoiceBox<String> export_format;
    @FXML
    private ChoiceBox<String> data;
    @FXML
    private BorderPane border_pane;
    @FXML
    private ListView<String> walletListView;
    @FXML
    private Button categories_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        language_menu.setText(bundle.getString("Language_menu"));
        account_menu.setText(bundle.getString("Account_menu"));
        edit_item.setText(bundle.getString("EditProfil_menu"));
        editPassword_menu.setText(bundle.getString("EditPassword_menu"));
        home_menu.setText(bundle.getString("Home_menu"));
        wallet_menu.setText(bundle.getString("Wallet_menu"));
        logout_item.setText(bundle.getString("Disconnect_menu"));
        card_menu.setText(bundle.getString("Card_menu"));
        mycard_menu.setText(bundle.getString("Mycard_menu"));
        manage_menu.setText(bundle.getString("Manage_menu"));
        transaction_menu.setText(bundle.getString("Transaction_menu"));
        welcome.setText(bundle.getString("Welcome_label") + ' ' + userService.getCurrentUser().getFirstName());
        YourWallet_label.setText(bundle.getString("YourWallet_label"));
        consult_btn.setText(bundle.getString("Consult_btn"));
        delete_btn.setText(bundle.getString("Delete_btn"));
        Day.setText(bundle.getString("Day_btn"));
        Week.setText(bundle.getString("Week_btn"));
        Month.setText(bundle.getString("Month_btn"));
        Year.setText(bundle.getString("Year_btn"));
        from.setText(bundle.getString("From_label"));
        to.setText(bundle.getString("To_label"));
        export_format.setTooltip(new Tooltip(bundle.getString("Export_format")));
        Export.setText(bundle.getString("Export_btn"));
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
        setChoiceBox();
        DynamicViews.border_pane = border_pane;
        loadWallets();
    }

    /**
     * Set values in ChoiceBox
     */
    private void setChoiceBox() {
        ObservableList<String> formats = FXCollections.observableArrayList(".csv", ".json");
        export_format.setItems(formats);
        formats = FXCollections.observableArrayList(bundle.getString("Transactions"), bundle.getString("Financial_Products"));
        data.setItems(formats);
    }

    /**
     * Load different wallets of client
     */
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

    /**
     * Clear the WalletListView
     */
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
        DynamicViews.loadBorderCenter(border_pane,"Client-ModifyPersonnalData");
    }

    /**
     * Open a window allowing you to modify password
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void edit_password(ActionEvent event) {
        DynamicViews.loadBorderCenter(border_pane,"Client-ModifyPassword");
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
        ApplicationClient client = clientService.updateLanguage("fr");

        if (client != null){
            userService.setCurrentUser(client);
        }
    }

    /**
     * Change the language to English
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void languageEN(ActionEvent event) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        setText();
        ApplicationClient client = clientService.updateLanguage("en");

        if (client != null){
            userService.setCurrentUser(client);
        }
    }

    /**
     * Access the Wallets management interface
     * @param event the click of the mouse on the menu
     */
    @FXML
    private void on_wallet(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open Transaction management interface
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_transaction(ActionEvent event) {
        DynamicViews.loadBorderCenter(border_pane,"Client-Dashboard-Transaction");
    }

    /**
     * Open Card management interface
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_card(ActionEvent event) {
        if(walletService.getCurrentWallet() != null){
            DynamicViews.loadBorderCenter(border_pane, "Client-Wallet-Card");
        }else{
            walletService.not_selected_error();
        }
    }

    /**
     * Open a window allowing you to request access to a wallet from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_wallet(MouseEvent event) {
        DynamicViews.loadBorderCenter(border_pane,"Client-Dashboard-AskWalletToInstitution");
    }

    /**
     * Open a window allowing you to request access to the transfer functionality from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void transfer(MouseEvent event) {
        DynamicViews.loadBorderCenter(border_pane,"Client-Dashboard-Transfer");
    }

    /**
     * Selectionner un element de la liste pour effectuer une action
     * @param event
     */
    @FXML
    private void selectedItem(MouseEvent event){
        String label = walletListView.getSelectionModel().getSelectedItem();
        int index = walletListView.getItems().indexOf(label);

        walletService.setCurrentWallet(walletList.get(index));
    }

    /**
     * Accéder aux details d'un element de la liste
     * @param event
     */
    @FXML
    private void on_display(MouseEvent event){
        if(walletService.getCurrentWallet() != null){
            DynamicViews.loadBorderCenter(border_pane, "Client-Wallet");
        }else{
            walletService.not_selected_error();
        }
    }

    @FXML
    private void on_analyze(MouseEvent event){
    }

    /**
     * Supprimer un element de la liste
     * @param event
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if(walletService.getCurrentWallet() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la suppression du portefeuille ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = walletService.deleteById(walletService.getCurrentWallet().getId().toString());
                boolean statusRequestWallet = requestWalletService.deleteByInstitutionBICAndApplicationID(walletService.getCurrentWallet().getFinancialInstitution().getBIC(), walletService.getCurrentWallet().getApplicationClient().getNationalRegister());
                // if successful deletion
                if (status && statusRequestWallet){
                    walletService.moveCurrentWallet();
                    loadWallets();
                }
            }
        }else{
            walletService.not_selected_error();
        }

    }

    /**
     * Open a window showing notifications of the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_notifications(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Wallet-Notifications");
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
     * Export all data from the client's wallets
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export(MouseEvent event) throws IOException {
        //take the date so each time the user downloads a CSV file, its name is different
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        if(data.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("Export_data"));
            alert.showAndWait();
        }
        else if(data.getValue().equals(bundle.getString("Financial_Products"))) {
            List<FinancialProduct> financialProducts = financialProductService.getFinancialProductsByClient(userService.getCurrentUser());
            Exporter.ActionExport(currentDateTime, financialProducts, bundle, export_format, true);
        }
        else if(data.getValue().equals(bundle.getString("Transactions"))){
            List<Transaction> transactions = transactionService.getTransactionsByClient();
            Exporter.ActionExport(currentDateTime, transactions, bundle, export_format, true);
        }
    }

    public void manage_categories(MouseEvent event) {
        DynamicViews.loadBorderCenter("extension3/Client-Dashboard-Categories");
    }
}
