package com.pgl.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateTimeDeserializer extends JsonDeserializer<Object> {
    private static final String[] FORMATS =  {"yyyy-MM-dd'T'HH:mm:ss.sss'Z'", "dd-MM-yyyy hh:mm:ss", "dd/MM/yyyy"};

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if(StringUtils.isBlank(text)){
            return null;
        }
        for(String format : FORMATS){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                Date date = parse(dateFormat, text);
                return date;
            }
            catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    private Date parse(SimpleDateFormat dateFormat, String text) throws Exception {
        Date date = dateFormat.parse(text);
        return date;
    }

    public static Date deserialize(String stringDate) {
        if(StringUtils.isBlank(stringDate)){
            return null;
        }
        for(String format : FORMATS){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                Date date = dateFormat.parse(stringDate);
                return date;
            }
            catch (Exception e) {
                continue;
            }
        }
        return null;
    }
}
