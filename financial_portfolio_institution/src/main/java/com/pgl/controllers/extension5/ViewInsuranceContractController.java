package com.pgl.controllers.extension5;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.BankAccount;
import com.pgl.models.SavingsAccount;
import com.pgl.models.TermAccount;
import com.pgl.models.YoungAccount;
import com.pgl.models.extension5.*;
import com.pgl.services.BankAccountService;
import com.pgl.services.UserService;
import com.pgl.services.extension5.InsuranceContractService;
import com.pgl.utils.GlobalStage;
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
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewInsuranceContractController implements Initializable {

    UserService userService = new UserService();
    static ResourceBundle bundle;

    InsuranceContractService insuranceService = new InsuranceContractService();
    InsuranceContract insuranceContract;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @FXML
    private Label ViewInsuranceType_label;
    @FXML
    private Label clientRegisterNumber_label;
    @FXML
    private Label insuranceType_label;
    @FXML
    private Label insuranceNumber_label;
    @FXML
    private Label annualPremium_label;
    @FXML
    private Label insuranceTax_label;
    @FXML
    private Label renewalDate_label;
    @FXML
    private Label personNumber_label;
    @FXML
    private Label houseAddress_label;
    @FXML
    private Label immatriculation_label;
    @FXML
    private Label admissionFee_label;
    @FXML
    private Label annualReturn_label;
    @FXML
    private Label duration_label;
    @FXML
    private Label depositGuarantee_label;
    @FXML
    private Label withholdingTax_label;
    @FXML
    private Label taxBenefit_label;

    @FXML
    private Label clientRegisterNumber;
    @FXML
    private Label insuranceNumber;
    @FXML
    private Label insuranceType;
    @FXML
    private Label annualPremium;
    @FXML
    private Label insuranceTax;
    @FXML
    private Label renewalDate;
    @FXML
    private Label personNumber;
    @FXML
    private Label houseAddress;
    @FXML
    private Label immatriculation;
    @FXML
    private Label admissionFee;
    @FXML
    private Label annualReturn;
    @FXML
    private Label duration;
    @FXML
    private Label depositGuarantee;
    @FXML
    private Label withholdingTax;
    @FXML
    private Label taxBenefit;
    @FXML
    private Button Edit_btn;
    @FXML
    private Button Delete_btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        loadInsurance();
    }

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        ViewInsuranceType_label.setText(bundle.getString("ViewInsuranceType_label"));
        clientRegisterNumber_label.setText(bundle.getString("ClientNationalRegisterNumber_field"));
        insuranceType_label.setText(bundle.getString("InsuranceTypeComboBox"));
        insuranceNumber_label.setText(bundle.getString("InsuranceNumber"));
        annualPremium_label.setText(bundle.getString("AnnualPremium"));
        insuranceTax_label.setText(bundle.getString("InsuranceTax"));
        renewalDate_label.setText(bundle.getString("RenewalDate_label"));
        personNumber_label.setText(bundle.getString("PersonNumber"));
        houseAddress_label.setText(bundle.getString("HouseAddress"));
        immatriculation_label.setText(bundle.getString("Immatriculation"));
        admissionFee_label.setText(bundle.getString("AdmissionFee"));
        annualReturn_label.setText(bundle.getString("AnnualReturn"));
        duration_label.setText(bundle.getString("Duration"));
        depositGuarantee_label.setText(bundle.getString("DepositGuarantee"));
        withholdingTax_label.setText(bundle.getString("WithholdingTax"));
        taxBenefit_label.setText(bundle.getString("TaxBenefit"));
        Edit_btn.setText(bundle.getString("Edit_btn"));
        Delete_btn.setText(bundle.getString("Delete_btn"));
    }

    /**
     * Load Insurance Contract data to the interface
     */
    private void loadInsurance(){
        insuranceContract = insuranceService.getCurrentInsurance();
        clientRegisterNumber.setText(insuranceContract
                .getFinancialProductHolders().get(0).getNationalRegister());
        insuranceType.setText(insuranceContract.getInsuranceType().name());
        insuranceNumber.setText(insuranceContract.getInsuranceNumber());
        annualPremium.setText(String.valueOf(insuranceContract.getAnnualPremium()));
        insuranceTax.setText(String.valueOf(insuranceContract.getInsuranceTax()));
        renewalDate.setText(dateFormat.format(insuranceContract.getRenewalDate()));

        if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE)){
            loadFamilyInsurance();
            show_family_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE)){
            loadVehicleInsurance();
            show_vehicle_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE)){
            loadHabitationInsurance();
            show_habitation_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE)){
            show_assistance_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE)){
            show_hospitalization_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE)){
            show_travel_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE)){
            loadLifeBranch21Insurance();
            show_lifeBranch21_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE)){
            loadDefaultLifeBranchInsurance();
            show_lifeBranch23_insurance();
        }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE)){
            loadPensionSavingsInsurance();
            show_pensionSavings_insurance();
        }
    }

    /**
     * Load Family insurance contract data to the interface
     */
    private void loadFamilyInsurance(){
        FamilyInsurance familyInsurance = (FamilyInsurance) insuranceContract;
        personNumber.setText(String.valueOf(familyInsurance.getPersonNumber()));
    }

    /**
     * Load Habitation insurance contract data to the interface
     */
    private void loadHabitationInsurance(){
        HabitationInsurance habitationInsurance = (HabitationInsurance) insuranceContract;
        houseAddress.setText(habitationInsurance.getHouseAddress());
    }

    /**
     * Load Vehicle insurance contract data to the interface
     */
    private void loadVehicleInsurance(){
        VehicleInsurance vehicleInsurance = (VehicleInsurance) insuranceContract;
        immatriculation.setText(vehicleInsurance.getImmatriculation());
    }

    /**
     * Load default Life Branch insurance contract data to the interface
     */
    private void loadDefaultLifeBranchInsurance(){
        LifeBranchInsurance lifeBranchInsurance = (LifeBranchInsurance) insuranceContract;
        admissionFee.setText(String.valueOf(lifeBranchInsurance.getAdmissionFee()));
        annualReturn.setText(String.valueOf(lifeBranchInsurance.getAnnualReturn()));
        duration.setText(dateFormat.format(lifeBranchInsurance.getDuration()));
    }

    /**
     * Load Life Branch 21 insurance contract data to the interface
     */
    private void loadLifeBranch21Insurance(){
        loadDefaultLifeBranchInsurance();
        LifeBranch21Insurance lifeBranch21Insurance = (LifeBranch21Insurance) insuranceContract;
        depositGuarantee.setText(String.valueOf(lifeBranch21Insurance.getDepositGuarantee()));
        withholdingTax.setText(String.valueOf(lifeBranch21Insurance.getWithholdingTax()));
    }

    /**
     * Load Pension Savings Insurance contract data to the interface
     */
    private void loadPensionSavingsInsurance(){
        loadDefaultLifeBranchInsurance();
        PensionSavingsInsurance pensionSavingsInsurance = (PensionSavingsInsurance) insuranceContract;
        taxBenefit.setText(String.valueOf(pensionSavingsInsurance.getTaxBenefit()));
    }

    /**
     * Display the fields necessary for the registration of a family insurance
     */
    private void show_family_insurance(){
        personNumber.setVisible(true);
        houseAddress.setVisible(false);
        immatriculation.setVisible(false);
        admissionFee.setVisible(false);
        annualReturn.setVisible(false);
        duration.setVisible(false);
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a habitation insurance
     */
    private void show_habitation_insurance(){
        personNumber.setVisible(false);
        houseAddress.setVisible(true);
        immatriculation.setVisible(false);
        admissionFee.setVisible(false);
        annualReturn.setVisible(false);
        duration.setVisible(false);
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a vehicle insurance
     */
    private void show_vehicle_insurance(){
        personNumber.setVisible(false);
        houseAddress.setVisible(false);
        immatriculation.setVisible(true);
        admissionFee.setVisible(false);
        annualReturn.setVisible(false);
        duration.setVisible(false);
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a assistance insurance
     */
    private void show_assistance_insurance(){
        personNumber.setVisible(false);
        houseAddress.setVisible(false);
        immatriculation.setVisible(false);
        admissionFee.setVisible(false);
        annualReturn.setVisible(false);
        duration.setVisible(false);
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a hospitalization insurance
     */
    private void show_hospitalization_insurance(){
        personNumber.setVisible(false);
        houseAddress.setVisible(false);
        immatriculation.setVisible(false);
        admissionFee.setVisible(false);
        annualReturn.setVisible(false);
        duration.setVisible(false);
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a travel insurance
     */
    private void show_travel_insurance(){
        personNumber.setVisible(false);
        houseAddress.setVisible(false);
        immatriculation.setVisible(false);
        admissionFee.setVisible(false);
        annualReturn.setVisible(false);
        duration.setVisible(false);
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a life branch insurance
     */
    private void show_lifeBranch_insurance(){
        personNumber.setVisible(false);
        houseAddress.setVisible(false);
        immatriculation.setVisible(false);
        admissionFee.setVisible(true);
        annualReturn.setVisible(true);
        duration.setVisible(true);
    }

    /**
     * Display the fields necessary for the registration of a life branch 21 insurance
     */
    private void show_lifeBranch21_insurance(){
        show_lifeBranch_insurance();
        depositGuarantee.setVisible(true);
        withholdingTax.setVisible(true);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a life branch 23 insurance
     */
    private void show_lifeBranch23_insurance(){
        show_lifeBranch_insurance();
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(false);
    }

    /**
     * Display the fields necessary for the registration of a pension savings insurance
     */
    private void show_pensionSavings_insurance(){
        show_lifeBranch_insurance();
        depositGuarantee.setVisible(false);
        withholdingTax.setVisible(false);
        taxBenefit.setVisible(true);
    }

    /**
     * Access the interface for modifying the bank account selected
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_edit(MouseEvent event){
        if(insuranceService.getCurrentInsurance() != null){
            insuranceService.setEdit(true);
            DynamicViews.loadBorderCenter("extension5/Institution-AddInsuranceContract");
        }else{
            insuranceService.not_selected_error();
        }
    }

    /**
     * Delete Bank Account selected
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if(insuranceService.getCurrentInsurance() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("question4"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = insuranceService.deleteById(
                        insuranceService.getCurrentInsurance().getId().toString());
                // if successful deletion
                if (status){
                    insuranceService.moveCurrentInsuranceContract();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/views/extension5/Institution-Dashboard-InsuranceContract.fxml"));
                        Stage newWindow = new Stage();
                        Scene scene = new Scene(root);
                        newWindow.setScene(scene);
                        GlobalStage.setStage(newWindow);

                    } catch (IOException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    insuranceService.not_selected_error();
                }
            }
        }else{
            insuranceService.not_selected_error();
        }
    }
}
