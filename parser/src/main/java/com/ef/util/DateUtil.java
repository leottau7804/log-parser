package com.ef.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sergio.leottau on 1/10/17.
 */
public final class DateUtil {

    /**
     * Private constructor to avoid instantiation
     */
    private DateUtil() {

    }

    /**
     * This method is in charge of validates if a string value correspond to Date value.
     *
     * @param value string value.
     * @return true if the value correspond to a date, or false otherwise.
     */
    public static boolean isValid(String pattern, String value) {
        boolean result = false;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.parse(value.trim());
            result = true;
        } catch (ParseException e) {
            result = false;
        }

        return result;
    }

    /**
     * Method in charge of convert a string to a Date.
     *
     * @param value date in string format.
     * @return date built
     */
    public static Date convertToDate(String pattern, String value) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            date = simpleDateFormat.parse(value.trim());
        } catch (ParseException e) {
            //Ignored exception.
        }

        return date;
    }

    /**
     *
     * @param pattern
     * @param value
     * @return
     */
    public static String convertToString(String pattern, Date value) {
        String date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        date = simpleDateFormat.format(value);

        return date;
    }
}
