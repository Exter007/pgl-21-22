package com.pgl.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgl.models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Exporter {

    public static <P extends PersistentWithoutId> void ActionExport(String currentDateTime, List<P> list, ResourceBundle bundle, ChoiceBox<String> export_format, boolean all) {
        Exporter exp = new Exporter();
        String fileName = null;
        if(list.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("Export_alert"));
            alert.showAndWait();
            return;
        }
        else if(list.get(0) instanceof FinancialProduct){
            if(all)
                fileName="financial_products_" + currentDateTime;
            else
                fileName= ((FinancialProduct) list.get(0)).getFinancialInstitution().getName() + "_wallet_" + currentDateTime;
        }
        else if(list.get(0) instanceof Transaction){
            fileName = "transactions_" + currentDateTime;
        }
        exp.export(list, fileName, bundle, export_format);
    }

    /** Export a list of persistent object in a certain format to a file
     *
     * @param list the list of persistent object
     * @param fileName the file name
     * @param bundle the language bundle
     * @param export_format the format
     * @param <P> generic type extended from PersistentWithoutId
     */
    public <P extends PersistentWithoutId> void export(List<P> list, String fileName, ResourceBundle bundle, ChoiceBox<String> export_format) {
        if(export_format.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("Export_format"));
            alert.showAndWait();
        }
        else if(export_format.getValue().equals(".csv")){
            this.toCSVFile(list, fileName, bundle);
        }else if(export_format.getValue().equals(".json")){
            this.toJsonFile(list, fileName);
        }
    }

    /** Write a CSV file of a list of java object
     *
     * @param list the list of java object
     * @param fileName the name of the CSV file
     * @param <P> generic type extended from PersistentWithoutId
     */
    private  <P extends PersistentWithoutId> void toCSVFile (List<P> list, String fileName, ResourceBundle bundle) {
        fileName = fileName.concat(".csv");
        if(list.get(0) instanceof FinancialProduct){
            List<P> currentAccounts = new ArrayList<>();
            List<P> youngAccounts = new ArrayList<>();
            List<P> savingAccounts = new ArrayList<>();
            List<P> termAccounts = new ArrayList<>();

            for (P account: list) {
                if(account.getClass().equals(CurrentAccount.class))
                    currentAccounts.add(account);
                else if(account.getClass().equals(YoungAccount.class))
                    youngAccounts.add(account);
                else if(account.getClass().equals(SavingsAccount.class))
                    savingAccounts.add(account);
                else if(account.getClass().equals(TermAccount.class))
                    termAccounts.add(account);
            }

            try {
                writeAccounts(CurrentAccount.class, fileName, currentAccounts, bundle);
                writeAccounts(YoungAccount.class, fileName, youngAccounts, bundle);
                writeAccounts(SavingsAccount.class, fileName, savingAccounts, bundle);
                writeAccounts(TermAccount.class, fileName, termAccounts, bundle);

            } catch (Exception ex) {
                System.err.println("Error writing the CSV file: " + ex);
            }
        }
        else if(list.get(0) instanceof Transaction){
            try {
                writeAccounts(Transaction.class, fileName, list, bundle);
            } catch (Exception ex) {
                System.err.println("Error writing the CSV file: " + ex);
            }
        }
    }

    /** Make an appropriate CellProcessor[] for the type of object we want to export
     *
     * @param obj the typical object
     * @param <P> generic type extended from PersistentWithoutId
     * @return CellProcessor[] or null if the type is unsupported
     */
    private <P extends PersistentWithoutId> String[] header(Class<P> obj){
        if (obj.equals(CurrentAccount.class)){
            return new String[]{"NATURE", "ACCOUNT TYPE", "IBAN", "AMOUNT", "CURRENCY",
                    "MONTHLY FEE", "ANNUAL YIELD", "FINANCIAL INSTITUTION", "CREATION DATE", "MODIFICATION DATE"};
        }
        else if(obj.equals(YoungAccount.class)){
            return new String[]{"NATURE", "ACCOUNT TYPE", "IBAN", "AMOUNT", "CURRENCY","MAX TRANSACTIONAL AMOUNT",
                    "MONTHLY FEE", "ANNUAL YIELD", "FINANCIAL INSTITUTION", "CREATION DATE", "MODIFICATION DATE"};
        }
        else if(obj.equals(SavingsAccount.class)){
            return new String[]{"NATURE", "ACCOUNT TYPE", "IBAN", "AMOUNT", "CURRENCY",
                    "MONTHLY FEE", "ANNUAL YIELD", "FINANCIAL INSTITUTION", "LOYALTY DATE",
                    "LOYALTY BONUS", "ANNUAL INTEREST", "CREATION DATE", "MODIFICATION DATE"};
        }
        else if(obj.equals(TermAccount.class)){
            return new String[]{"NATURE", "ACCOUNT TYPE", "IBAN", "AMOUNT", "CURRENCY",
                    "MONTHLY FEE", "ANNUAL YIELD", "FINANCIAL INSTITUTION", "TERM DATE", "PENALTY",
                    "CREATION DATE", "MODIFICATION DATE"};
        }
        else if(obj.equals(Transaction.class)){
            return new String[]{"TRANSACTION NUMBER", "TRANSACTION TYPE", "Transmitter IBAN", "BENEFICIARY IBAN",
                    "BENEFICIARY NAME", "AMOUNT", "COMMUNICATION TYPE", "COMMUNICATION", "DATE", "REQUEST_STATUS"};
        }
        else
            return null;
    }

    /** Make an appropriate header for the object we want to export
     *
     * @param obj the object
     * @param <P> generic type extended from PersistentWithoutId
     * @return String[] or null if the object is unsupported
     */
    private <P extends PersistentWithoutId> String[] fileMapping(Class<P> obj){
        if (obj.equals(CurrentAccount.class)){
            return new String[]{"nature", "accountType", "iban", "amount", "currency",
                    "monthlyFee", "annualYield", "financialInstitution.name", "creationDate", "modificationDate"};
        }
        else if (obj.equals(YoungAccount.class)){
            return new String[]{"nature", "accountType", "jointIban.iban", "amount", "currency", "maxTransactionAmount",
                    "monthlyFee", "annualYield", "financialInstitution.name", "creationDate", "modificationDate"};
        }
        else if (obj.equals(SavingsAccount.class)){
            return new String[]{"nature", "accountType", "jointIban.iban", "amount", "currency",
                    "monthlyFee", "annualYield", "financialInstitution.name", "loyaltyDate",
                    "loyaltyBonus", "annualInterest", "creationDate", "modificationDate"};
        }
        else if (obj.equals(TermAccount.class)){
            return new String[]{"nature", "accountType", "jointIban.iban", "amount", "currency", "monthlyFee", "annualYield",
                    "financialInstitution.name", "maximumDate", "penalty",
                    "creationDate", "modificationDate"};
        }
        else if(obj.equals(Transaction.class)){
            return new String[]{"transactionNumber", "type", "bankAccount.iban", "destinationIBAN",
                    "destinationName", "amount", "communication_type", "communication", "date", "status"};
        }
        else
            return null;
    }

    /** Write a JSON file of a list of java object
     *
     * @param list the list of java object
     * @param fileName the name of the JSON file
     * @param <P> generic type extended from PersistentWithoutId
     */
    private <P extends PersistentWithoutId> void toJsonFile(List<P> list, String fileName){
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

    private void showHeaderError(String[] header, ResourceBundle bundle){
        if (header == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("CSV_alert"));
            alert.showAndWait();
        }
    }

    private void showProcessorsError(String[] fileMapping, ResourceBundle bundle){
        if (fileMapping == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(bundle.getString("CSV_alert"));
            alert.showAndWait();
        }
    }

    private<P extends PersistentWithoutId> void writeAccounts(Class pClass, String fileName, List<P> accounts,
                                                      ResourceBundle bundle) throws IOException {
        String[] fileMapping;
        String[] header;
        ICsvDozerBeanWriter beanWriter;
        // create writer
        beanWriter = new CsvDozerBeanWriter(new FileWriter(fileName, true),
                CsvPreference.STANDARD_PREFERENCE);

        //implement the chain of responsibility
        fileMapping = fileMapping(pClass);
        showProcessorsError(fileMapping, bundle);
        //account header
        header = header(pClass);
        showHeaderError(header, bundle);

        beanWriter.configureBeanMapping(pClass, fileMapping);

        beanWriter.writeHeader(header);

        for (P element : accounts) {
            beanWriter.write(element);
        }

        beanWriter.writeHeader("\n");
        try {
            beanWriter.close();
        } catch (IOException ex) {
            System.err.println("Error closing the writer: " + ex);
        }
    }
}
