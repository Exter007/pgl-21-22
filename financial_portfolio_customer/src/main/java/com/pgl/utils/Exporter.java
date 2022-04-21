package com.pgl.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgl.models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class Exporter {

    /** Export a list of persistent object in a certain format to a file
     *
     * @param list the list of persistent object
     * @param fileName the file name
     * @param bundle the language bundle
     * @param export_format the format
     * @param <P> generic type extended from PersistentWithoutId
     */
    public static <P extends PersistentWithoutId> void export(List<P> list, String fileName, ResourceBundle bundle, ChoiceBox<String> export_format) {
        Exporter exp = new Exporter();
        if(export_format.getValue().equals(".csv")){
            exp.toCSVFile(list, fileName, bundle);
        }else if(export_format.getValue().equals(".json")){
            exp.toJsonFile(list, fileName, bundle);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("Export_format"));
            alert.showAndWait();
        }
    }

    /** Write a CSV file of a list of java object
     *
     * @param list the list of java object
     * @param fileName the name of the CSV file
     * @param <P> generic type extended from PersistentWithoutId
     */
    private  <P extends PersistentWithoutId> void toCSVFile (List<P> list, String fileName, ResourceBundle bundle){
        ICsvBeanWriter beanWriter = null;

        //implement the chain of responsibility
        CellProcessor[] processors = processorsHeader(list.get(0));
        if (processors == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("CSV_alert"));
            alert.showAndWait();
            return;
        }

        //file header
        String[] header = header(list.get(0));
        if (header == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("CSV_alert"));
            alert.showAndWait();
            return;
        }

        fileName = fileName.concat(".csv");
        try {
            // create writer
            beanWriter = new CsvBeanWriter(new FileWriter(fileName),
                    CsvPreference.STANDARD_PREFERENCE);

            beanWriter.writeHeader(header);

            for (P element : list) {
                beanWriter.write(element, header, processors);
            }

        } catch (Exception ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }

    /** Make an appropriate CellProcessor[] for the type of object we want to export
     *
     * @param obj the typical object
     * @param <P> generic type extended from PersistentWithoutId
     * @return CellProcessor[] or null if the type is unsupported
     */
    private <P extends PersistentWithoutId> CellProcessor[] processorsHeader(P obj){
        if (obj instanceof CurrentAccount){
            return new CellProcessor[] {
                    new NotNull(), // iban
                    new NotNull(), // nature
                    new NotNull(), // accountType
                    new ParseDouble(), // amount
                    new NotNull(), // currency
                    new ParseDouble(), //monthlyFee
                    new ParseDouble(), //annualYield
                    new Optional(), //jointIban
                    new NotNull(), //financialInstitution
                    new ParseDate("dd/MM/yyyy"), //creationDate
                    new ParseDate("dd/MM/yyyy"), //modificationDate
            };
        }else
            return null;
    }

    /** Make an appropriate header for the object we want to export
     *
     * @param obj the object
     * @param <P> generic type extended from PersistentWithoutId
     * @return String[] or null if the object is unsupported
     */
    private <P extends PersistentWithoutId> String[] header(P obj){
        if (obj instanceof CurrentAccount){
            return new String[]{"iban", "nature", "accountType", "amount", "currency",
                    "monthlyFee", "annualYield", "jointIban", "financialInstitution", "creationDate", "modificationDate"};
        }
        else if (obj instanceof YoungAccount){
            return new String[]{"iban", "nature", "accountType", "amount", "currency", "maxTransactionAmount",
                    "monthlyFee", "annualYield", "jointIban", "financialInstitution", "creationDate", "modificationDate"};
        }
        else if (obj instanceof SavingsAccount){
            return new String[]{"iban", "nature", "accountType", "amount", "currency",
                    "monthlyFee", "annualYield", "jointIban", "financialInstitution", "loyaltyDate",
                    "loyaltyBonus", "annualInterest", "creationDate", "modificationDate"};
        }
        else if (obj instanceof TermAccount){
            return new String[]{"iban", "nature", "accountType", "amount", "currency", "monthlyFee", "annualYield",
                    "jointIban", "financialInstitution", "maximumDate", "penalty",
                    "creationDate", "modificationDate"};
        //}
        //else if(obj instanceof Wallet){
            //return new String[]{"name", "financialInstitution", "applicationClient", "walletFinancialProducts"};
        }else
            return null;
    }

    /** Write a JSON file of a list of java object
     *
     * @param list the list of java object
     * @param fileName the name of the JSON file
     * @param <P> generic type extended from PersistentWithoutId
     */
    private <P extends PersistentWithoutId> void toJsonFile(List<P> list, String fileName, ResourceBundle bundle){
        fileName = fileName.concat(".json");
        try {
            // create Gson instance with pretty-print
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // create writer
            FileWriter writer = new FileWriter(fileName);

            // convert list to JSON file
            gson.toJson(list, writer);

            // close writer
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
