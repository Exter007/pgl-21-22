package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;

import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.BankAccountService;
import com.pgl.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardBankAccountController implements Initializable {

    UserService userService = new UserService();

    BankAccountService bankAccountService = new BankAccountService();

    ObservableList list = FXCollections.observableArrayList();
    List<BankAccount> accountList = new ArrayList<>();

    @FXML
    private ListView<String> accountListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la suppression du Compte bancaire?");
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
        //TODO
    }

    /**
     * Export all clients' data from the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export_ClientData(MouseEvent event) {
        //TODO
    }

    /**
     * Import clients' data
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void import_ClientData(MouseEvent event) {
        //TODO
    }
}
