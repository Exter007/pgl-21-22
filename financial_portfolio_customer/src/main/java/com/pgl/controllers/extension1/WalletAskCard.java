package com.pgl.controllers.extension1;

import com.pgl.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class WalletAskCard implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;
    static String lang;

    @FXML
    private Menu menu;
    @FXML
    private TextField CardNumber;
    @FXML
    private PasswordField PINCode;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink login_with_id_link;
    @FXML
    private Hyperlink create_account_link;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        /*menu.setText(bundle.getString("Language_menu"));
        CardNumber.setPromptText(bundle.getString("CardNumber_field"));
        PINCode.setPromptText(bundle.getString("PINCode_field"));
        login_btn.setText(bundle.getString("Connexion_btn"));
        login_with_id_link.setText(bundle.getString("LoginWithID_link"));
        create_account_link.setText(bundle.getString("CreateAccount_link"));*/
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
     * Connect the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void login(MouseEvent event) {

    }
}