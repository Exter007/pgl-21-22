package com.pgl.controllers;

import com.pgl.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAddProductController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label AddProduct_label;
    @FXML
    private TextField customerNationalRegisterNumber;
    @FXML
    private ComboBox productType;
    @FXML
    private PasswordField password;
    @FXML
    private Button CreatProduct_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        AddProduct_label.setText(bundle.getString("AddProduct_label"));
        customerNationalRegisterNumber.setPromptText(bundle.getString("ClientNationalRegisterNumber_field"));
        password.setPromptText(bundle.getString("Password_field"));
        CreatProduct_btn.setText(bundle.getString("CreatProduct_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        setText();
    }

    /**
     * Creat and add a product to a client's wallet
     * @param event the click of the mouse on the button
     */
    @FXML
    private void create_product(ActionEvent event) {
        //TODO
    }
}
