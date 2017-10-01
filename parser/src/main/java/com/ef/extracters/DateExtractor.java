package com.ef.extracters;

import com.ef.util.DateUtil;
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

        if (ipIndex != -1 && ipIndex < elements.length && DateUtil.isValid(dateFormat, elements[ipIndex])) {
            date = DateUtil.convertToDate(dateFormat, elements[ipIndex]);
        } else {
            int index = 0;
            for (String element : elements) {
                if (DateUtil.isValid(dateFormat, element)) {
                    date = DateUtil.convertToDate(dateFormat, element);
                    break;
                }
                index++;
            }
        }
        return date;
    }



}
