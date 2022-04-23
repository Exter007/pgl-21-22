package com.pgl.controllers.extension2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pgl.services.UserService;
import com.pgl.utils.Validators;
import com.tecacet.finance.service.currency.CurrencyExchangeService;
import com.tecacet.finance.service.currency.GrandtrunkCurrencyExchangeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class WalletShowExchangeRateHistoryController implements Initializable {

    static UserService userService = new UserService();
    static ResourceBundle bundle;
    private LocalDate currentDate;

    @FXML
    private ChoiceBox<String> time_periode;

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

    private static final String HISTORICAL_RATE_URL = "http://currencies.apps.grandtrunk.net/getrange/%s/%s/%s/%s";
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
        currentDate = LocalDate.now();
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
        ObservableList<String> granularity = FXCollections.observableArrayList(bundle.getString("Year"),
                bundle.getString("Month"), bundle.getString("Week"));
        time_periode.setItems(granularity);
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
                if (from_currency.getValue().equals(to_currency.getValue())) {
                    to.setText(from.getText());
                }
                else{//the base (from_currency) and the destination (to_currency) have a different value
                    try {
                        convert(from_currency.getValue(), to_currency.getValue(), from, to);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        time_periode.setOnAction((event) ->{
            if(from_currency.getValue() == null || to_currency.getValue() == null || time_periode.getValue() == null )
                exchange_rate_line_chart.getData().clear();
            else{
                setLineChart(time_periode.getValue());
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

    private void setLineChart(String granularity){
        double rate;
        exchange_rate_line_chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        if(granularity.equals(bundle.getString("Year"))){
            exchange_rate_line_chart.setTitle(bundle.getString("Historical_exchange_rates"));
            if(from_currency.getValue().equals(to_currency.getValue())){
                for(int i = 1999; i < currentDate.getYear(); i++) {
                    series.getData().add(new XYChart.Data<String, Number>(bundle.getString("Year")+" "+i, 1));
                }
            }
            else{//TODO
                CurrencyExchangeService exchangeService = new GrandtrunkCurrencyExchangeService();
                series.setName(bundle.getString("Year")+" "+currentDate.getYear());
                for(int i = 1; i < 13; i++) {
                    if(currentDate.getMonthValue() < i)
                        break;

                    String begun = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.of(1999, 1, 1));
                    String end = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(currentDate);
                    //series.getData().add(new XYChart.Data<String, Number>(bundle.getString("Month"+i), ));
                }
            }
            exchange_rate_line_chart.getData().add(series);
        }
        else if(granularity.equals(bundle.getString("Month"))){
            exchange_rate_line_chart.setTitle(bundle.getString("Historical_exchange_rates")+", "+granularity+" "+currentDate.getYear());
            if(from_currency.getValue().equals(to_currency.getValue())){
                for(int i = 1; i < 13; i++) {
                    if(currentDate.getMonthValue() <= i)
                        break;
                    LocalDate date = LocalDate.of(currentDate.getYear(), i, 1);
                    series.getData().add(new XYChart.Data<String, Number>(bundle.getString("Month"+i), 1));
                }
            }
            else{
                CurrencyExchangeService exchangeService = new GrandtrunkCurrencyExchangeService();
                series.setName(bundle.getString("Year")+" "+currentDate.getYear());
                for(int i = 1; i < 13; i++) {
                    if(currentDate.getMonthValue() < i)
                        break;
                    LocalDate date = LocalDate.of(currentDate.getYear(), i, 1);
                    // Get Historical rate
                    rate = exchangeService.getExchangeRate(from_currency.getValue(), to_currency.getValue(), date);
                    series.getData().add(new XYChart.Data<String, Number>(bundle.getString("Month"+i), rate));
                }
                exchange_rate_line_chart.getData().add(series);
            }
        }
    }

    private String[] getRates(String begin, String end){
        String url = String.format(HISTORICAL_RATE_URL, begin, end, from_currency.getValue(), to_currency.getValue());
    }
}
