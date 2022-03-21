package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
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

public class ForgotPassword_2Controller implements Initializable {

    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField newPassword2;
    @FXML
    private TextField code;
    @FXML
    private Button reset_button;
    @FXML
    private Label PasswordLabel;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        newPassword.setPromptText(bundle.getString("New.Password"));
        newPassword2.setPromptText(bundle.getString("New.Password.2"));
        code.setPromptText(bundle.getString("Code"));
        reset_button.setText(bundle.getString("Reset_button"));
        PasswordLabel.setText(bundle.getString("Password.label"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ForgotPassword_1Controller.bundle;
        setText();
    }

    /**
     * Checks if the password is in the right format (at least 1 letter and 1 number)
     * @param password the user password
     * @return true or false
     */
    private boolean check_password(String password){
        char c;
        boolean alpha = false;
        boolean number = false;
        for(int i=0; i < password.length(); i++) {
            c = password.charAt(i);
            if( Character.isDigit(c)) {
                number = true;
            }
            if (Character.isUpperCase(c) || Character.isLowerCase(c)) {
                alpha = true;
            }
            if(number && alpha)
                return true;
        }
        return false;
    }

    /**
     * Reset the password
     * @param event the click of the mouse on the button
     */
    @FXML
    private void reset(MouseEvent event) {
        if (!check_password(newPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error4"));
            alert.showAndWait();

        } else if (!newPassword.getText().equals(newPassword2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("error5"));
            alert.showAndWait();

        } else {
            /*
            User user = new User();
            user.setPassword(newPassword.getText());
            user.setToken(code.getText());
            user.setLogin(UserService.getCurrentUser().getLogin());
            boolean result = userService.resetPassword(user);
            */
            if (true /*result*/) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes2"));
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);

                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * Back to previous window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(ForgotPassword_2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
