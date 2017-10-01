package com.ef.extracters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.lang.model.util.SimpleElementVisitor6;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@Service
public class DateExtractor implements Extractor<Date, String> {


    /**
     * Date format
     */
    @Value("${parser.date.format}")
    private String dateFormat;

    /**
     * IP index position
     */
    private static int ipIndex = -1;

    @Override
    public Date extract(String[] elements) {
        Date date = null;

        if (ipIndex != -1 && ipIndex < elements.length && dateMatches(elements[ipIndex])) {
            date = getDate(elements[ipIndex]);
        } else {
            int index = 0;
            for (String element : elements) {
                if (dateMatches(element)) {
                    date = getDate(element);
                    break;
                }
                index++;
            }
        }
        return date;
    }


    /**
     * This method is in charge of validates if a string value correspond to Date value.
     *
     * @param value string value.
     * @return true if the value correspond to a date, or false otherwise.
     */
    private boolean dateMatches(String value) {
        boolean result = false;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
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
    private Date getDate(String value) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            date = simpleDateFormat.parse(value.trim());
        } catch (ParseException e) {
            //Ignored exception.
        }

        return date;
    }
}
