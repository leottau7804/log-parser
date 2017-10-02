package com.ef.processors;

import com.ef.exception.ParserCode;
import com.ef.exception.ParserException;
import com.ef.extracters.DateExtractor;
import com.ef.extracters.IpExtractor;
import com.ef.processors.impl.ParserProcessor;
import com.ef.repositories.AccessLogRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ParserProcessorTest extends Mockito {


    /**
     * IP address extractor mock
     */
    @Mock
    private IpExtractor ipExtractor;
    /**
     * Date extractor mock
     */
    @Mock
    private DateExtractor dateExtractor;
    /**
     * Access log repository mock
     */
    @Mock
    private AccessLogRepository accessLogRepository;
    /**
     * Class to test.
     */
    @InjectMocks
    private ParserProcessor parserProcessor = new ParserProcessor();

    /**
     * This test validate
     */
    @Test(expected = NullPointerException.class)
    public void case1_invalidLogFile() {
        parserProcessor.process();
    }

    /**
     * This test validate that the processor throw an exception when the file is not found
     */
    @Test
    public void case2_fileNotFound() {

        parserProcessor.setLogFile("invalid_file");
        try {
            parserProcessor.process();
        } catch (ParserException e) {
            Assert.assertEquals(ParserCode.LOG_FILE_NOT_FOUND, e.getParserCode());
        }
    }
}
