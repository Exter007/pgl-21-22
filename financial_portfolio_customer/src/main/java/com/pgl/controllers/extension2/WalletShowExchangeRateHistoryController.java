package com.pgl.controllers.extension2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import com.pgl.utils.Validators;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class WalletShowExchangeRateHistoryController implements Initializable {

    static UserService userService = new UserService();
    static ResourceBundle bundle;

    WalletService walletService = new WalletService();

    @FXML
    private ChoiceBox<String> temporal_granularity;

    @FXML
    private ChoiceBox<String> from_currency;

    @FXML
    private ChoiceBox<String> to_currency;

    @FXML
    private LineChart<String, Number> exchange_rate_line_chart;

    @FXML
    private TextField from;

    @FXML
    private TextField to;


    /**
     * Initialize all labels and fields of the interface according to the chosen language
     */
    private void setText(){

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
        loadChoiceBoxes();
        setActionForChoiceBoxes();
        setActionForTextFields();
    }

    private void convert(String base_currency, String destination_currency, TextField base, TextField destination) throws IOException {
        double result;
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/5a247352b50730d6ae614ea6/pair/"
                +base_currency+"/"+destination_currency;

        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        // Accessing object
        String req_result = jsonobj.get("result").getAsString();
        if(req_result.equals("success")){
            result=jsonobj.get("conversion_rate").getAsDouble();
            destination.setText(Double.toString(result * Double.parseDouble(base.getText())));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("error26"));
            alert.showAndWait();
        }
    }

    private void loadChoiceBoxes(){
        ObservableList<String> currencies = FXCollections.observableArrayList("EUR", "USD", "GBP", "JPY", "RWF");
        from_currency.setItems(currencies);
        to_currency.setItems(currencies);
    }

    private void setActionForChoiceBoxes(){
        from_currency.setOnAction((event) -> {
            from.clear();
            if(from_currency.getValue() != null){
                from.setText("1");
                if(to_currency.getValue() != null){
                    try {
                        convert(from_currency.getValue(), to_currency.getValue(), from, to);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        to_currency.setOnAction((event) -> {
            Double result = null;
            if(from_currency.getValue()==null || to_currency.getValue()==null)
                to.clear();
            else{ //the base (from_currency) and the destination (to_currency) are defined(from_currency)
                if(from.getText()==null||from.getText().isEmpty())
                    from.setText("1");
                if (from_currency.getValue().equals(to_currency.getValue()))
                    to.setText(from.getText());
                else{//the base (from_currency) and the destination (to_currency) have a different value
                    try {
                        convert(from_currency.getValue(), to_currency.getValue(), from, to);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setActionForTextFields(){
        from.setOnAction((event) -> {
            to.clear();
            if(from_currency.getValue() == null|| !Validators.isNumeric(from.getText()))
                from.clear();
            else{ //from.getText() is numeric
                try {
                    convert(from_currency.getValue(), to_currency.getValue(), from, to);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        to.setOnAction((event) -> {
            if(from_currency.getValue() == null|| to_currency.getValue()==null || !Validators.isNumeric(to.getText()))
                to.clear();
            else{ //to.getText() is numeric
                try {
                    convert(to_currency.getValue(), from_currency.getValue(), to, from);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
