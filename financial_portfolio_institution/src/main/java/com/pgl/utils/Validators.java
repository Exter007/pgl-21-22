package com.pgl.utils;

public class Validators {

    /**
     * Remove spaces and concatenated
     * @param name the financial institution name
     * @param BIC the financial institution BIC
     * @return name and BIC concatenated
     */
    public static String username(String name, String BIC){
        String clearSpaceName = name.replaceAll("\\s+","");
        return clearSpaceName+BIC;
    }

    /**
     * Checks that the BIC is composed of 8 characters long
     * @param BIC the financial institution BIC
     * @return true or false
     */
    public static boolean check_BIC(String BIC){
        boolean isOK = (BIC.length() == 8);
        return isOK;
    }

    /**
     * Check that the e-mail is in the right format (@ and .)
     * @param email institution email
     * @return true or false
     */
    public static boolean check_email(String email){
        boolean hasArobase =  email.contains("@");
        boolean hasPoint =  email.contains(".");
        return hasArobase && hasPoint;
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
}
