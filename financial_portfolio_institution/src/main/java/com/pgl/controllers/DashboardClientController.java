package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.ProductHolderService;
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

public class DashboardClientController implements Initializable {

    UserService userService = new UserService();

    ProductHolderService productHolderService = new ProductHolderService();

    ObservableList list = FXCollections.observableArrayList();
    List<FinancialProductHolder> clientList = new ArrayList<>();

    @FXML
    private ListView<String> clientListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    @FXML
    private void selectedItem(MouseEvent event){
        String label = clientListView.getSelectionModel().getSelectedItem();
        int index = clientListView.getItems().indexOf(label);

        productHolderService.setCurrentClient(clientList.get(index));
    }

    @FXML
    private void on_display(MouseEvent event){
        if(productHolderService.getCurrentHolder() != null){
            DynamicViews.loadBorderCenter("Institution-ViewClient");
        }else{
            productHolderService.not_selected_error();
        }

    }

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la suppression du client?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = productHolderService.deleteById(productHolderService.getCurrentHolder().getId());
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
