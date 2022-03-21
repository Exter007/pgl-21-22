package com.pgl.utils;

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
}
