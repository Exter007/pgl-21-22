package com.pgl.controllers.extension2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pgl.services.UserService;
import com.pgl.utils.Validators;
import com.tecacet.finance.service.WebUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class WalletShowExchangeRateHistoryController implements Initializable {

    static UserService userService = new UserService();
    static ResourceBundle bundle;
    private LocalDate currentDate;

    @FXML
    private Button Confirm;

    @FXML
    private ChoiceBox<String> time_period;

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

    private static final String HISTORICAL_RATE_URL = "https://currencies.apps.grandtrunk.net/getrange/%s/%s/%s/%s";

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
        Confirm.setText(bundle.getString("Line_chart_Button"));
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
        ObservableList<String> granularity = FXCollections.observableArrayList(bundle.getString("Years"), bundle.getString("Year"),
                bundle.getString("Month"), bundle.getString("Week"));
        time_period.setItems(granularity);
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

        time_period.setOnAction((event) ->{
            if(from_currency.getValue() == null || to_currency.getValue() == null || time_period.getValue() == null )
                exchange_rate_line_chart.getData().clear();
            else{
                try {
                    setLineChart(time_period.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void setLineChart(String granularity) throws IOException {
        exchange_rate_line_chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String end = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(currentDate);
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableList(new ArrayList<>());

        //Years
        if(granularity.equals(bundle.getString("Years"))){
            exchange_rate_line_chart.setTitle(bundle.getString("Historical_exchange_rates"));
            if(from_currency.getValue().equals(to_currency.getValue())){
                for(int i = 1999; i < currentDate.getYear(); i++) {
                    data.add(new XYChart.Data<>(bundle.getString("Year") + " " + i, 1));
                }
            }
            else{
                String begin = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.of(1999, 1, 1));
                String [] dateAndRates = getDateAndRates(begin, end);
                for(int i = 0; i < dateAndRates.length; i=i+2) {
                    data.add(new XYChart.Data<>(dateAndRates[i], Double.parseDouble(dateAndRates[i + 1])));
                }
            }
        }
        //Year
        else if(granularity.equals(bundle.getString("Year"))){
            exchange_rate_line_chart.setTitle(bundle.getString("Historical_exchange_rates")+", "+granularity+" "+(currentDate.getYear()-1)+"-"+currentDate.getYear());
            int j = currentDate.getMonthValue();
            if(from_currency.getValue().equals(to_currency.getValue())){
                for(int i = 0; i < 12; i++) {
                    data.add(new XYChart.Data<>(bundle.getString("Month" + (j % 12 + 1)), 1));
                    j++;
                }
            }
            else{
                String begin = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.of(currentDate.getYear()-1, currentDate.getMonthValue()+1, 1));
                String [] dateAndRates = getDateAndRates(begin, end);
                for(int i = 0; i < dateAndRates.length; i=i+2) {
                    data.add(new XYChart.Data<>(dateAndRates[i], Double.parseDouble(dateAndRates[i + 1])));
                }
            }
        }
        //Month
        else if(granularity.equals(bundle.getString("Month"))){
            exchange_rate_line_chart.setTitle(bundle.getString("Historical_exchange_rates")+", "+granularity+":"+bundle.getString("Month"+currentDate.getMonthValue()));
            if(from_currency.getValue().equals(to_currency.getValue())){
                for(int i = 1; i < 28; i++) {
                    data.add(new XYChart.Data<>("Day " + i, 1));
                }
            }
            else{
                String begin = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1));
                String [] dateAndRates = getDateAndRates(begin, end);
                for(int i = 0; i < dateAndRates.length; i=i+2) {
                    data.add(new XYChart.Data<>(dateAndRates[i], Double.parseDouble(dateAndRates[i + 1])));
                }
            }
        }
        //Week
        else if(granularity.equals(bundle.getString("Week"))){
            int day = currentDate.getDayOfMonth(), month = currentDate.getMonthValue(), year = currentDate.getYear();
            exchange_rate_line_chart.setTitle(bundle.getString("Historical_exchange_rates")+", "+granularity);
            int j = currentDate.getDayOfWeek().getValue();
            if(from_currency.getValue().equals(to_currency.getValue())){
                for(int i = 0; i < 7; i++) {
                    data.add(new XYChart.Data<>(bundle.getString("Day" + (j%7 + 1)), 1));
                    j++;
                }
            }
            else{
                if (day<8){
                    if(month<2){//January
                        year = currentDate.getYear()-1;
                        month = 12;
                        day = 31 + currentDate.getDayOfMonth()-7;
                    }
                    else if(month==2){// February
                        month = 1;
                        if(isaLeapYear(year)){
                            day = 29 + day-7;
                        }
                        else
                            day = 28 + day-7;
                    }
                    else if(month==4||month==6
                            || month==9 || month==11){
                        month = month-1;
                        day = 30 + day-7;
                    }
                    else {
                        month = month-1;
                        day = 31 + day-7;
                    }
                }
                else {
                    day =currentDate.getDayOfMonth()-7;
                }
                LocalDate date = LocalDate.of(year, month, day);
                String begin = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
                String [] dateAndRates = getDateAndRates(begin, end);
                for(int i = 0; i < dateAndRates.length; i=i+2) {
                    data.add(new XYChart.Data<>(dateAndRates[i], Double.parseDouble(dateAndRates[i + 1])));
                }
            }
        }
        series.setData(data);
        exchange_rate_line_chart.getData().add(series);
    }

    private String[] getDateAndRates(String begin, String end) throws IOException {
        String url = String.format(HISTORICAL_RATE_URL, begin, end, from_currency.getValue(), to_currency.getValue());
        // Get Historical rates
        String response = WebUtil.getResponseAsString(url);

        return response.split("\\s+");
    }

    private boolean isaLeapYear(int year){
        boolean b = true;
        if(year % 4 != 0) {
            b = false;
        }
        else if (year % 100 == 0) {
            if(year % 400 != 0)
                b = false;
        }
        return b;
    }
}
