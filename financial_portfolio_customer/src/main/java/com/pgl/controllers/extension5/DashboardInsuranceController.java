package com.pgl.controllers.extension5;

import com.pgl.controllers.DashboardController;
import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.models.extension5.InsuranceContract;
import com.pgl.services.BankAccountService;
import com.pgl.services.FinancialProductService;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import com.pgl.services.extension5.InsuranceContractService;
import com.pgl.utils.GlobalStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardInsuranceController implements Initializable {

    static ResourceBundle bundle;

    InsuranceContractService insuranceService = new InsuranceContractService();

    @FXML
    private Label Wallet_label;
    @FXML
    private Label YourInsurances_label;
    @FXML
    private GridPane Product_gridPane;
    @FXML
    private Button insuranceType_btn;
    @FXML
    private Button insuranceQuote_btn;
    @FXML
    private Button ask_insuranceQuote_btn;
    @FXML
    private Button Day_btn;
    @FXML
    private Button Week_btn;
    @FXML
    private Button Month_btn;
    @FXML
    private Button Year_btn;
    @FXML
    private Label From_label;
    @FXML
    private Label To_label;
    @FXML
    private Button Graph_btn;
    @FXML
    private Button List_btn;
    @FXML
    private Button Tab_btn;
    @FXML
    private Button Export_btn;

    @FXML
    private TableView products_tableview;
    @FXML
    private DatePicker from_date;
    @FXML
    private DatePicker to_date;
    @FXML
    private ChoiceBox<String> export_format;
    @FXML
    private TableView financialProduct_tableview;
    @FXML
    private ListView financialProduct_listview;
    @FXML
    private LineChart financialProduct_linechart;
    @FXML
    private PieChart wallet_piechart;


    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        Wallet_label.setText(bundle.getString("WalletTitle_label"));
        YourInsurances_label.setText(bundle.getString("YourInsurances_label"));
        insuranceType_btn.setText(bundle.getString("InsuranceType_btn"));
        insuranceQuote_btn.setText(bundle.getString("InsuranceQuote_btn"));
        ask_insuranceQuote_btn.setText(bundle.getString("Ask_insuranceQuote_btn"));
        Day_btn.setText(bundle.getString("Day_btn"));
        Week_btn.setText(bundle.getString("Week_btn"));
        Month_btn.setText(bundle.getString("Month_btn"));
        Year_btn.setText(bundle.getString("Year_btn"));
        From_label.setText(bundle.getString("From_label"));
        To_label.setText(bundle.getString("To_label"));
        Graph_btn.setText(bundle.getString("Graph_btn"));
        List_btn.setText(bundle.getString("List_btn"));
        Tab_btn.setText(bundle.getString("Tab_btn"));
        Export_btn.setText(bundle.getString("Export_btn"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = DashboardController.bundle;
        setText();
        loadInsuranceContracts();
    }

    /**
     * Load Insurance Contracts
     */
    private void loadInsuranceContracts(){
        List<InsuranceContract> insuranceContracts = insuranceService.
                getInsuranceByWallet();
        if (insuranceContracts != null) {
            int index = 0;
            for (InsuranceContract insuranceContract : insuranceContracts) {
                createVisualInsuranceContracts(insuranceContract, index);
                index += 2;
            }
        }
    }

    /**
     * Open a window for Insurance Contract Type
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_insuranceType(MouseEvent event) {
//       DynamicViews.loadBorderCenter("extension5/Client-InsuranceType");
    }

    /**
     * Open a window for Insurance Type Quote
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_insuranceQuote(MouseEvent event) {
//        DynamicViews.loadBorderCenter("extension5/Client-InsuranceTypeQuote");
    }


    private void createVisualInsuranceContracts(InsuranceContract insuranceContract, int index) {
        HBox hBox = new HBox();
        // Create the structure of GridPane defined in Client-Dashboard-Insurance.fxml
        hBox.setStyle("-fx-border-radius: 10; -fx-padding: 10; -fx-border-width: 2; -fx-border-color: #000;");
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setPrefHeight(120);
        hBox.setPrefWidth(983);
        Product_gridPane.getChildren().add(hBox);

        // Insurance type
        Label nameLab = new Label(insuranceContract.getInsuranceType().name());
        nameLab.setAlignment(javafx.geometry.Pos.CENTER);
        nameLab.setContentDisplay(ContentDisplay.CENTER);
        nameLab.setFont(new Font(20));
        nameLab.setPrefWidth(250);
        hBox.getChildren().add(nameLab);

        // Holders
        VBox vBoxHolders = new VBox();
        vBoxHolders.setPrefWidth(250);
        vBoxHolders.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.getChildren().add(vBoxHolders);

        Label holders = new Label("Titulaires");
        // TODO : Traduire
        holders.setAlignment(javafx.geometry.Pos.CENTER);
        holders.setContentDisplay(ContentDisplay.CENTER);
        holders.setFont(new Font(20));
        holders.setPrefWidth(250);
        List<FinancialProductHolder> holdersList = insuranceContract.getFinancialProductHolders();
        vBoxHolders.getChildren().add(holders);
        for (FinancialProductHolder holder : holdersList) {
            Label holderName = new Label(holder.getFirstName() + " " + holder.getName());
            holderName.setAlignment(javafx.geometry.Pos.CENTER);
            holderName.setContentDisplay(ContentDisplay.CENTER);
            holderName.setFont(new Font(20));
            holderName.setPrefWidth(250);
            vBoxHolders.getChildren().add(holderName);
        }

        // Amount
        Label amountLab = new Label(insuranceContract.getAnnualPremium() + " €");
        amountLab.setAlignment(javafx.geometry.Pos.CENTER);
        amountLab.setContentDisplay(ContentDisplay.CENTER);
        amountLab.setFont(new Font(20));
        amountLab.setPrefWidth(250);
        hBox.getChildren().add(amountLab);

        // Date
        VBox vBoxDate = new VBox();
        vBoxDate.setPrefWidth(250);
        vBoxDate.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.getChildren().add(vBoxDate);
        Label dateLab = new Label("Date de renouvellement");
        // TODO : Traduire
        dateLab.setAlignment(javafx.geometry.Pos.CENTER);
        dateLab.setContentDisplay(ContentDisplay.CENTER);
        dateLab.setFont(new Font(20));
        dateLab.setPrefWidth(250);
        vBoxDate.getChildren().add(dateLab);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(insuranceContract.getRenewalDate());
        Label dateValue = new Label(date);
        dateValue.setAlignment(javafx.geometry.Pos.CENTER);
        dateValue.setContentDisplay(ContentDisplay.CENTER);
        dateValue.setFont(new Font(20));
        dateValue.setPrefWidth(250);
        vBoxDate.getChildren().add(dateValue);


        HBox hbox2 = new HBox();
        hbox2.setNodeOrientation(NodeOrientation.INHERIT);
        hbox2.setPrefHeight(120);
        hbox2.setPrefWidth(170);

        ImageView imgViewInsurance = new ImageView(getClass().getResource("/images/icons/source_icons_eye-empty.jpg").toString());
        imgViewInsurance.setOnMouseClicked(this::display_product);
        imgViewInsurance.setFitHeight(36);
        imgViewInsurance.setFitWidth(36);
        imgViewInsurance.setPreserveRatio(true);
        imgViewInsurance.setPickOnBounds(true);
        hbox2.getChildren().add(imgViewInsurance);

        // View button
        ImageView imgDeleteProduct = new ImageView(getClass().getResource("/images/icons/tabler-icon-arrows-right-left.jpg").toString());
        imgDeleteProduct.setOnMouseClicked(this::make_transaction);
        imgViewInsurance.setFitHeight(36);
        imgViewInsurance.setFitWidth(36);
        imgViewInsurance.setPreserveRatio(true);
        imgViewInsurance.setPickOnBounds(true);
        hbox2.getChildren().add(imgDeleteProduct);

        hBox.getChildren().add(hbox2);

        GridPane.setRowIndex(hBox, index);

    }

    /**
     * Open a window for display insurance contract
     * @param event the click of the mouse on the button
     */
    @FXML
    private void display_product(MouseEvent event) {
        ImageView img = (ImageView) event.getSource();
        InsuranceContract insurance = (InsuranceContract) img.getUserData();

        insuranceService.setCurrentInsurance(insurance);
        DynamicViews.loadBorderCenter("extension5/Client-ViewInsuranceContract");
    }

    /**
     * Open a window for make transaction
     * @param event the click of the mouse on the button
     */
    @FXML
    private void make_transaction(MouseEvent event) {
//        ImageView img = (ImageView) event.getSource();
//        InsuranceContract insurance = (InsuranceContract) img.getUserData();
//
//        insuranceService.setCurrentInsurance(insurance);
//        DynamicViews.loadBorderCenter("Client-Pay-Insurance");
    }


    /**
     * Changes the time granularity to day
     * @param event the click of the mouse on the button
     */
    @FXML
    private void day(MouseEvent event) {

    }

    /**
     * Changes the time granularity to week
     * @param event the click of the mouse on the button
     */
    @FXML
    private void week(MouseEvent event) {

    }

    /**
     * Changes the time granularity to month
     * @param event the click of the mouse on the button
     */
    @FXML
    private void month(MouseEvent event) {

    }

    /**
     * Changes the time granularity to year
     * @param event the click of the mouse on the button
     */
    @FXML
    private void year(MouseEvent event) {

    }

    /**
     * Displays data in graphical form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void graph(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(false);
        financialProduct_listview.visibleProperty().setValue(false);
        financialProduct_linechart.visibleProperty().setValue(true);
    }

    /**
     * Displays data in list form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void list(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(false);
        financialProduct_listview.visibleProperty().setValue(true);
        financialProduct_linechart.visibleProperty().setValue(false);
    }

    /**
     * Displays data in table form
     * @param event the click of the mouse on the button
     */
    @FXML
    private void tableview(MouseEvent event) {
        financialProduct_tableview.visibleProperty().setValue(true);
        financialProduct_listview.visibleProperty().setValue(false);
        financialProduct_linechart.visibleProperty().setValue(false);
    }

    /**
     * Export all data from the client's wallets
     * @param event the click of the mouse on the button
     */
    @FXML
    private void export(MouseEvent event) {

    }

}
