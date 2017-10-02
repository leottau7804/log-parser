package com.ef.processors.impl;

import com.ef.exception.ParserCode;
import com.ef.exception.ParserException;
import com.ef.model.Blocked;
import com.ef.model.DurationEnum;
import com.ef.processors.Processor;
import com.ef.repositories.AccessLogRepository;
import com.ef.repositories.BlockedRepository;
import com.ef.util.DateUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class is in charge of searching process
 * <p>
 * Created by sergio.leottau on 1/10/17.
 */
@Scope("prototype")
@Service
public class SearchProcessor implements Processor {


    /**
     * Logger configuration.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchProcessor.class);

    /**
     * Start date
     */
    private String startDate;

    /**
     * Threshold
     */
    private Integer threshold;

    /**
     * Duration
     */
    private String duration;
    /**
     * Duration
     */
    private DurationEnum durationEnum;
    /**
     * Date format
     */
    @Value("${search.date.format}")
    private String dateFormat;
    /**
     * Access log repository
     */
    @Autowired
    private AccessLogRepository accessLogRepository;
    /**
     * Bloacked repository
     */
    @Autowired
    private BlockedRepository blockedRepository;


    @Override
    public void process() {

        validate();

        Date start = DateUtil.convertToDate(dateFormat, startDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        switch (durationEnum) {
            case HOURLY:
                calendar.add(Calendar.HOUR, 1);
                break;
            case DAILY:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
        }
        Date end = calendar.getTime();


        List<String> ips = accessLogRepository.findByDateAndThreshold(start, end, threshold);

        for (String ip : ips) {
            MessageFormat messageFormat = new MessageFormat("The ip {0} has more than {1} access between {2} and {3}");
            String message = messageFormat.format(new Object[]{ip, threshold,
                    DateUtil.convertToString(dateFormat, start), DateUtil.convertToString(dateFormat, end)});
            LOGGER.info(message);

            Blocked blocked = new Blocked();
            blocked.setIp(ip);
            blocked.setObservations(message);
            blockedRepository.save(blocked);
        }

        LOGGER.info("A total of {} IPs address were found and blocked", ips.size());


    }

    /**
     * Method in charge of searching validations
     *
     * @return true if the arguments are valid, or false otherwise.
     */
    private void validate() {
        Validate.notNull(startDate, "You must specify a start date to does a search");
        Validate.notNull(threshold, "You must specify a threshold to does a search");
        Validate.notNull(duration, "You must specify a duration to does a search");

        if (!DateUtil.isValid(dateFormat, startDate)) {
            throw new ParserException(ParserCode.INVALID_DATE_FORMAT, dateFormat);
        }

        if (threshold < 0) {
            throw new ParserException(ParserCode.INVALID_THRESHOLD);
        }

        try {
            durationEnum = DurationEnum.valueOf(duration.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParserException(ParserCode.INVALID_DURATION);
        }

    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
