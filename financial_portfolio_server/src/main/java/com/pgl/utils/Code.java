package com.pgl.utils;

public class Code {

    public static String generateCode(){
        return String.valueOf(10000 + (int) (Math.random()*(99999-10000)));
    }
}
