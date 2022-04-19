package com.pgl.utils;

import com.pgl.controllers.DashboardController;
import javafx.scene.control.Alert;

public class Validators {

    /**
     * Checks that the national registry number is composed of numbers only and if it is 11 characters long
     * @param nationalRegisterNumber the user national register number
     * @return true or false
     */
    public static boolean check_nationalRegisterNumber(String nationalRegisterNumber){
        boolean isNumeric =  nationalRegisterNumber.matches("[+-]?\\d*(\\.\\d+)?");
        isNumeric = (nationalRegisterNumber.length() == 11);
        return isNumeric;
    }

    /**
     * Checks if the password is in the right format (at least 1 letter and 1 number)
     * @param password the user password
     * @return true or false
     */
    public static boolean check_password(String password){
        char c;
        boolean alpha = false;
        boolean number = false;
        for(int i=0; i < password.length(); i++) {
            c = password.charAt(i);
            if( Character.isDigit(c)) {
                number = true;
            }
            if (Character.isUpperCase(c) || Character.isLowerCase(c)) {
                alpha = true;
            }
            if(number && alpha)
                return true;
        }
        return false;
    }

    /**
     * Check that the e-mail is in the right format (@ and .)
     * @param email the user email
     * @return true or false
     */
    public static boolean check_email(String email){
        boolean hasArobase =  email.contains("@");
        boolean hasPoint =  email.contains(".");
        return hasArobase && hasPoint;
    }

    /**
     * Remove spaces and concatenated
     * @param name the user name
     * @param nationalRegisterNumber the user national register number
     * @return name and national registry number concatenated
     */
    public static String username(String name, String nationalRegisterNumber){
        String clearSpaceName = name.replaceAll("\\s+","");
        return clearSpaceName+nationalRegisterNumber;
    }

    /**
     * Checks that the IBAN is composed of at least 14 characters
     * @param IBAN the bank account numbering
     * @return true or false
     */
    public static boolean check_IBAN(String IBAN){
        boolean length = (IBAN.length() >= 14);
        return length;
    }

    /**
     * Convert string to float
     * @param value
     * @return
     */
    public static float convertToFloat(String value){
        try{
            float floatValue = Float.valueOf(value);
            return floatValue;
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(DashboardController.bundle.getString("error181")
                    + value + " " + DashboardController.bundle.getString("error182"));
            alert.showAndWait();
        }
        return 0;
    }
}
