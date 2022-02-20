package com.pgl.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringUtil {
    public static boolean isNullOrEmpty(String text){
        return text == null || text.isEmpty();
    }

    public static boolean isNullOrWhitespace(String text){
        return text == null || text.trim().isEmpty();
    }

    public static boolean isNull(Object object){
        return object == null;
    }

    public static boolean isNotNull(Object object){
        return !isNull(object);
    }

    public static boolean isBlanck(Object object){
        return isNull(object) || object.toString().trim().isEmpty();
    }

    public static boolean isNotEmpty(Object object){
        return !isBlanck(object);
    }

    public static String randomString(int lenght) {
        String token;
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        token = String.join("", Collections.nCopies(100, alphabet));
        token = shuffle(token);
        return token.substring(0, lenght);
    }

    public static String shuffle(String s) {
        List<Character> characters = new ArrayList<>();

        for (char c : s.toCharArray()) {
            characters.add(c);
        }

        StringBuilder outPut = new StringBuilder(s.length());

        while (characters.size() !=0) {
            int randPicker = (int)(Math.random()*characters.size());
            outPut.append(characters.remove(randPicker));
        }

        return outPut.toString();
    }
}
