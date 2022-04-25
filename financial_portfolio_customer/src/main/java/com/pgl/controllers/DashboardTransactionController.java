package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.Transaction;
import com.pgl.services.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardTransactionController implements Initializable {

    static ResourceBundle bundle;

    TransactionService transactionService = new TransactionService();

    ObservableList list = FXCollections.observableArrayList();
    List<Transaction> transactionList = new ArrayList<>();

    @FXML
    private ListView<String> transactionListView;
    @FXML
    private Label filters_label;
    @FXML
    private ChoiceBox transactionName;
    @FXML
    private Button Search_btn;
    @FXML
    private Label myTransactions_label;
    @FXML
    private Button export_btn;
    @FXML
    private Button import_btn;
    @FXML
    private Button Consult_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        filters_label.setText(bundle.getString("Filters_label"));
        Search_btn.setText(bundle.getString("Search_btn"));
        myTransactions_label.setText(bundle.getString("MyTransactions_label"));
        export_btn.setText(bundle.getString("Export_btn"));
        import_btn.setText(bundle.getString("Import_btn"));
        Consult_btn.setText(bundle.getString("Consult_btn"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadTransactions();
    }

    /**
     * Load Transactions of the Application Client
     */
    public void loadTransactions() {
        clear();
        transactionList = transactionService.getTransactionsByClient();
        if (transactionList != null && transactionList.size() > 0) {
            transactionList.forEach(transaction -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                String label = dateFormat.format(transaction.getDate()) + "   "
                        + transaction.getTransactionNumber() + " : "
                        + transaction.getType().name() + "   "
                        + (transaction.getBankAccount().getIban() != null
                        ? transaction.getBankAccount().getIban()
                        : transaction.getBankAccount().getNature().name())
                        + "   "
//                        + transaction.getDestinationIBAN() + "   "
                        + transaction.getStatus().name();
                list.add(label);
            });

            transactionListView.getItems().addAll(list);
        }
    }

    /**
     * Clear items in the list
     */
    public void clear() {
        transactionList.clear();
        list.clear();
        transactionListView.getItems().clear();
        transactionService.moveCurrentTransaction();
    }

    /**
     * Select an item from the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void selectedItem(MouseEvent event) {
        String label = transactionListView.getSelectionModel().getSelectedItem();
        int index = transactionListView.getItems().indexOf(label);

        transactionService.setCurrentTransaction(transactionList.get(index));
    }

    /**
     * Show selected item
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_display(MouseEvent event) {
        if (transactionService.getCurrentTransaction() != null) {
            DynamicViews.loadBorderCenter("Client-ViewTransaction");
        } else {
            transactionService.not_selected_error();
        }
    }


    /**
     * Search for Transaction according to the chosen filters
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void search(MouseEvent event) {
    }

    /**
     * Export all transactions' data from the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export_Data(MouseEvent event) {
    }

    /**
     * Import transactions' data
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void import_Data(MouseEvent event) {
    }
}