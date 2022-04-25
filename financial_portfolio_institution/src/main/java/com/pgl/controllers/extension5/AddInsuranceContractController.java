package com.pgl.controllers.extension5;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.models.extension5.*;
import com.pgl.services.*;
import com.pgl.services.extension5.*;
import com.pgl.utils.Validators;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddInsuranceContractController implements Initializable {

    UserService userService = new UserService();
    static ResourceBundle bundle;
    FinancialInstitutionService institutionService = new FinancialInstitutionService();
    InsuranceContractService insuranceService = new InsuranceContractService();
    AssistanceInsuranceService assistanceInsuranceService = new AssistanceInsuranceService();
    FamilyInsuranceService familyInsuranceService = new FamilyInsuranceService();
    HabitationInsuranceService habitationInsuranceService = new HabitationInsuranceService();
    HospitalizationInsuranceService hospitalizationInsuranceService = new HospitalizationInsuranceService();
    LifeBranch21InsuranceService lifeBranch21InsuranceService = new LifeBranch21InsuranceService();
    LifeBranch23InsuranceService lifeBranch23InsuranceService = new LifeBranch23InsuranceService();
    PensionSavingsInsuranceService pensionSavingsInsuranceService = new PensionSavingsInsuranceService();
    TravelInsuranceService travelInsuranceService = new TravelInsuranceService();
    VehicleInsuranceService vehicleInsuranceService = new VehicleInsuranceService();

    ProductHolderService productHolderService = new ProductHolderService();
    FinancialProductHolder holder;

    @FXML
    private Label AddInsuranceType_label;
    @FXML
    private TextField clientRegisterNumber;
    @FXML
    private TextField insuranceNumber;
    @FXML
    private ComboBox insuranceTypeComboBox;
    @FXML
    private TextField annualPremium;
    @FXML
    private TextField insuranceTax;
    @FXML
    private DatePicker renewalDate;
    @FXML
    private TextField personNumber;
    @FXML
    private TextField houseAddress;
    @FXML
    private TextField immatriculation;
    @FXML
    private TextField admissionFee;
    @FXML
    private TextField annualReturn;
    @FXML
    private DatePicker duration;
    @FXML
    private TextField depositGuarantee;
    @FXML
    private TextField withholdingTax;
    @FXML
    private TextField taxBenefit;
    @FXML
    private PasswordField password;
    @FXML
    private Button Valid_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        AddInsuranceType_label.setText(bundle.getString("AddInsuranceType_label"));
        clientRegisterNumber.setPromptText(bundle.getString("ClientNationalRegisterNumber_field"));
        insuranceTypeComboBox.setPromptText(bundle.getString("InsuranceTypeComboBox"));
        insuranceNumber.setPromptText(bundle.getString("InsuranceNumber"));
        annualPremium.setPromptText(bundle.getString("AnnualPremium"));
        insuranceTax.setPromptText(bundle.getString("InsuranceTax"));
        renewalDate.setPromptText(bundle.getString("RenewalDate"));
        personNumber.setPromptText(bundle.getString("PersonNumber"));
        houseAddress.setPromptText(bundle.getString("HouseAddress"));
        immatriculation.setPromptText(bundle.getString("Immatriculation"));
        admissionFee.setPromptText(bundle.getString("AdmissionFee"));
        annualReturn.setPromptText(bundle.getString("AnnualReturn"));
        duration.setPromptText(bundle.getString("Duration"));
        depositGuarantee.setPromptText(bundle.getString("DepositGuarantee"));
        withholdingTax.setPromptText(bundle.getString("WithholdingTax"));
        taxBenefit.setPromptText(bundle.getString("TaxBenefit"));
        password.setPromptText(bundle.getString("Password_field"));
        Valid_btn.setText(bundle.getString("Valid_btn"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadComboBoxDatas();
        InsuranceContract insuranceContract = insuranceService.getCurrentInsurance();

        // Whether to load the interface in edit mode
        if(insuranceService.isEdit()){
            insuranceTypeComboBox.getSelectionModel().select(insuranceContract.getInsuranceType().name());
            insuranceTypeComboBox.setDisable(true);
            if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE)){
                setVehicleInsurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE)){
                setFamilyInsurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE)){
                setAssistanceInsurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE)){
                setHabitationInsurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE)){
                setHospitalizationInsurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE)){
                setTravelInsurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE)){
                setLifeBranch21Insurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE)){
                setLifeBranch23Insurance();
            }else if (insuranceContract.getInsuranceType().equals(InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE)){
                setPensionSavingsInsurance();
            }
        }
    }

    /**
     * Load interface Combo Box datas
     */
    private void loadComboBoxDatas(){
        List<String> insuranceTypeList = new ArrayList<>();
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE.name());
        insuranceTypeList.add(InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE.name());
        insuranceTypeComboBox.getItems().addAll(insuranceTypeList);

        insuranceTypeComboBox.getSelectionModel().selectFirst();
        show_family_insurance();
        insuranceTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE.name())) {
                show_family_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE.name())) {
                show_vehicle_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE.name())) {
                show_habitation_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE.name())) {
                setAssistanceInsurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE.name())) {
                show_hospitalization_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE.name())) {
                show_travel_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE.name())) {
                show_lifeBranch21_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE.name())) {
                show_lifeBranch23_insurance();
            }else if (newValue.equals(InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE.name())) {
                show_pensionSavings_insurance();
            }
        });

    }

    /**
     * Load default Insurance contract data on the interface for edit mode
     */
    private void setDefaultInsurance(InsuranceContract insuranceContract){
        clientRegisterNumber.setText(insuranceContract.getFinancialProductHolders().get(0).getNationalRegister());
        insuranceNumber.setText(insuranceContract.getInsuranceNumber());
        insuranceTypeComboBox.getSelectionModel().select(insuranceContract.getInsuranceType().name());
        annualPremium.setText(String.valueOf(insuranceContract.getAnnualPremium()));
        insuranceTax.setText(String.valueOf(insuranceContract.getInsuranceTax()));
        renewalDate.setValue(insuranceContract.getRenewalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusYears(1));
    }

    /**
     * Load Family Insurance contract data on the interface for edit mode
     */
    private void setFamilyInsurance(){
        show_family_insurance();
        FamilyInsurance familyInsurance = (FamilyInsurance) insuranceService.getCurrentInsurance();
        setDefaultInsurance(familyInsurance);
        personNumber.setText(String.valueOf(familyInsurance.getPersonNumber()));
    }

    /**
     * Load Habitation Insurance data on the interface for edit mode
     */
    private void setHabitationInsurance(){
        show_habitation_insurance();
        HabitationInsurance habitationInsurance = (HabitationInsurance) insuranceService.getCurrentInsurance();
        setDefaultInsurance(habitationInsurance);
        houseAddress.setText(habitationInsurance.getHouseAddress());
    }

    /**
     * Load Vehicle Insurance data on the interface for edit mode
     */
    private void setVehicleInsurance(){
        show_vehicle_insurance();
        VehicleInsurance vehicleInsurance = (VehicleInsurance) insuranceService.getCurrentInsurance();
        setDefaultInsurance(vehicleInsurance);
        immatriculation.setText(vehicleInsurance.getImmatriculation());
    }

    /**
     * Load Assistance Insurance data on the interface for edit mode
     */
    private void setAssistanceInsurance(){
        show_assistance_insurance();
        AssistanceInsurance assistanceInsurance = (AssistanceInsurance) insuranceService.getCurrentInsurance();
        setDefaultInsurance(assistanceInsurance);
    }

    /**
     * Load Travel Insurance data on the interface for edit mode
     */
    private void setTravelInsurance(){
        show_travel_insurance();
        TravelInsurance travelInsurance = (TravelInsurance) insuranceService.getCurrentInsurance();
        setDefaultInsurance(travelInsurance);
    }

    /**
     * Load Hospitalization Insurance data on the interface for edit mode
     */
    private void setHospitalizationInsurance(){
        show_hospitalization_insurance();
        HospitalizationInsurance hospitalizationInsurance = (HospitalizationInsurance) insuranceService.getCurrentInsurance();
        setDefaultInsurance(hospitalizationInsurance);
    }

    /**
     * Load default Life Branch Insurance data on the interface for edit mode
     */
    private void setDefaultLifeBranchInsurance(LifeBranchInsurance lifeBranchInsurance){
        show_lifeBranch_insurance();
        setDefaultInsurance(lifeBranchInsurance);
        admissionFee.setText(String.valueOf(lifeBranchInsurance.getAdmissionFee()));
        annualReturn.setText(String.valueOf(lifeBranchInsurance.getAnnualReturn()));
        duration.setValue(lifeBranchInsurance.getDuration().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * Load Life Branch 23 Insurance data on the interface for edit mode
     */
    private void setLifeBranch23Insurance(){
        show_lifeBranch23_insurance();
        LifeBranch23Insurance lifeBranch23Insurance = (LifeBranch23Insurance) insuranceService.getCurrentInsurance();
        setDefaultLifeBranchInsurance(lifeBranch23Insurance);
    }

    /**
     * Load Life Branch 21 Insurance data on the interface for edit mode
     */
    private void setLifeBranch21Insurance(){
        show_lifeBranch21_insurance();
        LifeBranch21Insurance lifeBranch21Insurance = (LifeBranch21Insurance) insuranceService.getCurrentInsurance();
        setDefaultLifeBranchInsurance(lifeBranch21Insurance);
        depositGuarantee.setText(String.valueOf(lifeBranch21Insurance.getDepositGuarantee()));
        withholdingTax.setText(String.valueOf(lifeBranch21Insurance.getWithholdingTax()));
    }

    /**
     * Load Pension Savings Insurance data on the interface for edit mode
     */
    private void setPensionSavingsInsurance(){
        show_pensionSavings_insurance();
        PensionSavingsInsurance pensionSavingsInsurance = (PensionSavingsInsurance) insuranceService.getCurrentInsurance();
        setDefaultLifeBranchInsurance(pensionSavingsInsurance);
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
     * Validate the registration of a Insurance Contract
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_validate(MouseEvent event) {
        if (clientRegisterNumber.getText().isEmpty() || insuranceNumber.getText().isEmpty() || insuranceTypeComboBox.getValue() == null
                || annualPremium.getText().isEmpty() || insuranceTax.getText().isEmpty()
                || renewalDate.getValue() == null ){
            show_error();
        }else if(!Validators.check_nationalRegisterNumber(clientRegisterNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error11"));
            alert.showAndWait();
        }else if(!Validators.check_password(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error5"));
            alert.showAndWait();
        }else if(!institutionService.checkPassword(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error8"));
            alert.showAndWait();
        }else if(( holder = productHolderService
                .getHolderByInstitutionAndRegisterNum(clientRegisterNumber.getText())) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error14"));
            alert.showAndWait();
        }else if( !insuranceService.isEdit() && (insuranceService
                .getInsuranceByNumber(insuranceNumber.getText())) != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error200"));
            alert.showAndWait();
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE.name())){
            if ( personNumber.getText().isEmpty()){
               show_error();
            }else{
                FamilyInsurance result = familyInsuranceService.save(buildFamilyInsurance());
                if (result != null){
                    loadInsuranceDashboard();
                }
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE.name())){
            if ( houseAddress.getText().isEmpty()){
                show_error();
            }else{
                HabitationInsurance result = habitationInsuranceService.save(buildHabitationInsurance());
                if (result != null){
                    loadInsuranceDashboard();
                }
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE.name())){
            if ( immatriculation.getText().isEmpty()){
                show_error();
            }else{
                VehicleInsurance result = vehicleInsuranceService.save(buildVehicleInsurance());
                if (result != null){
                    loadInsuranceDashboard();
                }
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE.name())){
            AssistanceInsurance result = assistanceInsuranceService.save(buildAssistanceInsurance());
            if (result != null){
                loadInsuranceDashboard();
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE.name())){
            HospitalizationInsurance result = hospitalizationInsuranceService.save(buildHospitalizationInsurance());
            if (result != null){
                loadInsuranceDashboard();
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE.name())){
            TravelInsurance result = travelInsuranceService.save(buildTravelInsurance());
            if (result != null){
                loadInsuranceDashboard();
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE.name())){
            if ( admissionFee.getText().isEmpty() || annualReturn.getText().isEmpty()
                    || duration.getValue() == null){
                show_error();
            }else{
                LifeBranch23Insurance result = lifeBranch23InsuranceService.save(buildLifeBranch23Insurance());
                if (result != null){
                    loadInsuranceDashboard();
                }
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE.name())){
            if ( admissionFee.getText().isEmpty() || annualReturn.getText().isEmpty()
                    || duration.getValue() == null || depositGuarantee.getText().isEmpty()
                    || withholdingTax.getText().isEmpty()){
                show_error();
            }else{
                LifeBranch21Insurance result = lifeBranch21InsuranceService.save(buildLifeBranch21Insurance());
                if (result != null){
                    loadInsuranceDashboard();
                }
            }
        }else if (insuranceTypeComboBox.getSelectionModel().getSelectedItem().equals(
                InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE.name())){
            if ( admissionFee.getText().isEmpty() || annualReturn.getText().isEmpty()
                    || duration.getValue() == null
                    || taxBenefit.getText().isEmpty()){
                show_error();
            }else{
                PensionSavingsInsurance result = pensionSavingsInsuranceService.save(buildPensionSavingsInsurance());
                if (result != null){
                    loadInsuranceDashboard();
                }
            }
        }
    }

    /**
     * Build a family insurance
     * @return
     */
    private FamilyInsurance buildFamilyInsurance(){
        FamilyInsurance familyInsurance;
        // If edit mode
        if(insuranceService.isEdit()){
            familyInsurance = (FamilyInsurance) insuranceService.getCurrentInsurance();
            familyInsurance.setModificationDate(new Date());
        }else {
            familyInsurance = new FamilyInsurance();
        }

        buildDefault(familyInsurance);
        familyInsurance.setPersonNumber((int) convertToFloat(personNumber.getText()));

        return familyInsurance;
    }

    /**
     * Build a Habitation insurance
     * @return
     */
    private HabitationInsurance buildHabitationInsurance(){
        HabitationInsurance habitationInsurance;
        // If edit mode
        if(insuranceService.isEdit()){
            habitationInsurance = (HabitationInsurance) insuranceService.getCurrentInsurance();
            habitationInsurance.setModificationDate(new Date());
        }else {
            habitationInsurance = new HabitationInsurance();
        }

        buildDefault(habitationInsurance);
        habitationInsurance.setHouseAddress(houseAddress.getText());

        return habitationInsurance;
    }

    /**
     * Build a vehicle insurance
     * @return
     */
    private VehicleInsurance buildVehicleInsurance(){
        VehicleInsurance vehicleInsurance;
        // If edit mode
        if(insuranceService.isEdit()){
            vehicleInsurance = (VehicleInsurance) insuranceService.getCurrentInsurance();
            vehicleInsurance.setModificationDate(new Date());
        }else {
            vehicleInsurance = new VehicleInsurance();
        }

        buildDefault(vehicleInsurance);
        vehicleInsurance.setImmatriculation(immatriculation.getText());

        return vehicleInsurance;
    }

    /**
     * Build a assistance insurance
     * @return
     */
    private AssistanceInsurance buildAssistanceInsurance(){
        AssistanceInsurance assistanceInsurance;
        // If edit mode
        if(insuranceService.isEdit()){
            assistanceInsurance = (AssistanceInsurance) insuranceService.getCurrentInsurance();
            assistanceInsurance.setModificationDate(new Date());
        }else {
            assistanceInsurance = new AssistanceInsurance();
        }

        buildDefault(assistanceInsurance);

        return assistanceInsurance;
    }

    /**
     * Build a travel insurance
     * @return
     */
    private TravelInsurance buildTravelInsurance(){
        TravelInsurance travelInsurance;
        // If edit mode
        if(insuranceService.isEdit()){
            travelInsurance = (TravelInsurance) insuranceService.getCurrentInsurance();
            travelInsurance.setModificationDate(new Date());
        }else {
            travelInsurance = new TravelInsurance();
        }

        buildDefault(travelInsurance);

        return travelInsurance;
    }

    /**
     * Build a hospitalization insurance
     * @return
     */
    private HospitalizationInsurance buildHospitalizationInsurance(){
        HospitalizationInsurance hospitalizationInsurance ;
        // If edit mode
        if(insuranceService.isEdit()){
            hospitalizationInsurance = (HospitalizationInsurance) insuranceService.getCurrentInsurance();
            hospitalizationInsurance.setModificationDate(new Date());
        }else {
            hospitalizationInsurance = new HospitalizationInsurance();
        }

        buildDefault(hospitalizationInsurance);

        return hospitalizationInsurance;
    }

    /**
     * Build a Life Branch 21 insurance
     * @return
     */
    private LifeBranch21Insurance buildLifeBranch21Insurance(){
        LifeBranch21Insurance lifeBranch21Insurance ;
        // If edit mode
        if(insuranceService.isEdit()){
            lifeBranch21Insurance = (LifeBranch21Insurance) insuranceService.getCurrentInsurance();
            lifeBranch21Insurance.setModificationDate(new Date());
        }else {
            lifeBranch21Insurance = new LifeBranch21Insurance();
        }

        buildDefaultLifeBranch(lifeBranch21Insurance);
        lifeBranch21Insurance.setDepositGuarantee(convertToFloat(depositGuarantee.getText()));
        lifeBranch21Insurance.setWithholdingTax(convertToFloat(withholdingTax.getText()));

        return lifeBranch21Insurance;
    }

    /**
     * Build a Life Branch 23 insurance
     * @return
     */
    private LifeBranch23Insurance buildLifeBranch23Insurance(){
        LifeBranch23Insurance lifeBranch23Insurance ;
        // If edit mode
        if(insuranceService.isEdit()){
            lifeBranch23Insurance = (LifeBranch23Insurance) insuranceService.getCurrentInsurance();
            lifeBranch23Insurance.setModificationDate(new Date());
        }else {
            lifeBranch23Insurance = new LifeBranch23Insurance();
        }

        buildDefaultLifeBranch(lifeBranch23Insurance);

        return lifeBranch23Insurance;
    }

    /**
     * Build a Pension Savings insurance
     * @return
     */
    private PensionSavingsInsurance buildPensionSavingsInsurance(){
        PensionSavingsInsurance pensionSavingsInsurance ;
        // If edit mode
        if(insuranceService.isEdit()){
            pensionSavingsInsurance = (PensionSavingsInsurance) insuranceService.getCurrentInsurance();
            pensionSavingsInsurance.setModificationDate(new Date());
        }else {
            pensionSavingsInsurance = new PensionSavingsInsurance();
        }

        buildDefaultLifeBranch(pensionSavingsInsurance);
        pensionSavingsInsurance.setTaxBenefit(convertToFloat(taxBenefit.getText()));

        return pensionSavingsInsurance;
    }

    /**
     * Build default Insurance Contract
     * @param insuranceContract
     * @return
     */
    private InsuranceContract buildDefault(InsuranceContract insuranceContract){
        insuranceContract.setInsuranceType(getInsuranceType(insuranceTypeComboBox.getSelectionModel()
                .getSelectedItem().toString()));

        insuranceContract.setFinancialInstitution(userService.getCurrentUser());
        insuranceContract.getFinancialProductHolders().add(0,holder);

        insuranceContract.setAmount(0);
        insuranceContract.setInsuranceNumber(insuranceNumber.getText());
        insuranceContract.setAnnualPremium(convertToFloat(annualPremium.getText()));
        insuranceContract.setInsuranceTax(convertToFloat(insuranceTax.getText()));
        insuranceContract.setRenewalDate(java.sql.Date.valueOf(renewalDate.getValue().plusYears(1)));
        insuranceContract.setCreationDate(new Date());

        return insuranceContract;
    }

    /**
     * Build default Life Branch Insurance Contract
     * @param lifeBranchInsurance
     * @return
     */
    private LifeBranchInsurance buildDefaultLifeBranch(LifeBranchInsurance lifeBranchInsurance){

        buildDefault(lifeBranchInsurance);
        lifeBranchInsurance.setAdmissionFee(convertToFloat(admissionFee.getText()));
        lifeBranchInsurance.setAnnualReturn(convertToFloat(annualReturn.getText()));
        lifeBranchInsurance.setDuration(java.sql.Date.valueOf(duration.getValue()));

        return lifeBranchInsurance;
    }

    /**
     * Load the insurance contract management dashboard interface
     */
    private void loadInsuranceDashboard(){
        DynamicViews.loadBorderCenter("extension5/Institution-Dashboard-InsuranceContract");
        insuranceService.moveCurrentInsuranceContract();
    }

    /**
     * Show error when validating bank account record fields
     */
    private void show_error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(bundle.getString("error1"));
        alert.showAndWait();
    }

    /**
     * Retrieve the type of Insurance Contract from its name String
     * @param insuranceTypeName
     * @return
     */
    public InsuranceContract.INSURANCE_TYPE getInsuranceType(String insuranceTypeName){
        if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.FAMILY_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.HABITATION_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.VEHICLE_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.ASSISTANCE_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.HOSPITALIZATION_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.TRAVEL_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_21_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.LIFE_BRANCH_23_INSURANCE;
        }else if(insuranceTypeName.equals(InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE.name())){
            return InsuranceContract.INSURANCE_TYPE.PENSION_SAVINGS_INSURANCE;
        }
        return null;
    }

    /**
     * Convert string to float
     * @param value
     * @return
     */
    private float convertToFloat(String value){
        try{
            float floatValue = Float.valueOf(value);
            return floatValue;
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("error181") + value + bundle.getString("error182"));
            alert.showAndWait();
        }
        return 0;
    }
}
