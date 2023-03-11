package org.example.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public abstract class Helper {

    public static HashMap<Class, Field> valueGetters = new HashMap<>();
    public Map<String, Integer> sequenceIds = new HashMap<>();

    public static String getValue(Object object) throws IllegalAccessException {
        try {
              Field a = object.getClass().getDeclaredField("$atvalue");
              a.setAccessible(true);
              Object value = a.get(object);
              return value.toString();
    }
    catch (NoSuchFieldException e) {
        return null;
    }
    }

    public static Long getDatetimeValue(Object object) throws  IllegalAccessException {
        try {
        String stringValue = getStringValue(object);
        if (stringValue != null && !stringValue.trim().equals("")) {
            return DateUtil.toImpalaCompatibleUnixTime(stringValue);
        }
        return null;
        }
        catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static String getStringValue(Object object) throws NoSuchFieldException, IllegalAccessException {
        // TODO: Move to global or use global if exists
        if (object != null) {
            String value = getValue(object);
            if (value != null && !value.trim().equals("")) {
                if (value.length() > 1000) {
                    return value.substring(0, 1000);
                }
                else {
                    return value.trim();
                }
            }
        }
        return null;
    }

    public static Double getDoubleValue(Object object) throws NoSuchFieldException, IllegalAccessException {
        // TODO: Move to global or use global if exists
        String stringValue = getStringValue(object);
        Double doubleValue = null;
        if (stringValue != null) {
            stringValue = stringValue.replaceAll(",", ".");
            try {
                doubleValue = Double.valueOf(stringValue).doubleValue();
            } catch (NumberFormatException e) {
                // Some floats are weird, nothing to do here.
                // Null will be returned.
            }
        }
        return doubleValue;
    }

    public static Integer getIntegerCode(Object object) {
        // TODO: Move to global or use global if exists
        if (object != null) {
            String code = getCode(object);
            if (code != null && !code.trim().equals("")) {
                return Integer.valueOf(code.trim());
            }
        }
        return null;
    }

    public Integer getNextId(String sequence) {
        // CM: A great performance comparison of different ways to do the operation done below
        // https://stackoverflow.com/questions/81346/most-efficient-way-to-increment-a-map-value-in-java
        //should be atomic...
        Integer id = sequenceIds.getOrDefault(sequence, -1) + 1;
        sequenceIds.put(sequence, id);
        return id;
    }
    public static Integer getIntegerValue(Object object) throws NoSuchFieldException, IllegalAccessException {
        // TODO: Move to global or use global if exists
        String stringValue = getStringValue(object);
        Integer integerValue = null;
        if (stringValue != null) {
            // Assume that thousands separator cannot be sent, so it must be decimal separator.
            stringValue = stringValue.replaceAll(",", ".");

            // Remove decimal separator
            int indexOfDecimalSeperator = stringValue.indexOf(".");
            if (indexOfDecimalSeperator != -1) {
                stringValue = stringValue.substring(0, indexOfDecimalSeperator);
            }

            try {
                integerValue = Integer.parseInt(stringValue);
            } catch (Exception e) {
                integerValue = null;
            }
        }
        return integerValue;
    }
    public static String getCode(Object object) {
        try {
            return (String) object.getClass().getMethod("getCode").invoke(object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
