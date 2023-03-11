package org.example.parser;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.*;


@SuppressWarnings("Duplicates")
public class DateUtil implements Serializable {

    public DateUtil() {

    }

    public static Long toImpalaCompatibleUnixTime(String dateString) {
        String datePattern;
        Date date = null;
        try {
            switch (dateString.trim().length()) {
                case 12:
                    datePattern = "yyyyMMddHHmm";
                    break;
                case 14:
                    datePattern = "yyyyMMddHHmmss";
                    break;
                case 8:
                    datePattern = "yyyyMMdd";
                    break;
                default:
                    datePattern = "0";
                    break;
            }
            DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(datePattern);
            org.joda.time.LocalDateTime dt = dateFormatter.parseLocalDateTime(dateString);
            date = dt.toDate();

        } catch (Exception e) {
            System.out.print(dateString + " " + e.getMessage());
            return null;
        }
        long unixTime = date.getTime();
        // min: 1900, max: 2030
        if (unixTime > -2208949632000L && unixTime < 1893456038000L) {
            return unixTime * 1000;
        } else {
            return null;
        }

    }

}
