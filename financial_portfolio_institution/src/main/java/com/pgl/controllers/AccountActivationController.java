package com.pgl.controllers;

import com.pgl.models.User;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
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

public class AccountActivationController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private TextField code;
    @FXML
    private Label Check_label;
    @FXML
    private Button Check_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        code.setText(bundle.getString("Code_field"));
        Check_label.setText(bundle.getString("Check_label"));
        Check_btn.setText(bundle.getString("Check_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = RegisterController.bundle;
        setText();
    }

    /**
     * Activate the account that has just been created
     * @param event the click of the mouse on the button
     */
    @FXML
    private void validate(MouseEvent event) {

        if(code.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error10"));
            alert.showAndWait();
        }else {
            User user = new User();

            user.setLogin(userService.getCurrentUser().getLogin());
            user.setToken(code.getText());

            boolean result = userService.accountActivation(user);

            if(result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes5"));
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-login.fxml"));
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
