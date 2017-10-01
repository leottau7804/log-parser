package com.ef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Starter class.
 *
 * Created by sergio.leottau on 30/09/17.
 */
public class Parser {

    /**
     * Logger configuration.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(Parser.class);

    public static void main(String[] args) {

        LOGGER.info("################[ACCESS LOG PARSER - BEGIN]#################");

        AnnotationConfigApplicationContext appContext
                = new AnnotationConfigApplicationContext();
        appContext.register(AppConfigurator.class);
        appContext.refresh();


        LOGGER.info("################[ACCESS LOG PARSER - FINISH]#################");
    }

}
