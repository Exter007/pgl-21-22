package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.FinancialProductHolder;
import com.pgl.services.FinancialInstitutionService;
import com.pgl.services.ProductHolderService;
import com.pgl.services.UserService;
import com.pgl.utils.Validators;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardAddClientController implements Initializable {

    UserService userService = new UserService();
    ProductHolderService productHolderService = new ProductHolderService();
    FinancialInstitutionService institutionService = new FinancialInstitutionService();

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

    /**
     *  Load Current Holder data on the interface for edit mode
     */
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
        }else if (!institutionService.checkPassword(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Le mot de passe est incorrect");
            alert.showAndWait();
        } else if(!productHolderService.isEdit() && (productHolderService
                .getHolderByInstitutionAndRegisterNum(nationalRegisterNumber.getText())) != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Un client avec ce registre national existe deja");
            alert.showAndWait();
        } else{
            FinancialProductHolder holder = build_holder();
            FinancialProductHolder result = productHolderService.save(holder);

            if (result != null){
                DynamicViews.loadBorderCenter("Institution-Dashboard-Client");
                productHolderService.moveCurrentClient();
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
            holder.setModificationDate(new Date());
        }else {
            holder = new FinancialProductHolder();
        }

        holder.setFirstName(firstName.getText());
        holder.setName(lastName.getText());
        holder.setNationalRegister(nationalRegisterNumber.getText());
        holder.setBirthDate(java.sql.Date.valueOf(birthDate.getValue()));
        holder.setFinancialInstitution(userService.getCurrentUser());

        return holder;
    }
}
