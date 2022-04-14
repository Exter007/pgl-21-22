package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.FinancialInstitution;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPasswordController implements Initializable {

    UserService userService = new UserService();
    FinancialInstitutionService institutionService = new FinancialInstitutionService();
    static ResourceBundle bundle;

    @FXML
    private Label title_label;
    @FXML
    private Label password_label;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword2;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Button edit_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
    }

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        title_label.setText(bundle.getString("Title_label"));
		oldPassword.setPromptText(bundle.getString("OldPassword_field"));
        newPassword.setPromptText(bundle.getString("NewPassword_field"));
        password_label.setText(bundle.getString("Password_label"));
        newPassword2.setPromptText(bundle.getString("NewPassword2_field"));
        edit_btn.setText(bundle.getString("Edit_btn"));
    }

    /**
     * Update password
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if(oldPassword.getText().isEmpty() ||
                newPassword.getText().isEmpty()||
                newPassword2.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();

        }else if(!Validators.check_password(oldPassword.getText()) || !Validators.check_password(newPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error5"));
            alert.showAndWait();

        }else if(!newPassword.getText().equals(newPassword2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("error6"));
            alert.showAndWait();

        }else if (!institutionService.checkPassword(oldPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        } else {
            FinancialInstitution result = institutionService.updatePassword(newPassword.getText());
            if (result != null){
                userService.setCurrentUser(result);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes2"));
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
