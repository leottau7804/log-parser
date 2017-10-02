package com.ef.services;

import com.ef.exception.ParserCode;
import com.ef.exception.ParserException;
import com.ef.processors.impl.ParserProcessor;
import com.ef.processors.impl.SearchProcessor;
import com.ef.services.impl.RouterImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class RouterImplTest extends Mockito {


    /**
     * Application context mock
     */
    @Mock
    private ApplicationContext applicationContext;

    /**
     * Parser processor mock
     */
    @Mock
    private ParserProcessor parserProcessor;

    /**
     * Search processor mock
     */
    @Mock
    private SearchProcessor searchProcessor;

    /**
     * Class to test
     */
    @InjectMocks
    private RouterImpl router = new RouterImpl();

    /**
     * This test validate if Router throw an exception when no argument are given.
     */
    @Test
    public void case1_invalidArgumentTest() {

        try {
            router.route();
            Assert.fail("Fail test, it must throw an exception");
        } catch (ParserException e) {
            Assert.assertEquals(ParserCode.INVALID_ARGUMENTS, e.getParserCode());
        }
    }

    /**
     * This test validate if Router throw an exception when searchin arguments are not enough.
     */
    @Test
    public void case2_notEnoughSearchArgumentsTest() {

        router.setThreshold(100);

        try {
            router.route();
            Assert.fail("Fail test, it must throw an exception");
        } catch (ParserException e) {
            Assert.assertEquals(ParserCode.NOT_ENOUGH_SEARCH_ARGUMENTS, e.getParserCode());
        }
    }

    /**
     * This test validate if Router call parser processor when logFile argument is given correctly.
     */
    @Test
    public void case3_parserProcessorOkTest() {

        //Mocks
        router.setLogFile("/User/access_log");
        when(applicationContext.getBean(ParserProcessor.class))
                .thenReturn(parserProcessor);
        Mockito.doNothing().when(parserProcessor).process();

        //Test
        router.route();

        //Validations
        verify(applicationContext).getBean(ParserProcessor.class);
        verify(parserProcessor).process();
    }


    /**
     * This test validate if Router call parser processor when logFile argument is given correctly.
     */
    @Test
    public void case4_searchProcessorOkTest() {

        //Mocks
        router.setStartDate("2017-01-01.13:00:00");
        router.setThreshold(100);
        router.setDuration("hourly");

        when(applicationContext.getBean(SearchProcessor.class))
                .thenReturn(searchProcessor);
        Mockito.doNothing().when(searchProcessor).process();

        //Test
        router.route();

        //Validations
        verify(applicationContext).getBean(SearchProcessor.class);
        verify(searchProcessor).process();
    }

}
