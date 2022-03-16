package com.pgl.services;

import com.pgl.models.ApplicationClient;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @Inject
    static UserService userService = new UserService();

    @FXML
    private TextField name;
    @FXML
    private TextField nationalRegisterNumber;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    /**
     * Supprime les espaces dans le nom
     * @param name
     * @param nationalRegisterNumber
     * @return le nom et le numéro de registre national concaténé
     */
    private String username(String name, String nationalRegisterNumber){
        String clearSpaceName = name.replaceAll("\\s+","");
        return clearSpaceName+nationalRegisterNumber;
    }

    /**
     * Vérifie que le numéro de registre national n'est composé que de nombres et si il fait 11 caractères
     * @param nationalRegisterNumber
     * @return true ou false
     */
    private boolean check_nationalRegisterNumber(String nationalRegisterNumber){
        boolean isNumeric =  nationalRegisterNumber.matches("[+-]?\\d*(\\.\\d+)?");
        isNumeric = (nationalRegisterNumber.length() == 11);
        return isNumeric;
    }

    @FXML
    private void login(MouseEvent event) {
        if(name.getText().isEmpty() || nationalRegisterNumber.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Veuillez remplir tout les champs");
            alert.showAndWait();
        }else if(!check_nationalRegisterNumber(nationalRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre n° de registre national n'est pas au bon format ! \n - 11 chiffres\n - Pas de lettres");
            alert.showAndWait();
        }else{
            ApplicationClient user = new ApplicationClient();
            user.setNationalRegister(nationalRegisterNumber.getText());
            user.setFirstName(name.getText());
            String login = user.buildLogin();
            boolean response = userService.login(login, password.getText());
            if (response){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Les données que vous avez renseigné ne sont pas correct");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void register(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Register.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void password_reset(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-ForgotPassword_1.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void languageFR(ActionEvent event) {
        //TODO
    }

    @FXML
    private void languageEN(ActionEvent event) {
        //TODO
    }
}
