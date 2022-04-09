package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.services.FinancialProductHolderService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPersonnalData_2Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static FinancialProductHolderService financialProductHolderService = new FinancialProductHolderService();
    static ResourceBundle bundle;

    @FXML
    private Button edit_btn;
    @FXML
    private Label password_label;
    @FXML
    private Label title_label;
    @FXML
    private TextField email;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField newPassword2;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        title_label.setText(bundle.getString("Title_label"));
        email.setPromptText(bundle.getString("Email_field"));
        newPassword.setPromptText(bundle.getString("NewPassword_field"));
        password_label.setText(bundle.getString("Password_label"));
        newPassword2.setPromptText(bundle.getString("NewPassword2_field"));
        edit_btn.setText(bundle.getString("Edit_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ModifyPersonnalData_1Controller.bundle;
        setText();
    }

    /**
     * Reset filled fields
     * @param event the click of the mouse on the button
     */
    @FXML
    private void modify(MouseEvent event) {
        if(email.getText() == "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error12"));
            alert.showAndWait();

        }else if(email.getText() != "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            User user = new User();
            user.setEmail(email.getText());
            user.setLogin(UserService.getCurrentUser().getLogin());
            boolean result = userService.editUser(user);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes6"));
            alert.showAndWait();

            Stage stage = (Stage) edit_btn.getScene().getWindow();
            stage.close();

        }else if(email.getText() == "" &&
                ((newPassword.getText() != "" && newPassword2.getText() == "") ||
                        (newPassword.getText() == "" && newPassword2.getText() != ""))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();

        }else if(email.getText() == "" &&
                (!newPassword.getText().equals(newPassword2.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error5"));
            alert.showAndWait();

        }else{
            User user = new User();
            user.setPassword(newPassword.getText());
            user.setEmail(email.getText());
            user.setLogin(UserService.getCurrentUser().getLogin());
            boolean result = userService.editUser(user);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes7"));
            alert.showAndWait();

            Stage stage = (Stage) edit_btn.getScene().getWindow();
            stage.close();
        }
    }
}
