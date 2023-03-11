package org.example.parser;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.*;


@SuppressWarnings("Duplicates")
public class DateUtil implements Serializable {
    private static Locale TRLocale = new Locale("tr", "TR");
    private static final String notdefined = "Belirtilmemiş";
    private DateTimeFormatter formatter;
    private String dateString;
    private org.joda.time.LocalDateTime dt;
    private String year = null;
    private String monthNumber = null;
    private String dayNumber = null;
    private static TimeZone utc = TimeZone.getTimeZone("UTC");
    private static String timeZone = "Europe/Istanbul";


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

    public static Long toImpalaCompatibleUnixDate(String dateString) {
        Long unixTime = null;
        if (dateString != null && !dateString.trim().equals("")) {
            unixTime = DateUtil.toImpalaCompatibleUnixTime(dateString.substring(0, 8));
        }
        return unixTime;
    }
}
