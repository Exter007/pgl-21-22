package com.pgl.controllers;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForgotPassword_1Controller implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private TextField name;
    @FXML
    private TextField BIC;
    @FXML
    private Label forgotpassword1_label;
    @FXML
    private Button sendCode_button;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        forgotpassword1_label.setText(bundle.getString("ForgotPassword1_label"));
        name.setPromptText(bundle.getString("Name_field"));
        BIC.setPromptText(bundle.getString("BIC_field"));
        sendCode_button.setText(bundle.getString("SendCode_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = LoginController.bundle;
        setText();
    }


    /**
     * Check if the e-mail entered is the correct one
     * @param event the click of the mouse on the button
     */
    @FXML
    private void validate(MouseEvent event) {
        if(name.getText().isEmpty() || BIC.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();
        }else if(!Validators.check_BIC(BIC.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error2"));
            alert.showAndWait();
        }else {
            FinancialInstitution institution = new FinancialInstitution();
            institution.setBIC(BIC.getText());
            institution.setName(name.getText());

            User user = new User();
            user.setLogin(institution.buildLogin());

            User result = userService.sendPasswordResetCode(user);

            if (result != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes1"));
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-ForgotPassword_2.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);

                } catch (IOException ex) {
                    Logger.getLogger(ForgotPassword_1Controller.class.getName()).log(Level.SEVERE, null, ex);
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
            Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Login.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(ForgotPassword_1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
