package com.nikita.notes;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static String toTimestamp(Date value) {
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

}
