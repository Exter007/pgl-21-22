package com.pgl.controllers;

import com.pgl.models.User;
import com.pgl.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPersonnalData2Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();
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
        bundle = ModifyPersonnalDataController.bundle;
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
            alert.setHeaderText(bundle.getString("error9"));
            alert.showAndWait();

        }else if(email.getText() != "" && newPassword.getText() == "" && newPassword2.getText() == ""){
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes3"));
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
            alert.setHeaderText(bundle.getString("error6"));
            alert.showAndWait();

        }else{
            //TODO

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(bundle.getString("succes4"));
            alert.showAndWait();

            Stage stage = (Stage) edit_btn.getScene().getWindow();
            stage.close();
        }

        Stage stage = (Stage) newPassword.getScene().getWindow();
        stage.close();
    }
}
