package com.pgl.controllers;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.Notification;
import com.pgl.services.NotificationService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.inject.Inject;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class WalletNotificationsController implements Initializable {

    NotificationService notificationService = new NotificationService();
    static ResourceBundle bundle;

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

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        Title_label.setText(bundle.getString("NotificationsTitle_label"));
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
        // Adding the request transfer notifications
        List<Notification> notifications = notificationService.getUnreadNotificationsByClient();
        for (Notification notification : notifications) {
            createNotification(notification.getFinancialInstitution().getName(),
                    notification.getMessage(),
                    notification.getCreationDate(),
                    notification,
                    this::read_request
            );
        }
        vbox.setPadding(new Insets(0, 50, 60, 50));
    }


    /**
     * Mark Notification as read
     * @param event The event of the mouse click on the button
     */
    @FXML
    private void read_request(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert(true);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ... user chose OK
            // Get userData from the button
            Button button = (Button) event.getSource();
            Notification notification = (Notification) button.getUserData();

            // Update reading notification
            notification.setStatus(Notification.NOTIFICATION_STATUS.READ);
            notification.setModificationDate(new Date());
            notification = notificationService.save(notification);

            if (notification != null){
                // Update the notification from the vbox
               reloadInterface();
            }
        }
    }


    /**
     * This method create a notification and add it to the vbox
     * @param name The name of the client
     * @param subject The subject of the notification
     * @param creationDate The creation date of the notification
     * @param userData The notification object (RequestWallet, RequestTransfer, ... object for example)
     */
    private void createNotification(String name, String subject, Date creationDate, Object userData, EventHandler<ActionEvent> readEventHandler) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-border-radius: 10; -fx-padding: 10; -fx-border-width: 1; -fx-border-color: #000000;");
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setPrefHeight(160);
        hBox.setPrefWidth(1197);

        Label nameLab = new Label(name);
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

        accept = new Button(bundle.getString("MarkRead_btn"));
        accept.setPrefWidth(215);
        accept.setAlignment(javafx.geometry.Pos.CENTER);
        accept.setContentDisplay(ContentDisplay.CENTER);
        accept.setStyle("-fx-text-fill: #008000;");
        accept.setPadding(new Insets(15, 0, 15, 0));

        accept.setUserData(userData);
        accept.setOnAction(readEventHandler);

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
        vBoxAsk.getChildren().addAll(accept);
        hBox.getChildren().addAll(vBoxSubject, vBoxDate, vBoxAsk);
        vbox.getChildren().add(hBox);
    }

    private Optional<ButtonType> showConfirmationAlert(boolean isAccepted) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(isAccepted) {
            alert.setContentText(bundle.getString("question1"));
        }
        return alert.showAndWait();
    }

    /**
     * Reload interface
     */
    private void reloadInterface() {
        DynamicViews.loadBorderCenter("Client-Wallet-Notifications");
    }

    /**
     * Remove the notification from the vbox
     * @param button the button that is clicked
     */
    private void removeNotification(Button button) {
        vbox.getChildren().remove(button.getParent().getParent());
    }
}
