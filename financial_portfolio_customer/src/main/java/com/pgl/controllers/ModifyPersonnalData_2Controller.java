package com.pgl.controllers;

import com.pgl.models.ApplicationClient;
import com.pgl.models.User;
import com.pgl.services.ApplicationClientService;
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
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPersonnalData_2Controller implements Initializable {

    UserService userService = new UserService();
    ApplicationClientService clientService = new ApplicationClientService();
    ApplicationClient currentClient = userService.getCurrentUser();
    static ResourceBundle bundle;

    @FXML
    private Label title_label;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private Button edit_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        title_label.setText(bundle.getString("Title_label"));
        lastName.setPromptText(bundle.getString("LastName_field"));
        firstName.setPromptText(bundle.getString("FirstName_field"));
        edit_btn.setText(bundle.getString("Edit_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ModifyPersonnalData_1Controller.bundle;
        setText();
        setCurrentUser();
    }

    /**
     * Display user data on the interface
     */
    private void setCurrentUser(){
        lastName.setText(currentClient.getName());
        firstName.setText(currentClient.getFirstName());
    }

    /**
     * Update personal data
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if(lastName.getText().isEmpty() ||
                firstName.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error1"));
            alert.showAndWait();

        }else {
            ApplicationClient user = build_user();

            user = clientService.updateClient(user);
            if (user != null){
                userService.setCurrentUser(user);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes7"));
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
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
     * Build user
     * @return the user
     */
    public ApplicationClient build_user(){

        ApplicationClient client = SerializationUtils.clone(currentClient);
        client.setName(lastName.getText());
        client.setFirstName(firstName.getText());

        client.setLogin(client.buildLogin());

        return client;
    }
}
