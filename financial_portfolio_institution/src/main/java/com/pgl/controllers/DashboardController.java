package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;

import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProduct;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.BankAccountService;
import com.pgl.services.FinancialProductService;
import com.pgl.models.User;
import com.pgl.services.UserService;
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
import java.io.IOException;
import java.net.URL;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    static UserService userService = new UserService();
    FinancialProductService productService = new FinancialProductService();
    BankAccountService bankAccountService = new BankAccountService();

    static ResourceBundle bundle;

    ObservableList list = FXCollections.observableArrayList();
    List<FinancialProduct> productList = new ArrayList<>();

    FinancialProduct currentProduct;

    @FXML
    private ListView<String> productListView;
    @FXML
    private Menu Account_menu;
    @FXML
    private MenuItem EditProfil_menu;
    @FXML
    private MenuItem Disconnect_menu;
    @FXML
    private Menu Language_menu;
    @FXML
    private Menu Home_menu;
    @FXML
    private MenuItem FinancialProduct_menu;
    @FXML
    private MenuItem Clients_menu;
    @FXML
    private Menu Products_menu;
    @FXML
    private MenuItem BankAccounts_menu;
    @FXML
    private MenuItem Assurances_menu;
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
        Account_menu.setText(bundle.getString("Account_menu"));
        EditProfil_menu.setText(bundle.getString("EditProfil_menu"));
        Disconnect_menu.setText(bundle.getString("Disconnect_menu"));
        Language_menu.setText(bundle.getString("Language_menu"));
        Home_menu.setText(bundle.getString("Home_menu"));
        FinancialProduct_menu.setText(bundle.getString("FinancialProduct_menu"));
        Clients_menu.setText(bundle.getString("Clients_menu"));
        Products_menu.setText(bundle.getString("Products_menu"));
        BankAccounts_menu.setText(bundle.getString("BankAccounts_menu"));
        Assurances_menu.setText(bundle.getString("Assurances_menu"));
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
        loadFinancialProducts();
    }

    /**
     * Load Financial Products of the financial institution
     */
    public void loadFinancialProducts() {
        clear();
        productList = productService.getFinancialProductsByInstitution();
        if (productList != null && productList.size() > 0) {
            productList.forEach(product -> {
                FinancialProductHolder holder = product.getFinancialProductHolders().get(0);
                String label = holder.getNationalRegister() + " : "
                        + holder.getFirstName() + " " + holder.getName()
                        + "   "
                        + product.getState().name()
                        + "   "
                        + product.getProductType().name();
                list.add(label);
            });

            productListView.getItems().addAll(list);
        }
    }

    /**
     * Clear items in the list
     */
    public void clear() {
        productList.clear();
        list.clear();
        productListView.getItems().clear();
        productService.moveCurrentProduct();
    }

    /**
     * Select an item from the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void selectedItem(MouseEvent event) {
        String label = productListView.getSelectionModel().getSelectedItem();
        int index = productListView.getItems().indexOf(label);

        productService.setCurrentProduct(productList.get(index));
        currentProduct = productList.get(index);
    }

    /**
     * Show selected item
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_display(MouseEvent event) {
        if (currentProduct != null) {
            if (currentProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT)){
                bankAccountService.setCurrentBankAccount((BankAccount) productService.getCurrentProduct());
                DynamicViews.loadBorderCenter("Institution-ViewBankAccount");
            }else if (currentProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.INSURANCE)){
//                insuranceService.setCurrentInsurance((BankAccount) productService.getCurrentProduct());
//                DynamicViews.loadBorderCenter("Institution-ViewInsurance");
            }
        } else {
            productService.not_selected_error();
        }
    }

    /**
     * Access the interface for modifying the financial product selected in the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_edit(MouseEvent event) {
        if (currentProduct != null) {
            if (currentProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT)){
                bankAccountService.setEdit(true);
                bankAccountService.setCurrentBankAccount((BankAccount) productService.getCurrentProduct());
                DynamicViews.loadBorderCenter("Institution-Dashboard-AddBankAccount");
            }else if (currentProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.INSURANCE)){
//                insuranceService.setEdit(true);
//                insuranceService.setCurrentInsurance((BankAccount) productService.getCurrentProduct());
//                DynamicViews.loadBorderCenter("Institution-Dashboard-AddInsurance");
            }
        } else {
            productService.not_selected_error();
        }
    }

    /**
     * Open a window allowing you to delete a Financial Product to the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if (bankAccountService.getCurrentBankAccount() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("question4"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = false;
                if (currentProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT)){
                    status = bankAccountService.deleteById(String.valueOf(currentProduct.getId()));
                }else if (currentProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.INSURANCE)){
//                    status = insuranceService.deleteById(String.valueOf(currentProduct.getId()));
                }
                // if successful deletion
                if (status) {
                    bankAccountService.moveCurrentBankAccount();
                    loadFinancialProducts();
                }
            }
        } else {
            bankAccountService.not_selected_error();
        }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("ConfirmDisconnection_text"));
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
