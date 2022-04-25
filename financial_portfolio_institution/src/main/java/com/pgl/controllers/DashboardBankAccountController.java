package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;

import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProduct;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.BankAccountService;

import com.pgl.services.FinancialProductService;
import com.pgl.utils.Porter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardBankAccountController implements Initializable {

    static ResourceBundle bundle;

    BankAccountService bankAccountService = new BankAccountService();

    ObservableList list = FXCollections.observableArrayList();
    List<BankAccount> accountList = new ArrayList<>();

    @FXML
    private ListView<String> accountListView;
    @FXML
    private Label filters_label;
    @FXML
    private ChoiceBox productInstitutionName;
    @FXML
    private Button Search_btn;
    @FXML
    private Label BankAccounts_label;
    @FXML
    private Button export_btn;
    @FXML
    private Button import_btn;
    @FXML
    private Button Consult_btn;
    @FXML
    private Button Edit_btn;
    @FXML
    private Button Delete_btn;
    @FXML
    private ChoiceBox<String> export_format;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        filters_label.setText(bundle.getString("Filters_label"));
        Search_btn.setText(bundle.getString("Search_btn"));
        BankAccounts_label.setText(bundle.getString("BankAccounts_label"));
        export_btn.setText(bundle.getString("Export_btn"));
        import_btn.setText(bundle.getString("Import_btn"));
        Consult_btn.setText(bundle.getString("Consult_btn"));
        Edit_btn.setText(bundle.getString("Edit_btn"));
        Delete_btn.setText(bundle.getString("Delete_btn"));
        ObservableList<String> formats = FXCollections.observableArrayList(".csv", ".json");
        export_format.setItems(formats);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadBanksAccount();
    }

    /**
     * Load Banks Account of the financial institution
     */
    public void loadBanksAccount() {
        clear();
        accountList = bankAccountService.getBankAccountsByInstitution();
        if (accountList != null && accountList.size() > 0) {
            accountList.forEach(account -> {
                FinancialProductHolder holder = account.getFinancialProductHolders().get(0);
                String label = holder.getNationalRegister() + " : "
                        + holder.getFirstName() + " " + holder.getName()
                        + "   "
                        + ( (account.getIban()) != null ? account.getIban() : "" )
                        + ( (account.getJointIban()) != null ? account.getJointIban().getIban() : "" )
                        + "   "
                        + account.getNature().name() + "   "
                        + account.getAccountType().name() + "   "
                        + account.getState().name();
                list.add(label);
            });

            accountListView.getItems().addAll(list);
        }
    }

    /**
     * Clear items in the list
     */
    public void clear() {
        accountList.clear();
        list.clear();
        accountListView.getItems().clear();
        bankAccountService.moveCurrentBankAccount();
    }

    /**
     * Open a window allowing you to add a Bank account to the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_add(MouseEvent event) {
        bankAccountService.setEdit(false);
        DynamicViews.loadBorderCenter("Institution-Dashboard-AddBankAccount");
    }

    /**
     * Select an item from the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void selectedItem(MouseEvent event) {
        String label = accountListView.getSelectionModel().getSelectedItem();
        int index = accountListView.getItems().indexOf(label);

        bankAccountService.setCurrentBankAccount(accountList.get(index));
    }

    /**
     * Show selected item
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_display(MouseEvent event) {
        if (bankAccountService.getCurrentBankAccount() != null) {
            DynamicViews.loadBorderCenter("Institution-ViewBankAccount");
        } else {
            bankAccountService.not_selected_error();
        }

    }

    /**
     * Access the interface for modifying the bank account selected in the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_edit(MouseEvent event) {
        if (bankAccountService.getCurrentBankAccount() != null) {
            bankAccountService.setEdit(true);
            DynamicViews.loadBorderCenter("Institution-Dashboard-AddBankAccount");
        } else {
            bankAccountService.not_selected_error();
        }
    }

    /**
     * Open a window allowing you to delete a Bank Account to the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if (bankAccountService.getCurrentBankAccount() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("question4"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = bankAccountService.deleteById(String.valueOf(bankAccountService.getCurrentBankAccount().getId()));
                // if successful deletion
                if (status) {
                    bankAccountService.moveCurrentBankAccount();
                    loadBanksAccount();
                }
            }
        } else {
            bankAccountService.not_selected_error();
        }
    }

    /**
     * Search for Client according to the chosen filters
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void search_Client(MouseEvent event) {
    }

    /**
     * Export all clients' data from the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export(MouseEvent event) {
        //take the date so each time the user downloads a CSV file, its name is different
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByInstitution();
        Porter.ActionExport(currentDateTime, bankAccounts, bundle, export_format);
    }

    /**
     * Import clients' data
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void import_ClientData(MouseEvent event) {
    }
}
