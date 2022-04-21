package com.pgl.controllers;

import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletHideProductsController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label HideProductTitle;
    @FXML
    private Label HideProductSubTitle;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        HideProductTitle.setText(bundle.getString("HideProductTitle_label"));
        HideProductSubTitle.setText(bundle.getString("HideProductSubTitle_label"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = WalletController.bundle;
        setText();
    }

    /**
     * Back to previous window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Wallet.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(WalletHideProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Redisplay the hidden product
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_Product(MouseEvent event) {
        //TODO
    }

    /**
     * Open a window asking for a delete confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_Product(MouseEvent event) {

        //TODO

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-DeleteConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletHideProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
