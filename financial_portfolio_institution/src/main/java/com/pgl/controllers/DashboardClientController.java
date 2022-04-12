package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.ProductHolderService;
import com.pgl.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardClientController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    ProductHolderService productHolderService = new ProductHolderService();

    ObservableList list = FXCollections.observableArrayList();
    List<FinancialProductHolder> clientList = new ArrayList<>();

    @FXML
    private ListView<String> clientListView;
    @FXML
    private Label filters_label;
    @FXML
    private TextField productClientName;
    @FXML
    private ChoiceBox productInstitutionName;
    @FXML
    private Button Search_btn;
    @FXML
    private Label YourClients_label;
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

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        filters_label.setText(bundle.getString("Filters_label"));
        productClientName.setPromptText(bundle.getString("ProductClientName_field"));
        Search_btn.setText(bundle.getString("Search_btn"));
        YourClients_label.setText(bundle.getString("YourClients_label"));
        export_btn.setText(bundle.getString("Export_btn"));
        import_btn.setText(bundle.getString("Import_btn"));
        Consult_btn.setText(bundle.getString("Consult_btn"));
        Edit_btn.setText(bundle.getString("Edit_btn"));
        Delete_btn.setText(bundle.getString("Delete_btn"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadClients();
    }

    /**
     * Load customers of the financial institution
     */
    public void loadClients(){
        clear();
        clientList = productHolderService.getHolderByInstitution();
        if (clientList != null && clientList.size()>0){
            clientList.forEach(holder -> {
                String label = holder.getNationalRegister() + " : "
                        + holder.getFirstName() + ' ' + holder.getName();
                list.add(label);
            });

            clientListView.getItems().addAll(list);
        }
    }

    /**
     * Clear items in the list
     */
    public void clear(){
        clientList.clear();
        list.clear();
        clientListView.getItems().clear();
        productHolderService.moveCurrentClient();
    }

    /**
     * Open a window allowing you to add a client to the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void add_Client(MouseEvent event) {
        productHolderService.setEdit(false);
        DynamicViews.loadBorderCenter("Institution-Dashboard-AddClient");
    }

    /**
     * Select an item from the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void selectedItem(MouseEvent event){
        String label = clientListView.getSelectionModel().getSelectedItem();
        int index = clientListView.getItems().indexOf(label);

        productHolderService.setCurrentClient(clientList.get(index));
    }

    /**
     * Show selected item
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_display(MouseEvent event){
        if(productHolderService.getCurrentHolder() != null){
            DynamicViews.loadBorderCenter("Institution-ViewClient");
        }else{
            productHolderService.not_selected_error();
        }

    }

    /**
     * Modify a selected item in the list
     * @param event event the click of the mouse on the button
     */
    @FXML
    private void on_edit(MouseEvent event){
        if(productHolderService.getCurrentHolder() != null){
            productHolderService.setEdit(true);
            DynamicViews.loadBorderCenter("Institution-Dashboard-AddClient");
        }else{
            productHolderService.not_selected_error();
        }
    }

    /**
     * Open a window allowing you to delete a client to the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if(productHolderService.getCurrentHolder() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("question3"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = productHolderService.deleteById(String.valueOf(productHolderService.getCurrentHolder().getId()));
                // if successful deletion
                if (status){
                    productHolderService.moveCurrentClient();
                    loadClients();
                }
            }
        }else{
            productHolderService.not_selected_error();
        }
    }

    /**
     * Search for Client according to the chosen filters
     * @param event the click of the mouse on the button
     */
    @FXML
    private void search_Client(MouseEvent event) {
        //TODO
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

}
