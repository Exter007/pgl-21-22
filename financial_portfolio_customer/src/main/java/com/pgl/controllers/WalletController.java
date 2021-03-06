package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.BankAccountService;
import com.pgl.services.FinancialProductService;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import com.pgl.utils.Exporter;
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

public class WalletController implements Initializable {

    UserService userService = new UserService();
    static ResourceBundle bundle;

    FinancialProductService financialProductService = new FinancialProductService();
    BankAccountService bankAccountService = new BankAccountService();
    WalletService walletService = new WalletService();

    @FXML
    private Label Wallet_label;
    @FXML
    private Label YourFinancialProducts_label;
    @FXML
    private GridPane Product_gridPane;
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
        YourFinancialProducts_label.setText(bundle.getString("YourFinancialProducts_label"));
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
        if(userService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(userService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
        setText();
        List<FinancialProduct> fp = financialProductService.getFinancialProductsByWallet();
        loadFinancialProducts(fp);
        setPieChart(fp);
        ObservableList<String> formats = FXCollections.observableArrayList(".csv", ".json");
        export_format.setItems(formats);
    }

    private void setPieChart(List<FinancialProduct> list) {
        float current_value = 0;
        float saving_value = 0;
        float term_value = 0;
        float young_value = 0;
        for (FinancialProduct fp: list) {
            if(fp.getClass().equals(CurrentAccount.class))
                current_value = ((CurrentAccount) fp).getAmount();
            else if(fp.getClass().equals(SavingsAccount.class))
                saving_value = ((SavingsAccount) fp).getAmount();
            else if(fp.getClass().equals(TermAccount.class))
                term_value = ((TermAccount) fp).getAmount();
            else if(fp.getClass().equals(YoungAccount.class))
                young_value = ((YoungAccount) fp).getAmount();
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Current Account", current_value),
                        new PieChart.Data("Saving Account", saving_value),
                        new PieChart.Data("Term Account", term_value),
                        new PieChart.Data("Young Account", young_value));
        wallet_piechart.setData(pieChartData);
    }

    private void loadFinancialProducts(List<FinancialProduct> fp){
        if (fp != null) {
            int index = 0;
            for (FinancialProduct financialProduct : fp) {
                createVisualFinancialProduct(financialProduct, index);
                index += 2;
            }
        } else {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, "Financial products not found");
        }
    }

    /**
     * Back to previous window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void goBack(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Dashboard.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window for make transfer
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_transfer(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Dashboard-Transfer");
    }

    @FXML
    private void show_exchange_rate_history(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/extension2/Client-Wallet-ShowExchangeRateHistory.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to request a card linked to an account
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_card(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/extension1/Client-Wallet-AskCard.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to pay with you card
     * @param event the click of the mouse on the button
     */
    @FXML
    private void pay_byCard(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/extension1/Client-Wallet-PayByCard.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window allowing you to request a financial product from an institution
     * @param event the click of the mouse on the button
     */
    @FXML
    private void ask_financialProduct(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-AskFinancialProduct.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window showing the hidden products
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_hideProducts(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Wallet-HideProducts.fxml"));
            Stage newWindow = new Stage();
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            GlobalStage.setStage(newWindow);

        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window showing notifications of the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void show_notifications(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Wallet-Notifications");
    }

    /**
     * Open a window asking for a hiding confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void hide_product(MouseEvent event) {
        DynamicViews.loadBorderCenter("Client-Wallet-HideConfirmation");
    }

    /**
     * Open a window for make transaction
     * @param event the click of the mouse on the button
     */
    @FXML
    private void make_transaction(MouseEvent event) {
        ImageView img = (ImageView) event.getSource();
        FinancialProduct product = (FinancialProduct) img.getUserData();

        BankAccount account = (BankAccount) product;
        bankAccountService.setCurrentBankAccount(account);
        bankAccountService.setEdit(true);

        if (account.getNature().equals(BankAccount.ACCOUNT_NATURE.CURRENT_ACCOUNT)
                || account.getNature().equals(BankAccount.ACCOUNT_NATURE.YOUNG_ACCOUNT))
        {
            if (account.getTransferAccess().equals(FinancialProduct.TRANSFER_ACCESS.AUTHORIZED)){
                DynamicViews.loadBorderCenter("Client-Dashboard-Transfer");
            }else {
                DynamicViews.loadBorderCenter("Client-Wallet-AskTransferConfirmation");
            }
        }else {
            DynamicViews.loadBorderCenter("Client-Dashboard-Recharge");
        }
    }

    /**
     * Open a manage card window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void card_settings(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/extension1/Client-Card-Manage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a history card window
     * @param event the click of the mouse on the button
     */
    @FXML
    private void card_history(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/extension1/Client-Card-History.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a window showing Insurance Contracts of the user
     * @param event the click of the mouse on the button
     */
    @FXML
    private void on_insurances(MouseEvent event) {
        DynamicViews.loadBorderCenter("extension5/Client-Dashboard-Insurances");
    }

    /*
     * Open a window allowing you to request transfer from an institution
     * @param event the click of the mouse on the button

    @FXML
   private void ask_transfer(MouseEvent event) {
        ImageView img = (ImageView) event.getSource();
        financialProductService.setCurrentProduct((FinancialProduct) img.getUserData());
        DynamicViews.loadBorderCenter("Client-Wallet-AskTransferConfirmation");
    }*/

    /**
     * Open a window asking for a delete confirmation
     * @param event the click of the mouse on the button
     */
    @FXML
    private void delete_financialProduct(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Client-Wallet-DeleteConfirmation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void export(MouseEvent event) throws IOException {
        if(walletService.getCurrentWallet() != null){
            //take the date so each time the user downloads a CSV file, its name is different
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentDateTime = dateFormatter.format(new Date());
            List<FinancialProduct> financialProducts = financialProductService.getFinancialProductsByWallet();

            // ajouter des configurations diff??rentes(avec diff??rent crit??re)

            Exporter.ActionExport(currentDateTime, financialProducts, bundle, export_format, false);
        }
    }

    private void createVisualFinancialProduct(FinancialProduct financialProduct, int index) {
        HBox hBox = new HBox();
        // Create the structure of GridPane defined in Client-Wallet.fxml
        hBox.setStyle("-fx-border-radius: 10; -fx-padding: 10; -fx-border-width: 2; -fx-border-color: #000;");
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setPrefHeight(120);
        hBox.setPrefWidth(983);
        Product_gridPane.getChildren().add(hBox);

        Label nameLab = new Label(financialProduct.getClass().getSimpleName());
        nameLab.setAlignment(javafx.geometry.Pos.CENTER);
        nameLab.setContentDisplay(ContentDisplay.CENTER);
        nameLab.setFont(new Font(20));
        nameLab.setPrefWidth(250);
        hBox.getChildren().add(nameLab);

        VBox vBoxHolders = new VBox();
        vBoxHolders.setPrefWidth(250);
        vBoxHolders.setAlignment(javafx.geometry.Pos.CENTER);

        hBox.getChildren().add(vBoxHolders);

        Label holders = new Label("Titulaires");
        holders.setAlignment(javafx.geometry.Pos.CENTER);
        holders.setContentDisplay(ContentDisplay.CENTER);
        holders.setFont(new Font(20));
        holders.setPrefWidth(250);
        List<FinancialProductHolder> holdersList = financialProduct.getFinancialProductHolders();
        vBoxHolders.getChildren().add(holders);
        for (FinancialProductHolder holder : holdersList) {
            Label holderName = new Label(holder.getFirstName() + " " + holder.getName());
            holderName.setAlignment(javafx.geometry.Pos.CENTER);
            holderName.setContentDisplay(ContentDisplay.CENTER);
            holderName.setFont(new Font(20));
            holderName.setPrefWidth(250);
            vBoxHolders.getChildren().add(holderName);
        }
        Label label;
        if(financialProduct.getClass().equals(CurrentAccount.class)
                || financialProduct.getClass().equals(YoungAccount.class))
        {
            label = new Label("Virement");
        }else {
            label = new Label("Recharge/D??charge");
        }

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setFont(new Font(20));
        if ((financialProduct.getClass().equals(CurrentAccount.class)
                || financialProduct.getClass().equals(YoungAccount.class))
                && !financialProduct.getTransferAccess().equals(FinancialProduct.TRANSFER_ACCESS.AUTHORIZED)) {
            label.setStyle("-fx-text-fill: #ff0000;");
        } else {
            label.setStyle("-fx-text-fill: #008000;");
        }
        label.setPrefWidth(250);
        hBox.getChildren().add(label);
        if (financialProduct.getProductType().toString().equals(FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT.toString())) {
            BankAccount bk = (BankAccount) financialProduct;
            Label amount = new Label(bk.getAmount() + " ???");
            amount.setAlignment(javafx.geometry.Pos.CENTER);
            amount.setContentDisplay(ContentDisplay.CENTER);
            amount.setFont(new Font(20));
            amount.setPrefWidth(250);
            hBox.getChildren().add(amount);
        } else {
            Label amount = new Label("-");
            amount.setAlignment(javafx.geometry.Pos.CENTER);
            amount.setContentDisplay(ContentDisplay.CENTER);
            amount.setFont(new Font(20));
            amount.setPrefWidth(250);
            hBox.getChildren().add(amount);
        }

        HBox hbox2 = new HBox();
        hbox2.setNodeOrientation(NodeOrientation.INHERIT);
        hbox2.setPrefHeight(120);
        hbox2.setPrefWidth(170);

        ImageView imgHideProduct = new ImageView(getClass().getResource("/images/icons/source_icons_eye-empty.jpg").toString());
        imgHideProduct.setOnMouseClicked(this::hide_product);
        imgHideProduct.setFitHeight(36);
        imgHideProduct.setFitWidth(36);
        imgHideProduct.setPreserveRatio(true);
        imgHideProduct.setPickOnBounds(true);
        hbox2.getChildren().add(imgHideProduct);

        if(financialProduct.getProductType().equals(FinancialProduct.PRODUCT_TYPE.BANK_ACCOUNT)){
            ImageView imgTransactionProduct = new ImageView(getClass().getResource("/images/icons/tabler-icon-arrows-right-left.jpg").toString());
            imgTransactionProduct.setOnMouseClicked(this::make_transaction);
            imgTransactionProduct.setFitHeight(36);
            imgTransactionProduct.setFitWidth(36);
            imgTransactionProduct.setPreserveRatio(true);
            imgTransactionProduct.setPickOnBounds(true);
            imgTransactionProduct.setLayoutX(20);
            imgTransactionProduct.setLayoutY(50);
            imgTransactionProduct.setVisible(true);
            imgTransactionProduct.setUserData(financialProduct);
            hbox2.getChildren().add(imgTransactionProduct);
        }

        ImageView imgDeleteProduct = new ImageView(getClass().getResource("/images/icons/source_icons_trash.jpg").toString());
        imgDeleteProduct.setOnMouseClicked(this::delete_financialProduct);
        imgDeleteProduct.setFitHeight(36);
        imgDeleteProduct.setFitWidth(36);
        hbox2.getChildren().add(imgDeleteProduct);

        hBox.getChildren().add(hbox2);
        GridPane.setRowIndex(hBox, index);

    }
}
