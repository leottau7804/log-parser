package com.ef;

import com.beust.jcommander.JCommander;
import com.ef.exception.ParserException;
import com.ef.services.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Starter class.
 * <p>
 * Created by sergio.leottau on 30/09/17.
 */
public class Parser {

    /**
     * Logger configuration.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(Parser.class);


    /**
     * Main process
     *
     * @param args arguments.
     */
    public static void main(String[] args) {

        LOGGER.info("################[ACCESS LOG PARSER - BEGIN]#################");

        try {
            AnnotationConfigApplicationContext appContext
                    = new AnnotationConfigApplicationContext();
            appContext.register(AppConfigurator.class);
            appContext.refresh();


            Router router = appContext.getBean(Router.class);

            JCommander.newBuilder()
                    .addObject(router)
                    .build()
                    .parse(args);

            router.route();
        } catch (ParserException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("################[ACCESS LOG PARSER - FINISH]#################");
    }

}
