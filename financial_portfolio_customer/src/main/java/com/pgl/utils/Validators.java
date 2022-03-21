package com.pgl.utils;

public class Validators {

    /**
     * Vérifie que le numéro de registre national n'est composé que de nombres et si il fait 11 caractères
     * @param nationalRegisterNumber
     * @return true ou false
     */
    public static boolean check_nationalRegisterNumber(String nationalRegisterNumber){
        boolean isNumeric =  nationalRegisterNumber.matches("[+-]?\\d*(\\.\\d+)?");
        isNumeric = (nationalRegisterNumber.length() == 11);
        return isNumeric;
    }
}
