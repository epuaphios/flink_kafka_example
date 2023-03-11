package org.example.parser;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.text.*;
import java.time.*;
import java.util.*;

/**
 * Created by Saeid on 5/22/2017.
 */
@SuppressWarnings("Duplicates")
public class DateUtil implements Serializable {
    private static Locale TRLocale = new Locale("tr", "TR");
    private static final String notdefined = "Belirtilmemiş";
    private DateTimeFormatter formatter;
    private String dateString;
    private org.joda.time.LocalDateTime dt;
    private static CachingNumberFormat decimalFormatter = new CachingNumberFormat(new DecimalFormat("00"));
    private String year = null;
    private String monthNumber = null;
    private String dayNumber = null;
    private static TimeZone utc = TimeZone.getTimeZone("UTC");
    private static String timeZone = "Europe/Istanbul";
    public static long THREE_HOURS_AS_EPOCH_MILLI = 10800000L; //millisecond
    public static long THREE_HOURS_AS_EPOCH_MICRO = 10800000000L; //microsecond
    public static HashMap<String, SimpleDateFormat> datePatternFormatters = new HashMap<String, SimpleDateFormat>() {{
        this.put("yyyyMMddHHmm", new SimpleDateFormat("yyyyMMddHHmm"));
        this.put("yyyyMMddHHmmss", new SimpleDateFormat("yyyyMMddHHmmss"));
        this.put("yyyyMMdd", new SimpleDateFormat("yyyyMMdd"));
        this.put("0", new SimpleDateFormat("0"));
    }};

    public DateUtil() {

    }

    public DateUtil(String dateString) {
        this.dateString = dateString;
        String datePattern;

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
            formatter = DateTimeFormat.forPattern(datePattern);

            dt = formatter.parseLocalDateTime(dateString);
        } catch (IllegalArgumentException | NullPointerException e) {
            //e.printStackTrace();
        }

        try {
            this.year = dt.year().getAsString();
            this.monthNumber = decimalFormatter.format(dt.getMonthOfYear());
            this.dayNumber = decimalFormatter.format(dt.getDayOfMonth());
        } catch (IllegalArgumentException | NullPointerException e) {
            this.year = null;
            this.monthNumber = null;
            this.dayNumber = null;
        }

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
            System.out.print(dateString+ " "+e.getMessage());
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

    public static Integer getUnixTimeDiffAsMinute(long startTime, long stopTime) {
        Long diff = Math.abs(((stopTime / 1000000L) - (startTime / 1000000L)) / 60L);  //Microsecond to minute
        Integer timeDiff = diff.intValue();
        return timeDiff;
    }

    public static Integer getUnixTimeDiffAsDay(long startTime, long stopTime) {
        Double diff = Math.ceil(Math.abs((stopTime / 1000000L) - (startTime / 1000000L)) / (double) 86400);  //Microsecond to day (less than 1 full day considered as one day)
        Integer timeDiff = diff.intValue();
        return timeDiff;
    }

    public static Long getUTCTimeAsLong(String epochStr) throws Exception {
        Long result = null;
        result = Long.valueOf(epochStr);
        return result;
    }

    public static Long getCurrentTimeAsMicro() {
        //return Instant.now().getEpochSecond() * 1000000;
        return System.currentTimeMillis() * 1000;
    }

    public static Long getCurrentTimeAsMilli() {
        return Instant.now().getEpochSecond() * 1000;
    }

/*    public void setDateString(String dateString){
        this.dateString = dateString;
        String datePattern;

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
            formatter = DateTimeFormat.forPattern(datePattern);
            dt = formatter.parseDateTime(dateString);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
        }
    }*/

    public String getMonthName() {
        try {
            return dt.monthOfYear().getAsText(TRLocale);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getMonthNumber() {
        try {
            return decimalFormatter.format(dt.getMonthOfYear());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getDayName() {
        try {
            return dt.dayOfWeek().getAsText(TRLocale);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getDayNumber() {
        try {
            return decimalFormatter.format(dt.getDayOfMonth());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getYear() {
        try {
            return dt.year().getAsString();
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getHour() {
        try {
            return dt.hourOfDay().getAsString();
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getMinute() {
        try {
            return dt.minuteOfHour().getAsString();
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getYM() {
        if (this.year == null || this.monthNumber == null) {
            return null;
        } else {
            return this.year + "-" + this.monthNumber;
        }
    }

    public String getYMD() {
        if (this.year == null || this.monthNumber == null || this.dayNumber == null) {
            return null;
        } else {
            return this.year + "-" + this.monthNumber + "-" + this.dayNumber;
        }
    }

    public int getYearMonth() {
        if (this.year == null || this.monthNumber == null) {
            return -1;
        } else {
            return Integer.parseInt(this.year + this.monthNumber);
        }
    }

/*    public static Date toDate(String dateString) {
        Date date = null;
        if (dateString ==null){
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }*/

    public static Integer getCurrentYear() {
        ZoneId zoneId = ZoneId.of(timeZone);
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.getYear();
    }

    public static String microsecondsToDateString(Long microseconds, java.time.format.DateTimeFormatter formatter) {
        LocalDate date = Instant.ofEpochMilli(microseconds / 1000000)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return date.format(formatter);
    }

    public static String epochToDateString(Long microseconds) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(microseconds/ 1000);
    }

    public static String epochToDateTimeString(Long microseconds) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
        return df.format(microseconds/ 1000);
    }

    public static long getTimeAsSecond(long timeAsMillis) {
        return timeAsMillis / 1000;
    }


    public static void main(String[] args) {


        System.out.println(getUnixTimeDiffAsMinute(1420153560000000L, 1420239950000000L));
        System.out.println(ZonedDateTime.now());
        System.out.println(System.currentTimeMillis());
        System.out.println("---------------------");


        ZoneId id = ZoneId.of("Europe/Istanbul");
        System.out.println(LocalDateTime.now(id));
        System.out.println("---------------------");

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Istanbul");
        //Calendar calendar = new GregorianCalendar();
        Calendar calendar = Calendar.getInstance(timeZone);
        //calendar.setTimeZone(timeZone);
        System.out.println(calendar.getTimeInMillis());
        System.out.println("---------------------");

        System.out.println((new Date()).getTime());
        System.out.println("---------------------");

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        System.out.println(timeStampMillis);
        System.out.println("---------------------");

        byte[] data = SerializationUtils.serialize(instant);
        instant = (Instant) SerializationUtils.deserialize(data);
        System.out.println(DateUtil.toImpalaCompatibleUnixTime("201712311713"));


    }
}

// 1. Avoid extending library classes whenever possible.
// 2. Optimize for performance.
class CachingNumberFormat {
    NumberFormat formatter;
    HashMap<Long, String> formattedCachedLongs = new HashMap<>();
    HashMap<Integer, String> formattedCachedInts = new HashMap<>();

    public CachingNumberFormat(NumberFormat formatter) {
        this.formatter = formatter;
    }

    public String format(long value) {
        String formatted = formattedCachedLongs.get(value);
        if (formatted == null) {
            formatted = formatter.format(value);
            formattedCachedLongs.put(value, formatted);
        }
        return formatted;
    }

    public String format(int value) {
        String formatted = formattedCachedInts.get(value);
        if (formatted == null) {
            formatted = formatter.format(value);
            formattedCachedInts.put(value, formatted);
        }
        return formatted;
    }
}