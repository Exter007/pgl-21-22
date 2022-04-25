package com.pgl.controllers.extension3;

import com.pgl.helpers.DynamicViews;
import com.pgl.models.ScheduledTransaction;
import com.pgl.services.UserService;
import com.pgl.services.extension3.ScheduleTransactionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.pgl.helpers.DynamicViews.border_pane;

public class ScheduleTransaction implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    private ScheduleTransactionService scheduleTransactionService = new ScheduleTransactionService();

    @FXML
    private Label title;
    @FXML
    private Text every_title;
    @FXML
    private TextField every;
    @FXML
    private CheckBox activate_every;
    @FXML
    private Text inferior_at_title;
    @FXML
    private TextField inferior_at;
    @FXML
    private CheckBox activate_inferior;
    @FXML
    private Text superior_at_title;
    @FXML
    private TextField superior_at;
    @FXML
    private CheckBox activate_superior;
    @FXML
    private Button confirm;




    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText() {
        title.setText(bundle.getString("schedule_transaction"));
        every_title.setText(bundle.getString("every"));
        inferior_at_title.setText(bundle.getString("inferior_at"));
        superior_at_title.setText(bundle.getString("superior_at"));
        confirm.setText(bundle.getString("confirm"));
        every.setPromptText(bundle.getString("every_placeholder"));
        inferior_at.setPromptText(bundle.getString("at_placeholder"));
        superior_at.setPromptText(bundle.getString("at_placeholder"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(userService.getCurrentUser().getLanguage().equals("fr")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.FRENCH);
        }else if(userService.getCurrentUser().getLanguage().equals("en")){
            bundle = ResourceBundle.getBundle("properties.langue", Locale.ENGLISH);
        }else{
            bundle = null;
        }
    }


    /**
     *  Method called when the confirm button is clicked
     *  It creates a new scheduled transaction and adds it to the current
     */
    @FXML
    private void confirm_scheduling(){
        ScheduledTransaction scheduledTransaction = new ScheduledTransaction();
        if(activate_every.isSelected() && !every.getText().isEmpty()){
            scheduledTransaction.setEvery(String.valueOf(Integer.parseInt(every.getText())));
        }
        if(activate_inferior.isSelected() && !inferior_at.getText().isEmpty()){
            scheduledTransaction.setInferiorAt(Float.parseFloat(inferior_at.getText()));
        }
        if(activate_superior.isSelected() && !superior_at.getText().isEmpty()){
            scheduledTransaction.setSuperiorAt(Float.parseFloat(superior_at.getText()));
        }
        if(!every.getText().isEmpty()){
            scheduleTransactionService.setScheduledTransaction(scheduledTransaction);
            DynamicViews.loadBorderCenter(border_pane, "Client-Dashboard-Transfer");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("invalid_every"));
            alert.showAndWait();
        }
    }

}
