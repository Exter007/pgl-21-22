package com.pgl.controllers.extension1;

import com.pgl.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardHistoryController implements Initializable {

    @Inject
    static UserService userService = new UserService();
    static ResourceBundle bundle;

    @FXML
    private Label CardNumber_label;
    @FXML
    private Label From_label;
    @FXML
    private Label To_label;
    @FXML
    private Button Daily_btn;
    @FXML
    private Button Week_btn;
    @FXML
    private Button Month_btn;
    @FXML
    private Button Year_btn;
    @FXML
    private Button Export_btn;
    @FXML
    private Button Graph_btn;
    @FXML
    private Button List_btn;
    @FXML
    private Button Tab_btn;
    @FXML
    private DatePicker From_datepicker;
    @FXML
    private DatePicker To_datepicker;
    @FXML
    private ChoiceBox Format_choicebox;

    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){
        CardNumber_label.setText(bundle.getString("CardNumber_label"));
        From_label.setText(bundle.getString("From_label"));
        To_label.setText(bundle.getString("To_label"));
        Daily_btn.setText(bundle.getString("Day_btn"));
        Week_btn.setText(bundle.getString("Week_btn"));
        Month_btn.setText(bundle.getString("Month_btn"));
        Year_btn.setText(bundle.getString("Year_btn"));
        Export_btn.setText(bundle.getString("Export_btn"));
        Graph_btn.setText(bundle.getString("Graph_btn"));
        List_btn.setText(bundle.getString("List_btn"));
        Tab_btn.setText(bundle.getString("Tab_btn"));
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
    }

    @FXML
    private void day(MouseEvent event) {

    }

    @FXML
    private void week(MouseEvent event) {

    }

    @FXML
    private void month(MouseEvent event) {

    }

    @FXML
    private void year(MouseEvent event) {

    }

    @FXML
    private void export(MouseEvent event) {

    }

    @FXML
    private void graph(MouseEvent event) {

    }

    @FXML
    private void list(MouseEvent event) {

    }

    @FXML
    private void tab(MouseEvent event) {

    }
}