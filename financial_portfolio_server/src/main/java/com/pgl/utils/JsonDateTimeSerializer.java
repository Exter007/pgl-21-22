package com.pgl.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonDateTimeSerializer extends JsonSerializer<Object> {
    private static final String[] FORMATS =  {"yyyy-MM-dd'T'HH:mm:ss.sss'Z'"};

    @Override
    public void serialize(Object object, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        String formattedDate = getFormatedDate(object);
        gen.writeString(formattedDate);
    }

    /**
     * Format a date to string
     * @param object
     * @return
     */
    public static String getFormatedDate(Object object) {
        String formattedDate = object.toString();
        for(String format : FORMATS){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                formattedDate = dateFormat.format(object);
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
        return formattedDate;
    }
}
