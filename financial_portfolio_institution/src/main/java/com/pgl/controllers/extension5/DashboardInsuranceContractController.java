package com.pgl.controllers.extension5;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProductHolder;
import com.pgl.models.extension5.InsuranceContract;
import com.pgl.services.BankAccountService;
import com.pgl.services.extension5.InsuranceContractService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardInsuranceContractController implements Initializable {

    static ResourceBundle bundle;

    InsuranceContractService insuranceService = new InsuranceContractService();

    ObservableList list = FXCollections.observableArrayList();
    List<InsuranceContract> insuranceList = new ArrayList<>();

    @FXML
    private Label filters_label;
    @FXML
    private ChoiceBox productInstitutionName;
    @FXML
    private Button Search_btn;
    @FXML
    private ListView<String> insuranceListView;
    @FXML
    private Label InsuranceContracts_label;
    @FXML
    private Button export_btn;
    @FXML
    private Button import_btn;
    @FXML
    private Button Consult_btn;
    @FXML
    private Button Edit_btn;
    @FXML
    private Button Delete_btn;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        filters_label.setText(bundle.getString("Filters_label"));
        Search_btn.setText(bundle.getString("Search_btn"));
        InsuranceContracts_label.setText(bundle.getString("InsuranceContracts_label"));
        export_btn.setText(bundle.getString("Export_btn"));
        import_btn.setText(bundle.getString("Import_btn"));
        Consult_btn.setText(bundle.getString("Consult_btn"));
        Edit_btn.setText(bundle.getString("Edit_btn"));
        Delete_btn.setText(bundle.getString("Delete_btn"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = DashboardController.bundle;
        setText();
        loadInsuranceContract();
    }

    /**
     * Load Insurance Contract of the financial institution
     */
    public void loadInsuranceContract() {
        clear();
        insuranceList = insuranceService.getInsuranceContractsByInstitution();
        if (insuranceList != null && insuranceList.size() > 0) {
            insuranceList.forEach(insurance-> {
                FinancialProductHolder holder = insurance.getFinancialProductHolders().get(0);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String label = insurance.getInsuranceType().name() + "   "
                        + insurance.getInsuranceNumber() + "   "
                        + holder.getFirstName() + " " + holder.getName()
                        + "   "
                        + insurance.getState().name() + "   "
                        + dateFormat.format(insurance.getRenewalDate()) + "   "
                        + insurance.getAnnualPremium() + " €";
                list.add(label);
            });

            insuranceListView.getItems().addAll(list);
        }
    }

    /**
     * Clear items in the list
     */
    public void clear() {
        insuranceList.clear();
        list.clear();
        insuranceListView.getItems().clear();
        insuranceService.moveCurrentInsuranceContract();
    }

    /**
     * Open a window allowing you to add a Insurance Contract to the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_add(MouseEvent event) {
        insuranceService.setEdit(false);
        DynamicViews.loadBorderCenter("extension5/Institution-AddInsuranceContract");
    }

    /**
     * Select an item from the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void selectedItem(MouseEvent event) {
        String label = insuranceListView.getSelectionModel().getSelectedItem();
        int index = insuranceListView.getItems().indexOf(label);

        insuranceService.setCurrentInsurance(insuranceList.get(index));
    }

    /**
     * Show selected item
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_display(MouseEvent event) {
        if (insuranceService.getCurrentInsurance() != null) {
            DynamicViews.loadBorderCenter("extension5/Institution-ViewInsuranceContract");
        } else {
            insuranceService.not_selected_error();
        }

    }

    /**
     * Access the interface for modifying the Insurance Contract selected in the list
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_edit(MouseEvent event) {
        if (insuranceService.getCurrentInsurance() != null) {
            insuranceService.setEdit(true);
            DynamicViews.loadBorderCenter("extension5/Institution-AddInsuranceContract");
        } else {
            insuranceService.not_selected_error();
        }
    }

    /**
     * Open a window allowing you to delete a Insurance Contract to the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_delete(MouseEvent event) {
        if (insuranceService.getCurrentInsurance() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("question4"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = insuranceService.deleteById(insuranceService.getCurrentInsurance().getId().toString());
                // if successful deletion
                if (status) {
                    insuranceService.moveCurrentInsuranceContract();
                    loadInsuranceContract();
                }
            }
        } else {
            insuranceService.not_selected_error();
        }
    }

    /**
     * Search for Client according to the chosen filters
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void search_Client(MouseEvent event) {
        //TODO
    }

    /**
     * Export all clients' data from the institution
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export_ClientData(MouseEvent event) {
        //TODO
    }

    /**
     * Import clients' data
     *
     * @param event the click of the mouse on the button
     */
    @FXML
    private void import_ClientData(MouseEvent event) {
        //TODO
    }
}
