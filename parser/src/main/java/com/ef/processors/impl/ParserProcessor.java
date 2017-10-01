package com.ef.processors.impl;


import com.ef.processors.Processor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@Scope("prototype")
@Service
public class ParserProcessor implements Processor {


    /**
     * Log file path
     */
    private String logFile;

    @Override
    public void process() {



    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
}
