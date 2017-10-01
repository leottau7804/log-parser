package com.ef.processors.impl;


import com.ef.extracters.DateExtractor;
import com.ef.extracters.IpExtractor;
import com.ef.model.AccessLog;
import com.ef.processors.Processor;
import com.ef.repositories.AccessLogRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is in charge of access log parse.
 * <p>
 * Created by sergio.leottau on 1/10/17.
 */
@Scope("prototype")
@Service
public class ParserProcessor implements Processor {

    /**
     * Logger configuration.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ParserProcessor.class);

    /**
     * Log file path
     */
    private String logFile;
    /**
     * IP address extractor
     */
    @Qualifier("ipExtractor")
    @Autowired
    private IpExtractor ipExtractor;

    /**
     * Date extractor
     */
    @Qualifier("dateExtractor")
    @Autowired
    private DateExtractor dateExtractor;


    /**
     * Access log repository
     */
    @Autowired
    private AccessLogRepository accessLogRepository;

    @Override
    public void process() {

        Validate.notBlank(logFile, "You must specify the access log path");

        int successful = 0;
        int failed = 0;

        try (Scanner scanner = new Scanner(new File(logFile))) {

            while (scanner.hasNextLine()) {
                if (processLine(scanner.nextLine())) {
                    successful++;
                } else {
                    failed++;
                }
            }

            LOGGER.info("Process results: successfully parsed {}, failed parsed {}", successful, failed);
        } catch (FileNotFoundException e) {
            LOGGER.error("The file that you specify does not exist: [{}]", logFile);
        }

    }

    /**
     * Process each line contained in access log file.
     *
     * @param line line information
     * @return true if can parse the line successfully, or false otherwise.
     */
    private boolean processLine(String line) {
        LOGGER.debug("Processing line [{}]", line);

        String[] elements = line.split("\\|");

        String ip = ipExtractor.extract(elements);
        if (StringUtils.isBlank(ip)) {
            LOGGER.error("Invalid IP. The application can not parse the line [{}]", line);
            return false;
        }

        Date date = dateExtractor.extract(elements);
        if (date == null) {
            LOGGER.error("Invalid date. The application can not parse the line [{}]", line);
            return false;
        }

        AccessLog accessLog = new AccessLog();
        accessLog.setIp(ip);
        accessLog.setDate(date);
        accessLog.setLog(line);

        accessLogRepository.save(accessLog);
        LOGGER.debug("Successfully process of line [{}]", line);
        return true;
    }


    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
}
