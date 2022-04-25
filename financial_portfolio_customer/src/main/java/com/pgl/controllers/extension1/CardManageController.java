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

public class CardManageController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label CardNumber_label;
    @FXML
    private Label ValidityDate_label;
    @FXML
    private Button BlockCard_btn;
    @FXML
    private Button DeleteCard_btn;
    @FXML
    private Button ActiveCard_btn;
    @FXML
    private Label NewPIN_label;
    @FXML
    private TextField NewPIN_field;
    @FXML
    private Label International_label;
    @FXML
    private ToggleButton InternationalActive_btn;
    @FXML
    private ToggleButton InternationalDisable_btn;
    @FXML
    private Label Negative_label;
    @FXML
    private ToggleButton NegativeActive_btn;
    @FXML
    private ToggleButton NegativeDisable_btn;
    @FXML
    private Label PayPerDay_label;
    @FXML
    private TextField PayPerDay_field;
    @FXML
    private Label PayPerWeek_label;
    @FXML
    private TextField PayPerWeek_field;
    @FXML
    private Label CreditPerMonth_label;
    @FXML
    private TextField CreditPerMonth_field;
    @FXML
    private Label TempCreditPerMonth_label;
    @FXML
    private TextField TempCreditPerMonth_field;
    @FXML
    private PasswordField PINCode_field;
    @FXML
    private Button Valid_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        CardNumber_label.setText(bundle.getString("CardNumber_label"));
        ValidityDate_label.setText(bundle.getString("ValidityDate_label"));
        BlockCard_btn.setText(bundle.getString("BlockCard_btn"));
        DeleteCard_btn.setText(bundle.getString("DeleteCard_btn"));
        ActiveCard_btn.setText(bundle.getString("ActiveCard_btn"));
        NewPIN_label.setText(bundle.getString("NewPIN_label"));
        NewPIN_field.setPromptText(bundle.getString("NewPIN_field"));
        International_label.setText(bundle.getString("International_label"));
        InternationalActive_btn.setText(bundle.getString("InternationalActive_btn"));
        InternationalDisable_btn.setText(bundle.getString("InternationalDisable_btn"));
        Negative_label.setText(bundle.getString("Negative_label"));
        NegativeActive_btn.setText(bundle.getString("NegativeActive_btn"));
        NegativeDisable_btn.setText(bundle.getString("NegativeDisable_btn"));
        PayPerDay_label.setText(bundle.getString("PayPerDay_label"));
        PayPerDay_field.setPromptText(bundle.getString("PayPerDay_field"));
        PayPerWeek_label.setText(bundle.getString("PayPerWeek_label"));
        PayPerWeek_field.setPromptText(bundle.getString("PayPerWeek_field"));
        CreditPerMonth_label.setText(bundle.getString("CreditPerMonth_label"));
        CreditPerMonth_field.setPromptText(bundle.getString("CreditPerMonth_field"));
        TempCreditPerMonth_label.setText(bundle.getString("TempCreditPerMonth_label"));
        TempCreditPerMonth_field.setPromptText(bundle.getString("TempCreditPerMonth_field"));
        PINCode_field.setPromptText(bundle.getString("PINCode_field"));
        Valid_btn.setText(bundle.getString("Valid_btn"));
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

    @FXML
    private void blockCard(MouseEvent event) {

    }

    @FXML
    private void deleteCard(MouseEvent event) {

    }

    @FXML
    private void activeCard(MouseEvent event) {

    }

    @FXML
    private void internationalActive(MouseEvent event) {

    }

    @FXML
    private void internationalDisable(MouseEvent event) {

    }

    @FXML
    private void negativeActive(MouseEvent event) {

    }

    @FXML
    private void negativeDisable(MouseEvent event) {

    }

    @FXML
    private void valid(MouseEvent event) {

    }
}