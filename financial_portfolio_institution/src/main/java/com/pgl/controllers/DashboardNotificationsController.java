package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.*;
import com.pgl.services.*;
import com.pgl.utils.GlobalStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardNotificationsController implements Initializable {

    UserService userService = new UserService();
    static ResourceBundle bundle;

    RequestWalletService requestWalletService = new RequestWalletService();
    RequestTransferService requestTransferService = new RequestTransferService();

    @FXML
    private Label name;
    @FXML
    private Label Title_label;
    @FXML
    private Label subject;
    @FXML
    private Label date;
    @FXML
    private VBox vbox;
    @FXML
    private Button accept;
    @FXML
    private Button decline;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        Title_label.setText(bundle.getString("TitleNotifications_label"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adding the request wallet notifications
        bundle = DashboardController.bundle;
        setText();
        loadNotifications();
    }

    private void loadNotifications(){
        List<RequestWallet> requestWalletList = requestWalletService.getPendingRequestWalletByInstitution();
        for (RequestWallet requestWallet : requestWalletList) {
            createNotification(requestWallet.getApplicationClient().getName(),
                    requestWallet.getApplicationClient().getFirstName(),
                    bundle.getString("AskCreationText"),
                    requestWallet.getCreationDate(),
                    requestWallet,
                    this::accept_wallet_request,
                    this::refuse_wallet_request
            );
        }

        // Adding the request transfer notifications
        List<RequestTransfer> requestTransferList = requestTransferService.getPendingRequestTransferByInstitution();
        for (RequestTransfer requestTransfer : requestTransferList) {
            createNotification(requestTransfer.getApplicationClient().getName(),
                    requestTransfer.getApplicationClient().getFirstName(),
                    bundle.getString("AskCreationText"),
                    requestTransfer.getCreationDate(),
                    requestTransfer,
                    this::accept_transfer_request,
                    this::refuse_transfer_request);
        }
        vbox.setPadding(new Insets(0, 50, 60, 50));
    }

    /**
     * Accept a client RequestWallet
     * @param event The event of the mouse click on the button
     */
    @FXML
    private void accept_wallet_request(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert(true);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ... user chose OK
            // Get userData from the button
            Button button = (Button) event.getSource();
            RequestWallet requestWallet = (RequestWallet) button.getUserData();
            try {
                // Update the requested wallet to accepted
                requestWalletService.acceptRequestWallet(requestWallet);

                // Remove the notification from the vbox
                removeNotification(button);
            } catch (Exception e) {
                System.out.println("Error while updating request wallet");
            }
        }
    }

    /**
     * Refuse a client wallet request
     * @param event The event of the mouse click on the button
     */
    @FXML
    private void refuse_wallet_request(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert(false);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ... user chose OK
            // Get userData from the button
            Button button = (Button) event.getSource();
            RequestWallet requestWallet = (RequestWallet) button.getUserData();
            try {
                // Update the requested wallet to refused
                requestWalletService.refuseRequestWallet(requestWallet);
                // Remove the notification from the vbox
                removeNotification(button);
            } catch (Exception e) {
                System.out.println("Error while updating request wallet");
            }
        }
    }

    /**
     * Accept a client RequestTransfer
     * @param event The event of the mouse click on the button
     */
    @FXML
    private void accept_transfer_request(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert(true);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ... user chose OK
            // Get userData from the button
            Button button = (Button) event.getSource();
            RequestTransfer requestTransfer = (RequestTransfer) button.getUserData();
            try {
                // Update the requested wallet to accepted
                requestTransferService.acceptRequestTransfer(requestTransfer);
                // Remove the notification from the vbox
                removeNotification(button);
            } catch (Exception e) {
                System.out.println("Error while updating transfer request");
            }
        }
    }

    /**
     * Refuse a client wallet request
     * @param event The event of the mouse click on the button
     */
    @FXML
    private void refuse_transfer_request(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert(false);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ... user chose OK
            // Get userData from the button
            Button button = (Button) event.getSource();
            RequestTransfer requestTransfer = (RequestTransfer) button.getUserData();
            try {
                // Update the requested wallet to accepted
                requestTransferService.refuseRequestTransfer(requestTransfer);
                // Remove the notification from the vbox
                removeNotification(button);
            } catch (Exception e) {
                System.out.println("Error while updating request wallet");
            }
        }
    }


    /**
     * This method create a notification and add it to the vbox
     * @param name The name of the client
     * @param firstname The firstname of the client
     * @param subject The subject of the notification
     * @param creationDate The creation date of the notification
     * @param userData The notification object (RequestWallet, RequestTransfer, ... object for example)
     * @param acceptEventHandler The method to call when the user click on the accept button
     * @param declineEventHandler The method to call when the user click on the decline button
     */
    private void createNotification(String name, String firstname, String subject, Date creationDate, Object userData, EventHandler<ActionEvent> acceptEventHandler, EventHandler<ActionEvent> declineEventHandler) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-border-radius: 10; -fx-padding: 10; -fx-border-width: 1; -fx-border-color: #000000;");
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setPrefHeight(160);
        hBox.setPrefWidth(1197);

        Label nameLab = new Label(name + " " + firstname);
        nameLab.setAlignment(javafx.geometry.Pos.CENTER);
        nameLab.setContentDisplay(ContentDisplay.CENTER);
        nameLab.setFont(new Font(24));
        nameLab.setPrefWidth(500);
        hBox.getChildren().add(nameLab);

        VBox vBoxSubject = new VBox();
        vBoxSubject.setPrefWidth(500);
        vBoxSubject.setAlignment(javafx.geometry.Pos.CENTER);
        VBox vBoxDate = new VBox();
        vBoxDate.setPrefWidth(500);
        vBoxDate.setAlignment(javafx.geometry.Pos.CENTER);
        VBox vBoxAsk = new VBox();
        vBoxAsk.setPrefWidth(500);
        vBoxAsk.setAlignment(javafx.geometry.Pos.CENTER);
        vBoxAsk.setSpacing(10);

        Label subjectLab = new Label(subject);
        subjectLab.setAlignment(javafx.geometry.Pos.CENTER);
        subjectLab.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        subjectLab.setContentDisplay(ContentDisplay.CENTER);
        subjectLab.setFont(new Font("System Bold", 24));
        subjectLab.setWrapText(true);
        subjectLab.setPrefWidth(500);

        String tempDate = new SimpleDateFormat("dd/MM/yyyy").format(creationDate);
        date = new Label(tempDate);
        date.setAlignment(javafx.geometry.Pos.CENTER);
        date.setContentDisplay(ContentDisplay.CENTER);
        date.setFont(new Font("System Bold", 24));
        date.setPrefWidth(200);

        accept = new Button(bundle.getString("Accept_btn"));
        accept.setPrefWidth(215);
        accept.setAlignment(javafx.geometry.Pos.CENTER);
        accept.setContentDisplay(ContentDisplay.CENTER);
        accept.setStyle("-fx-text-fill: #008000;");
        accept.setPadding(new Insets(15, 0, 15, 0));

        decline = new Button(bundle.getString("Refuse_btn"));
        decline.setPrefWidth(215);
        decline.setAlignment(javafx.geometry.Pos.CENTER);
        decline.setContentDisplay(ContentDisplay.CENTER);
        decline.setStyle("-fx-text-fill: #ff0000;");
        decline.setPadding(new Insets(15, 0, 15, 0));

        accept.setUserData(userData);
        accept.setOnAction(acceptEventHandler);
        decline.setUserData(userData);
        decline.setOnAction(declineEventHandler);

        Label headerSubject = new Label(bundle.getString("Subject_label"));
        headerSubject.setFont(new Font(24));
        headerSubject.setAlignment(javafx.geometry.Pos.CENTER);
        headerSubject.setContentDisplay(ContentDisplay.CENTER);

        Label headerDate = new Label(bundle.getString("Date_label"));
        headerDate.setFont(new Font(24));
        headerDate.setAlignment(javafx.geometry.Pos.CENTER);
        headerDate.setContentDisplay(ContentDisplay.CENTER);

        vBoxSubject.getChildren().addAll(headerSubject, subjectLab);
        vBoxDate.getChildren().addAll(headerDate, date);
        vBoxAsk.getChildren().addAll(accept, decline);
        hBox.getChildren().addAll(vBoxSubject, vBoxDate, vBoxAsk);
        vbox.getChildren().add(hBox);
    }

    private Optional<ButtonType> showConfirmationAlert(boolean isAccepted) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(isAccepted) {
            alert.setContentText(bundle.getString("question1"));
        } else {
            alert.setContentText(bundle.getString("question2"));
        }
        return alert.showAndWait();
    }
    /**
     * Remove the notification from the vbox
     * @param button the button that is clicked
     */
    private void removeNotification(Button button) {
        vbox.getChildren().remove(button.getParent().getParent());
    }
}
