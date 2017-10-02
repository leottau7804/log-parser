package com.ef.services.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.ef.exception.ParserCode;
import com.ef.exception.ParserException;
import com.ef.processors.impl.ParserProcessor;
import com.ef.processors.impl.SearchProcessor;
import com.ef.services.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import javax.annotation.PostConstruct;

/**
 * This class route to a processor according to the arguments given.
 * <p>
 * Created by sergio.leottau on 1/10/17.
 */
@Parameters(separators = "=")
@Service
public class RouterImpl implements Router {

    /**
     * Logger configuration.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(Router.class);

    @Parameter(names = "--logFile")
    private String logFile;

    @Parameter(names = "--startDate")
    private String startDate;

    @Parameter(names = "--threshold")
    private Integer threshold;

    @Parameter(names = "--duration")
    private String duration;

    /**
     * Application Context
     */
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void route() {
        LOGGER.debug("Router request logFile [{}], startDate [{}], threshold [{}], duration [{}]", logFile, startDate,
                threshold, duration);


        boolean isLogFileValid = StringUtils.isNotBlank(logFile);
        boolean isStartDateValid = StringUtils.isNotBlank(startDate);
        boolean isThresholdValid = threshold != null;
        boolean isDurationValid = StringUtils.isNotBlank(duration);


        if (!isLogFileValid && !isStartDateValid && !isThresholdValid && !isDurationValid) {
            throw new ParserException(ParserCode.INVALID_ARGUMENTS);
        } else if (isLogFileValid) {
            LOGGER.debug("Log parser processor");

            ParserProcessor processor = applicationContext.getBean(ParserProcessor.class);
            processor.setLogFile(logFile);
            processor.process();
        }

        if (isStartDateValid && isThresholdValid && isDurationValid) {
            LOGGER.debug("Log searching processor");

            SearchProcessor processor = applicationContext.getBean(SearchProcessor.class);
            processor.setStartDate(startDate);
            processor.setThreshold(threshold);
            processor.setDuration(duration);
            processor.process();


        } else if (isStartDateValid || isThresholdValid || isDurationValid) {
            throw new ParserException(ParserCode.NOT_ENOUGH_SEARCH_ARGUMENTS);
        }
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
