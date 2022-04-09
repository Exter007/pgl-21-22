package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.BankAccountService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewBankAccountController implements Initializable {

    BankAccountService bankAccountService = new BankAccountService();
    BankAccount bankAccount;

    @FXML
    private Label clientRegisterNumber;
    @FXML
    private Label accountNature;
    @FXML
    private Label accountType;
    @FXML
    private Label monthlyFee;
    @FXML
    private Label annualYield;
    @FXML
    private Label ibanLinked;
    @FXML
    private Label interest;
    @FXML
    private Label loyaltyBonus;
    @FXML
    private Label tutorRegisterNumber;
    @FXML
    private Label maxTransactionAmount;
    @FXML
    private Label deadline;
    @FXML
    private Label penalty;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAccount();
    }

    /**
     * Load bank account data to the interface
     */
    private void loadAccount(){
        bankAccount = bankAccountService.getCurrentBankAccount();
        clientRegisterNumber.setText(bankAccount
                .getFinancialProductHolders().get(0).getNationalRegister());
        accountNature.setText(bankAccount.getNature().name());
        accountType.setText(bankAccount.getAccountType().name());
        monthlyFee.setText(String.valueOf(bankAccount.getMonthlyFee()));

        if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT)){
            loadCurrentAccount();
            show_current_account();
        }else if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.SAVING_ACCOUNT)){
            loadSavingAccount();
            show_saving_account();
        }else if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT)){
            loadYoungAccount();
            show_young_account();
        }else if (bankAccount.getNature().equals(BankAccount.ACCOUNT_NATURE.TERM_ACCOUNT)){
            loadTermAccount();
            show_term_account();
        }
    }

    /**
     * Load current bank account data to the interface
     */
    private void loadCurrentAccount(){
        ibanLinked.setText(bankAccount.getIban());
    }

    /**
     * Load saving bank account data to the interface
     */
    private void loadSavingAccount(){
        SavingsAccount savingsAccount = (SavingsAccount) bankAccount;
        ibanLinked.setText(savingsAccount.getJointIban().getIban());
        interest.setText(String.valueOf(savingsAccount.getAnnualInterest()));
        loyaltyBonus.setText(String.valueOf(savingsAccount.getLoyaltyBonus()));
    }

    /**
     * Load young bank account data to the interface
     */
    private void loadYoungAccount(){
        YoungAccount youngAccount = (YoungAccount) bankAccount;
        tutorRegisterNumber.setText(youngAccount
                .getFinancialProductHolders().get(1).getNationalRegister());
        tutorRegisterNumber.setText(youngAccount.getFinancialProductHolders().get(1).getNationalRegister());
        maxTransactionAmount.setText(String.valueOf(youngAccount.getMaxTransactionAmount()));
    }

    /**
     * Load term bank account data to the interface
     */
    private void loadTermAccount(){
        TermAccount termAccount = (TermAccount) bankAccount;
        annualYield.setText(String.valueOf(termAccount.getAnnualYield()));
        penalty.setText(String.valueOf(termAccount.getPenalty()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        deadline.setText(dateFormat.format(termAccount.getMaximumDate()));
    }

    /**
     * Display the fields necessary for the display of a current bank account
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
     * Display the fields necessary for the display of a saving bank account
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
     * Display the fields necessary for the display of a young bank account
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
     * Display the fields necessary for the display of a term bank account
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
     * Access the interface for modifying the bank account selected
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_edit(MouseEvent event){
        if(bankAccountService.getCurrentBankAccount() != null){
            bankAccountService.setEdit(true);
            DynamicViews.loadBorderCenter("Institution-Dashboard-AddBankAccount");
        }else{
            bankAccountService.not_selected_error();
        }
    }

    /**
     * Delete Bank Account selected
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if(bankAccountService.getCurrentBankAccount() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez la suppression du Compte bancaire?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = bankAccountService.deleteById(
                        String.valueOf(bankAccountService.getCurrentBankAccount().getId()));
                // if successful deletion
                if (status){
                    bankAccountService.moveCurrentBankAccount();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard-BankAccount.fxml"));
                        Stage newWindow = new Stage();
                        Scene scene = new Scene(root);
                        newWindow.setScene(scene);
                        GlobalStage.setStage(newWindow);

                        bankAccountService.moveCurrentBankAccount();

                    } catch (IOException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    bankAccountService.not_selected_error();
                }
            }
        }else{
            bankAccountService.not_selected_error();
        }
    }
}
