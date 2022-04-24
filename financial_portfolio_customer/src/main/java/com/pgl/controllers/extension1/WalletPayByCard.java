package com.pgl.controllers.extension1;

import com.pgl.controllers.DashboardController;
import com.pgl.controllers.LoginController;
import com.pgl.controllers.WalletController;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletPayByCard implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;
    static String lang;

    @FXML
    private Label Pay_label;
    @FXML
    private TextField Pay_card_field;
    @FXML
    private TextField Pay_account_to_field;
    @FXML
    private PasswordField Pay_pin_field;
    @FXML
    private Button Pay_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        Pay_label.setText(bundle.getString("Pay_label"));
        Pay_card_field.setPromptText(bundle.getString("Pay_card_field"));
        Pay_account_to_field.setPromptText(bundle.getString("Pay_account_to_field"));
        Pay_pin_field.setPromptText(bundle.getString("Pay_pin_field"));
        Pay_btn.setText(bundle.getString("Pay_btn"));
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
    }

    /**
     * Pay by card
     * @param event the click of the mouse on the button
     */
    @FXML
    private void pay_by_card(MouseEvent event) {

    }
}