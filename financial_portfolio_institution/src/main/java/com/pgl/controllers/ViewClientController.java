package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.ProductHolderService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewClientController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label nationalRegisterNumber;
    @FXML
    private Label birthDate;

    ProductHolderService productHolderService = new ProductHolderService();

    FinancialProductHolder holder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        loadHolder();
    }

    /**
     * Load Financial Holder data to the interface
     */
    public void loadHolder(){
        holder = productHolderService.getCurrentHolder();
        lastName.setText(holder.getName());
        firstName.setText(holder.getFirstName());
        nationalRegisterNumber.setText(holder.getNationalRegister());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        birthDate.setText(dateFormat.format(holder.getBirthDate()));
    }

    /**
     * Access the interface for modifying the Financial Holder selected
     * @param event the click of the mouse on the button
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
     *  Delete Financial Holder selected
     * @param event
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
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard-Client.fxml"));
                        Stage newWindow = new Stage();
                        Scene scene = new Scene(root);
                        newWindow.setScene(scene);
                        GlobalStage.setStage(newWindow);

                        productHolderService.moveCurrentClient();

                    } catch (IOException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    productHolderService.not_selected_error();
                }
            }
        }else{
            productHolderService.not_selected_error();
        }
    }
}
