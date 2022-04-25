package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.*;
import com.pgl.utils.Validators;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddBankAccountController implements Initializable {

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
    private Label AddBankAccount_label;
    @FXML
    private TextField clientRegisterNumber;
    @FXML
    private ComboBox accountNatureComboBox;
    @FXML
    private ComboBox accountTypeComboBox;
    @FXML
    private TextField monthlyFee;
    @FXML
    private TextField annualYield;
    @FXML
    private TextField ibanLinked;
    @FXML
    private TextField interest;
    @FXML
    private TextField loyaltyBonus;
    @FXML
    private TextField tutorRegisterNumber;
    @FXML
    private TextField maxTransactionAmount;
    @FXML
    private DatePicker deadline;
    @FXML
    private TextField penalty;
    @FXML
    private PasswordField password;
    @FXML
    private Button Valid_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        AddBankAccount_label.setText(bundle.getString("AddBankAccount_label"));
        clientRegisterNumber.setPromptText(bundle.getString("ClientNationalRegisterNumber_field"));
        monthlyFee.setPromptText(bundle.getString("MontlyFee_field"));
        annualYield.setPromptText(bundle.getString("AnnualYeld_field"));
        ibanLinked.setPromptText(bundle.getString("IBAN_field"));
        interest.setPromptText(bundle.getString("Interest_field"));
        loyaltyBonus.setPromptText(bundle.getString("LoyaltyBonus_field"));
        tutorRegisterNumber.setPromptText(bundle.getString("TutorRegisterNumber_field"));
        maxTransactionAmount.setPromptText(bundle.getString("MaxTransaction_field"));
        deadline.setPromptText(bundle.getString("Deadline_field"));
        penalty.setPromptText(bundle.getString("Penalty_field"));
        password.setPromptText(bundle.getString("Password_field"));
        Valid_btn.setText(bundle.getString("Valid_btn"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadComboBoxDatas();
        BankAccount bankAccount = bankAccountService.getCurrentBankAccount();

        // Whether to load the interface in edit mode
        if(bankAccountService.isEdit()){
            accountNatureComboBox.getSelectionModel().select(bankAccount.getNature().name());
            accountNatureComboBox.setDisable(true);
            clientRegisterNumber.setDisable(true);
            if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT)){
                setCurrentAccount();
            }else if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.SAVING_ACCOUNT)){
                setSavingAccount();
            }else if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT)){
                setYoungAccount();
            }else if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.TERM_ACCOUNT)){
                setTermAccount();
            }
        }
    }

    /**
     * Load interface Combo Box datas
     */
    private void loadComboBoxDatas(){
        List<String> accountNatureList = new ArrayList<>();
        accountNatureList.add(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT.name());
        accountNatureList.add(BankAccount.ACCOUNT_NATURE.SAVING_ACCOUNT.name());
        accountNatureList.add(BankAccount.ACCOUNT_NATURE.TERM_ACCOUNT.name());
        accountNatureList.add(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT.name());
        accountNatureComboBox.getItems().addAll(accountNatureList);

        List<String> accountTypeList = new ArrayList<>();
        accountTypeList.add(BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT.name());
        accountTypeList.add(BankAccount.ACCOUNT_TYPE.JOINT_ACCOUNT.name());
        accountTypeList.add(BankAccount.ACCOUNT_TYPE.ACCOUNT_UNDIVIDED.name());
        accountTypeComboBox.getItems().addAll(accountTypeList);

        accountNatureComboBox.getSelectionModel().selectFirst();
        show_current_account();
        accountTypeComboBox.getSelectionModel().selectFirst();

        accountNatureComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT.name())) {
                show_current_account();
                accountTypeComboBox.setDisable(false);
            }else if (newValue.equals(BankAccount.ACCOUNT_NATURE.SAVING_ACCOUNT.name())){
                show_saving_account();
                accountTypeComboBox.setDisable(false);
            }else if (newValue.equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT.name())){
                show_young_account();
                accountTypeComboBox.setValue(BankAccount.ACCOUNT_TYPE.JOINT_ACCOUNT.name());
                accountTypeComboBox.setDisable(true);
            }else if (newValue.equals(BankAccount.ACCOUNT_NATURE.TERM_ACCOUNT.name())){
                show_term_account();
                accountTypeComboBox.setDisable(false);
            }
        });

    }

    /**
     * Load Current Bank Account data on the interface for edit mode
     */
    private void setCurrentAccount(){
        show_current_account();
        CurrentAccount currentAccount = (CurrentAccount) bankAccountService.getCurrentBankAccount();
        clientRegisterNumber.setText(currentAccount.getFinancialProductHolders().get(0).getNationalRegister());
        accountTypeComboBox.getSelectionModel().select(currentAccount.getAccountType().name());
        ibanLinked.setText(currentAccount.getIban());
        monthlyFee.setText(String.valueOf(currentAccount.getMonthlyFee()));
    }

    /**
     * Load Savings Bank Account data on the interface for edit mode
     */
    private void setSavingAccount(){
        show_saving_account();
        SavingsAccount savingsAccount = (SavingsAccount) bankAccountService.getCurrentBankAccount();
        clientRegisterNumber.setText(savingsAccount.getFinancialProductHolders().get(0).getNationalRegister());
        accountTypeComboBox.getSelectionModel().select(savingsAccount.getAccountType().name());
        ibanLinked.setText(savingsAccount.getJointIban().getIban());
        monthlyFee.setText(String.valueOf(savingsAccount.getMonthlyFee()));
        interest.setText(String.valueOf(savingsAccount.getAnnualInterest()));
        loyaltyBonus.setText(String.valueOf(savingsAccount.getLoyaltyBonus()));
    }

    /**
     * Load Young Bank Account data on the interface for edit mode
     */
    private void setYoungAccount(){
        show_young_account();
        YoungAccount youngAccount = (YoungAccount) bankAccountService.getCurrentBankAccount();
        clientRegisterNumber.setText(youngAccount.getFinancialProductHolders().get(0).getNationalRegister());
        accountTypeComboBox.getSelectionModel().select(youngAccount.getAccountType().name());
        ibanLinked.setText(youngAccount.getIban());
        monthlyFee.setText(String.valueOf(youngAccount.getMonthlyFee()));
        tutorRegisterNumber.setText(youngAccount.getFinancialProductHolders().get(1).getNationalRegister());
        maxTransactionAmount.setText(String.valueOf(youngAccount.getMaxTransactionAmount()));
    }

    /**
     * Load Term Bank Account data on the interface for edit mode
     */
    private void setTermAccount(){
        show_term_account();
        TermAccount termAccount = (TermAccount) bankAccountService.getCurrentBankAccount();
        clientRegisterNumber.setText(termAccount.getFinancialProductHolders().get(0).getNationalRegister());
        accountTypeComboBox.getSelectionModel().select(termAccount.getAccountType().name());
        ibanLinked.setText(termAccount.getJointIban().getIban());
        monthlyFee.setText(String.valueOf(termAccount.getMonthlyFee()));
        annualYield.setText(String.valueOf(termAccount.getAnnualYield()));
        penalty.setText(String.valueOf(termAccount.getPenalty()));
        deadline.setValue(termAccount.getMaximumDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * Display the fields necessary for the registration of a current bank account
     */
    private void show_current_account(){
        ibanLinked.setVisible(true);
        monthlyFee.setVisible(true);
        annualYield.setVisible(false);
        interest.setVisible(false);
        loyaltyBonus.setVisible(false);
        tutorRegisterNumber.setVisible(false);
        maxTransactionAmount.setVisible(false);
        deadline.setVisible(false);
        penalty.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a savings bank account
     */
    private void show_saving_account(){
        ibanLinked.setVisible(true);
        monthlyFee.setVisible(true);
        annualYield.setVisible(false);
        interest.setVisible(true);
        loyaltyBonus.setVisible(true);
        tutorRegisterNumber.setVisible(false);
        maxTransactionAmount.setVisible(false);
        deadline.setVisible(false);
        penalty.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a young bank account
     */
    private void show_young_account(){
        ibanLinked.setVisible(true);
        monthlyFee.setVisible(true);
        annualYield.setVisible(false);
        interest.setVisible(false);
        loyaltyBonus.setVisible(false);
        tutorRegisterNumber.setVisible(true);
        maxTransactionAmount.setVisible(true);
        deadline.setVisible(false);
        penalty.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a term bank account
     */
    private void show_term_account(){
        ibanLinked.setVisible(true);
        monthlyFee.setVisible(true);
        annualYield.setVisible(true);
        interest.setVisible(false);
        loyaltyBonus.setVisible(false);
        tutorRegisterNumber.setVisible(false);
        maxTransactionAmount.setVisible(false);
        deadline.setVisible(true);
        penalty.setVisible(true);
    }

    /**
     * Validate the registration of a bank account
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if (clientRegisterNumber.getText().isEmpty() || ibanLinked.getText().isEmpty() || password.getText().isEmpty()){
            show_error();
        }else if(!Validators.check_nationalRegisterNumber(clientRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error11"));
            alert.showAndWait();
        }else if(!Validators.check_IBAN(ibanLinked.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error13"));
            alert.showAndWait();
        }else if(!Validators.check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error5"));
            alert.showAndWait();

        } else if(!institutionService.checkPassword(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        }else if(( holder = productHolderService
                .getHolderByInstitutionAndRegisterNum(clientRegisterNumber.getText())) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error14"));
            alert.showAndWait();
        }else if (accountNatureComboBox.getSelectionModel().getSelectedItem().equals(
                BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT.name())){

            if( !bankAccountService.isEdit() && (jointAccount = (CurrentAccount) bankAccountService
                    .getBankAccountsByIBAN(ibanLinked.getText())) != null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error15"));
                alert.showAndWait();
            }else if ( monthlyFee.getText().isEmpty()){
               show_error();
            }else{
                CurrentAccount result = currentAccountService.save(buildCurrentAccount());
                if (result != null){
                    loadBankDashboard();
                }
            }
        }else if (accountNatureComboBox.getSelectionModel().getSelectedItem().equals(
                BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT.name())){
            if( !bankAccountService.isEdit() && (bankAccountService
                    .getBankAccountsByIBAN(ibanLinked.getText())) != null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error15"));
                alert.showAndWait();
            }else if (monthlyFee.getText().isEmpty() || tutorRegisterNumber.getText().isEmpty() ||
                    maxTransactionAmount.getText().isEmpty()){
                show_error();
            }else if(!Validators.check_nationalRegisterNumber(tutorRegisterNumber.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error11"));
                alert.showAndWait();
            } else if((tutor = productHolderService
                    .getHolderByInstitutionAndRegisterNum(tutorRegisterNumber.getText())) == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(bundle.getString("error17"));
                alert.showAndWait();
            }else{
                YoungAccount result = youngAccountService.save(buildYoungAccount());
                if (result != null){
                    loadBankDashboard();
                }
            }
        } else if((jointAccount = (CurrentAccount) bankAccountService
                .getBankAccountsByIBAN(ibanLinked.getText())) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error16"));
            alert.showAndWait();
        } else if (accountNatureComboBox.getSelectionModel().getSelectedItem().equals(
                BankAccount.ACCOUNT_NATURE.SAVING_ACCOUNT.name())){
            if (monthlyFee.getText().isEmpty() || interest.getText().isEmpty() ||
                    loyaltyBonus.getText().isEmpty() || ibanLinked.getText().isEmpty()){
                show_error();
            }else {
                SavingsAccount result = savingAccountService.save(buildSavingAccount());
                if (result != null){
                    loadBankDashboard();
                }
            }
        }else if (accountNatureComboBox.getSelectionModel().getSelectedItem().equals(
                BankAccount.ACCOUNT_NATURE.TERM_ACCOUNT.name())){
            if (monthlyFee.getText().isEmpty() || annualYield.getText().isEmpty() ||
                    deadline.getValue() == null || penalty.getText().isEmpty()){
               show_error();
            }else{
                TermAccount result = termAccountService.save(buildTermAccount());
                if (result != null){
                    loadBankDashboard();
                }
            }
        }
    }

    /**
     * Build a current bank account
     * @return
     */
    private CurrentAccount buildCurrentAccount(){
        CurrentAccount currentAccount;
        // If edit mode
        if(bankAccountService.isEdit()){
            currentAccount = (CurrentAccount) bankAccountService.getCurrentBankAccount();
            currentAccount.setModificationDate(new Date());
        }else {
            currentAccount = new CurrentAccount();
            currentAccount.setPin_code(generatePinCode());
        }

        buildDefault(currentAccount);
        currentAccount.setIban(ibanLinked.getText());

        return currentAccount;
    }

    /**
     * Build a current savings account
     * @return
     */
    private SavingsAccount buildSavingAccount(){
        SavingsAccount savingsAccount;

        // If edit mode
        if(bankAccountService.isEdit()){
            savingsAccount = (SavingsAccount) bankAccountService.getCurrentBankAccount();
            savingsAccount.setModificationDate(new Date());
        }else {
            savingsAccount = new SavingsAccount();
            LocalDate localDate = LocalDate.now();
            savingsAccount.setLoyaltyDate(java.sql.Date.valueOf(localDate.plusYears(1)));
        }

        buildDefault(savingsAccount);

        float annualYield = convertToFloat(interest.getText()) + convertToFloat(loyaltyBonus.getText());

        savingsAccount.setJointIban(jointAccount);
        savingsAccount.setAnnualInterest(convertToFloat(interest.getText()));
        savingsAccount.setLoyaltyBonus(convertToFloat(loyaltyBonus.getText()));
        savingsAccount.setAnnualYield(annualYield);

        return savingsAccount;
    }

    /**
     * Build a current young account
     * @return
     */
    private YoungAccount buildYoungAccount(){
        YoungAccount youngAccount;

        // If edit mode
        if(bankAccountService.isEdit()){
            youngAccount = (YoungAccount) bankAccountService.getCurrentBankAccount();
            youngAccount.setModificationDate(new Date());
        }else {
            youngAccount = new YoungAccount();
            youngAccount.setPin_code(generatePinCode());
        }
        buildDefault(youngAccount);

        youngAccount.setIban(ibanLinked.getText());
        youngAccount.getFinancialProductHolders().add(1,tutor);
        youngAccount.setMaxTransactionAmount(convertToFloat(maxTransactionAmount.getText()));

        return youngAccount;
    }

    /**
     * Build a term bank account
     * @return
     */
    private TermAccount buildTermAccount(){
        TermAccount termAccount;

        // If edit mode
        if(bankAccountService.isEdit()){
            termAccount = (TermAccount) bankAccountService.getCurrentBankAccount();
            termAccount.setModificationDate(new Date());
        }else {
            termAccount = new TermAccount();
        }
        buildDefault(termAccount);

        termAccount.setJointIban(jointAccount);
        termAccount.setAnnualYield(convertToFloat(annualYield.getText()));
        termAccount.setMaximumDate(java.sql.Date.valueOf(deadline.getValue()));
        termAccount.setPenalty(Long.parseLong(penalty.getText()));

        return termAccount;
    }

    /**
     * Build default bank account
     * @param bankAccount
     * @return
     */
    private BankAccount buildDefault(BankAccount bankAccount){
        bankAccount.setAccountType(getAccountType(accountTypeComboBox.getSelectionModel()
                .getSelectedItem().toString()));

        bankAccount.setFinancialInstitution(userService.getCurrentUser());

        bankAccount.setAmount(0);
        bankAccount.setCurrency(BankAccount.CURRENCY.EURO);
        bankAccount.setMonthlyFee(convertToFloat(monthlyFee.getText()));
        bankAccount.getFinancialProductHolders().add(0,holder);
        bankAccount.setCreationDate(new Date());

        return bankAccount;
    }

    /**
     * Load the bank account management dashboard interface
     */
    private void loadBankDashboard(){
        DynamicViews.loadBorderCenter("Institution-Dashboard-BankAccount");
        bankAccountService.moveCurrentBankAccount();
    }

    /**
     * Show error when validating bank account record fields
     */
    private void show_error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(bundle.getString("error1"));
        alert.showAndWait();
    }

    /**
     * Retrieve the type of bank account from its name String
     * @param accountTypeName
     * @return
     */
    public BankAccount.ACCOUNT_TYPE getAccountType(String accountTypeName){
        if(accountTypeName.equals(BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT.name())){
            return BankAccount.ACCOUNT_TYPE.INDIVIDUAL_ACCOUNT;
        }else if (accountTypeName.equals(BankAccount.ACCOUNT_TYPE.JOINT_ACCOUNT.name())){
            return BankAccount.ACCOUNT_TYPE.JOINT_ACCOUNT;
        }else if (accountTypeName.equals(BankAccount.ACCOUNT_TYPE.ACCOUNT_UNDIVIDED.name())){
            return BankAccount.ACCOUNT_TYPE.ACCOUNT_UNDIVIDED;
        }
        return null;
    }

    /**
     * Convert string to float
     * @param value
     * @return
     */
    private float convertToFloat(String value){
        try{
            float floatValue = Float.valueOf(value);
            return floatValue;
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("error181") + value + bundle.getString("error182"));
            alert.showAndWait();
        }
        return 0;
    }

    /**
     * Generate pin code for bank account
     * @return
     */
    public static String generatePinCode(){
        return String.valueOf(1000 + (int) (Math.random()*(9999-1000)));
    }
}
