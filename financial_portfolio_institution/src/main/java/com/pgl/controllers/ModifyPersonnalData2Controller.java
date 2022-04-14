package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.Address;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.services.FinancialInstitutionService;
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
import org.apache.commons.lang3.SerializationUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyPersonnalData2Controller implements Initializable {

    UserService userService = new UserService();
    FinancialInstitutionService institutionService = new FinancialInstitutionService();
    static ResourceBundle bundle;
    FinancialInstitution currentInstitution = userService.getCurrentUser();

    @FXML
    private Button edit_btn;
    @FXML
    private Label title_label;
    @FXML
    private TextField institutionName;
    @FXML
    private TextField number;
    @FXML
    private TextField street;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField city;
    @FXML
    private TextField country;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        title_label.setText(bundle.getString("Title_passoword_label"));
        edit_btn.setText(bundle.getString("Edit_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ModifyPersonnalDataController.bundle;
        setText();
        setCurrentUser();
    }

    /**
     * Display user data on the interface
     */
    private void setCurrentUser(){
        institutionName.setText(currentInstitution.getName());
        number.setText(currentInstitution.getAddress().getStreetNumber());
        street.setText(currentInstitution.getAddress().getStreet());
        zipCode.setText(currentInstitution.getAddress().getPostalCode());
        city.setText(currentInstitution.getAddress().getCity());
        country.setText(currentInstitution.getAddress().getCountry());
    }

    /**
     * Update personal data
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if(number.getText().isEmpty() ||
                street.getText().isEmpty() ||
                zipCode.getText().isEmpty() ||
                city.getText().isEmpty() ||
                country.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error3"));
            alert.showAndWait();

        }else {
            FinancialInstitution user = build_user();

            user = institutionService.updateInstitution(user);
            if (user != null){
                userService.setCurrentUser(user);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("succes4"));
                alert.showAndWait();

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
    }


    /**
     * Build user
     * @return the user
     */
    public FinancialInstitution build_user(){

//        FinancialInstitution institution = institutionService.findById(currentInstitution.getBIC());
        FinancialInstitution institution = SerializationUtils.clone(currentInstitution);
        institution.setName(institutionName.getText());

        Address address = institution.getAddress();
        address.setStreetNumber(number.getText());
        address.setStreet(street.getText());
        address.setCity(city.getText());
        address.setPostalCode(zipCode.getText());
        address.setCountry(country.getText());

        institution.setAddress(address);

        institution.setLogin(institution.buildLogin());

//        institution.setModificationDate(new Date());
//        institution.toUpdate = true;

        return institution;
    }
}
