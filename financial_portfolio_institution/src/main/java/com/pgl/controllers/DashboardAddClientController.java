package com.pgl.controllers;

import com.pgl.models.FinancialInstitution;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.ProductHolderService;
import com.pgl.services.UserService;
import com.pgl.utils.GlobalStage;
import com.pgl.utils.Validators;
import javafx.event.ActionEvent;
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
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardAddClientController implements Initializable {

    UserService userService = new UserService();
    ProductHolderService productHolderService = new ProductHolderService();

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField nationalRegisterNumber;
    @FXML
    private DatePicker birthDate;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

         // Load interface in edit mode
        if(productHolderService.isEdit()){
            setCurrentHolder();
        }
    }

    private void setCurrentHolder(){
        FinancialProductHolder holder = productHolderService.getCurrentHolder();
        firstName.setText(holder.getFirstName());
        lastName.setText(String.valueOf(holder.getName()));
        nationalRegisterNumber.setText(holder.getNationalRegister());
        birthDate.setValue(holder.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * Add a client to the institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void add_customer(MouseEvent event) {
        if(firstName.getText().isEmpty() ||
                lastName.getText().isEmpty() ||
                nationalRegisterNumber.getText().isEmpty() ||
                password.getText().isEmpty() ||
                birthDate.getValue() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setHeaderText("Un ou plusieurs champs sont invalides, veuillez réessayer");
            alert.showAndWait();
        }else if(!Validators.check_nationalRegisterNumber(nationalRegisterNumber.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre n° de registre national n'est pas au bon format ! \n - 11 chiffres\n - Pas de lettres");
            alert.showAndWait();

        }else if(!Validators.check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Votre mot de passe doit comporter au moins 1 lettre et 1 chiffre");
            alert.showAndWait();
        }else{
            FinancialProductHolder holder = build_holder();

            FinancialProductHolder result = productHolderService.save(holder);

            if (result != null){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Institution-Dashboard.fxml"));
                    Stage newWindow = new Stage();
                    Scene scene = new Scene(root);
                    newWindow.setScene(scene);
                    GlobalStage.setStage(newWindow);

                    productHolderService.moveCurrentClient();

                } catch (IOException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * Build Financial Product Holder from the interface to save
     * @return FinancialProductHolder
     */
    public FinancialProductHolder build_holder(){
        FinancialProductHolder holder;
        // If edit mode
        if(productHolderService.isEdit()){
            holder = productHolderService.getCurrentHolder();
        }else {
            holder = new FinancialProductHolder();
            holder.toUpdate = false;
        }

        holder.setFirstName(firstName.getText());
        holder.setName(lastName.getText());
        holder.setNationalRegister(nationalRegisterNumber.getText());
        holder.setBirthDate(java.sql.Date.valueOf(birthDate.getValue()));
        FinancialInstitution currentUser = userService.getCurrentUser();
        // Set password to be able to check it
        currentUser.setPassword(password.getText());
        holder.setFinancialInstitution((currentUser));

        return holder;
    }
}
