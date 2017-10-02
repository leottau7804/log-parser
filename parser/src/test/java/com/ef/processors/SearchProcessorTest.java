package com.ef.processors;

import com.ef.exception.ParserCode;
import com.ef.exception.ParserException;
import com.ef.model.Blocked;
import com.ef.processors.impl.SearchProcessor;
import com.ef.repositories.AccessLogRepository;
import com.ef.repositories.BlockedRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchProcessorTest extends Mockito {

    /**
     * Access log repository mock
     */
    @Mock
    private AccessLogRepository accessLogRepository;
    /**
     * Bloacked repository mock
     */
    @Mock
    private BlockedRepository blockedRepository;

    /**
     * Class to test
     */
    @InjectMocks
    private SearchProcessor searchProcessor = new SearchProcessor();


    /**
     * This test validate if the processor throw an exception if start date is null
     */
    @Test(expected = NullPointerException.class)
    public void case1_nullStartDateTest() {
        searchProcessor.process();
    }

    /**
     * This test validate if the processor throw an exception if threshold is null
     */
    @Test(expected = NullPointerException.class)
    public void case2_nullThresholdTest() {
        searchProcessor.setStartDate("value");
        searchProcessor.process();
    }

    /**
     * This test validate if that processor throw an exception when duration is null
     */
    @Test(expected = NullPointerException.class)
    public void case3_nullDurationTest() {
        searchProcessor.setStartDate("value");
        searchProcessor.setThreshold(100);
        searchProcessor.process();
    }

    /**
     * This test validate if the processor throw an exception when the date format is not valid.
     */
    @Test
    public void case4_invalidStartDateTest() {

        searchProcessor.setDateFormat("yyyy-MM-dd.HH:mm:ss");
        searchProcessor.setStartDate("2017-07-20 20:20:00");
        searchProcessor.setThreshold(100);
        searchProcessor.setDuration("hourly");

        try {
            searchProcessor.process();
            Assert.fail("Fail test, it must throw an exception");
        } catch (ParserException e) {
            Assert.assertEquals(ParserCode.INVALID_DATE_FORMAT, e.getParserCode());
        }
    }

    /**
     * This test validate if the processor throw an exception when the threshold is negative.
     */
    @Test
    public void case5_invalidThresholdTest() {

        searchProcessor.setDateFormat("yyyy-MM-dd.HH:mm:ss");
        searchProcessor.setStartDate("2017-07-20.20:20:00");
        searchProcessor.setThreshold(-1);
        searchProcessor.setDuration("hourly");

        try {
            searchProcessor.process();
            Assert.fail("Fail test, it must throw an exception");
        } catch (ParserException e) {
            Assert.assertEquals(ParserCode.INVALID_THRESHOLD, e.getParserCode());
        }
    }

    /**
     * This test validate if the processor throw an exception when the threshold is negative.
     */
    @Test
    public void case6_invalidDurationTest() {

        searchProcessor.setDateFormat("yyyy-MM-dd.HH:mm:ss");
        searchProcessor.setStartDate("2017-07-20.20:20:00");
        searchProcessor.setThreshold(1);
        searchProcessor.setDuration("hour");

        try {
            searchProcessor.process();
            Assert.fail("Fail test, it must throw an exception");
        } catch (ParserException e) {
            Assert.assertEquals(ParserCode.INVALID_DURATION, e.getParserCode());
        }
    }

    /**
     * Successful case
     */
    @Test
    public void case7_processZeroAccessLogOkTest() {

        searchProcessor.setDateFormat("yyyy-MM-dd.HH:mm:ss");
        searchProcessor.setStartDate("2017-07-20.20:20:00");
        searchProcessor.setThreshold(1);
        searchProcessor.setDuration("hourly");

        when(accessLogRepository.findByDateAndThreshold(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        searchProcessor.process();

        verify(accessLogRepository).findByDateAndThreshold(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyInt());
    }

    /**
     * Successful case
     */
    @Test
    public void case8_processgOkTest() {

        searchProcessor.setDateFormat("yyyy-MM-dd.HH:mm:ss");
        searchProcessor.setStartDate("2017-07-20.20:20:00");
        searchProcessor.setThreshold(1);
        searchProcessor.setDuration("hourly");

        List<String> accessLogs = new ArrayList<>();
        accessLogs.add("172.168.1.1");
        accessLogs.add("172.168.1.2");

        when(accessLogRepository.findByDateAndThreshold(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyInt()))
                .thenReturn(accessLogs);

        when(blockedRepository.save(Mockito.any(Blocked.class)))
                .thenReturn(new Blocked());

        searchProcessor.process();

        verify(accessLogRepository).findByDateAndThreshold(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyInt());
        verify(blockedRepository, times(2)).save(Mockito.any(Blocked.class));
    }


}
