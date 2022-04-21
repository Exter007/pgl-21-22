package com.pgl.controllers.extension2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pgl.services.UserService;
import com.pgl.services.WalletService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
        ObservableList<String> currencies = FXCollections.observableArrayList("EUR", "USD", "GBP", "JPY", "RWF");
        from_currency.setItems(currencies);
        from_currency.setOnAction((event) -> {
            if(from_currency.getValue() == null)
                from.clear();
            else{
                from.setText("1");
            }
        });
        to_currency.setItems(currencies);
        to_currency.setOnAction((event) -> {
            Double result = null;
            if(from_currency.getValue().isEmpty() || to_currency.getValue().isEmpty() || from.getText().isEmpty())
                to.clear();
            else{
                if (from_currency.getValue().equals(to_currency.getValue()))
                    to.setText(from.getText());
                else{
                    try {
                        result = getLatestRate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(result == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(bundle.getString("error23"));
                        alert.showAndWait();
                    }
                    else
                        to.setText(Double.toString(result * Double.parseDouble(from.getText())));
                }
            }
        });
    }

    private Double getLatestRate() throws IOException {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/5a247352b50730d6ae614ea6/pair/"
                +from_currency.getValue()+"/"+to_currency.getValue();

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
            return jsonobj.get("conversion_rate").getAsDouble();
        }
        else{
            return null;
        }
    }
}
