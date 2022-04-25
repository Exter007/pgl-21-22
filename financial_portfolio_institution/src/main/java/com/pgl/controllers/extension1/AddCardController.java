package com.pgl.controllers.extension1;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.*;
import com.pgl.utils.Validators;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AddCardController implements Initializable {

    UserService userService = new UserService();
    static ResourceBundle bundle;
    BankAccountService bankAccountService = new BankAccountService();
    CurrentAccountService currentAccountService = new CurrentAccountService();
    SavingAccountService savingAccountService = new SavingAccountService();
    YoungAccountService youngAccountService = new YoungAccountService();
    TermAccountService termAccountService = new TermAccountService();
    ProductHolderService productHolderService = new ProductHolderService();
    FinancialInstitutionService institutionService = new FinancialInstitutionService();
    FinancialProductHolder holder;
    FinancialProductHolder tutor;
    CurrentAccount jointAccount;

    @FXML
    private Label CreatCard_label;
    @FXML
    private Label CardType_label;
    @FXML
    private Label Distribution_label;
    @FXML
    private TextField CourantAccount_field;
    @FXML
    private TextField NationalRegister_field;
    @FXML
    private PasswordField Password_field;
    @FXML
    private ComboBox CardType_combobox;
    @FXML
    private ComboBox Distribution_combobox;
    @FXML
    private Button CreatCard_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        CreatCard_label.setText(bundle.getString("CreatCard_label"));
        CardType_label.setText(bundle.getString("CardType_label"));
        Distribution_label.setText(bundle.getString("Distribution_label"));
        CourantAccount_field.setPromptText(bundle.getString("CourantAccount_field"));
        NationalRegister_field.setPromptText(bundle.getString("NationalRegister_field"));
        Password_field.setPromptText(bundle.getString("Password_field"));
        CreatCard_btn.setText(bundle.getString("CreatCard_btn"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        setText();
        loadComboBoxDatas();
    }

    /**
     * Load interface Combo Box datas
     */
    private void loadComboBoxDatas() {
        CardType_combobox.getItems().add(Card.CARD_TYPE.DEBIT_CARD);
        CardType_combobox.getItems().add(Card.CARD_TYPE.CREDIT_CARD);
        Distribution_combobox.getItems().add(CreditCard.CREDIT_CARD_TYPE.AMERICAN_EXPRESS);
        Distribution_combobox.getItems().add(CreditCard.CREDIT_CARD_TYPE.MASTERCARD);
        Distribution_combobox.getItems().add(CreditCard.CREDIT_CARD_TYPE.VISA);
        Distribution_combobox.getItems().add(DebitCard.DEBIT_CARD_TYPE.BANCONTACT);
        Distribution_combobox.getItems().add(DebitCard.DEBIT_CARD_TYPE.MAESTRO);
    }

    /**
     * Validate the registration of a bank account
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void creat(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(bundle.getString("success13"));
        alert.showAndWait();
    }

}