package com.ef.exception;

/**
 * Created by sergio.leottau on 1/10/17.
 */
public enum ParserCode {

    INVALID_ARGUMENTS("You must specify either access log path (logFile) or searching parameters (startDate, threshold, duration)"),
    NOT_ENOUGH_SEARCH_ARGUMENTS("To do a search you must specify all search parameters (startDate, threshold, duration)."),
    LOG_FILE_NOT_FOUND("The file that you specify does not exist: {0}"),
    INVALID_DATE_FORMAT("Invalid date format, try a date argument with a format like {0}"),
    INVALID_THRESHOLD("Invalid threshold, this argument can not be negative."),
    INVALID_DURATION("Invalid duration value. The only duration valid values are: hourly or daily");

    /**
     * Code message
     */
    private final String message;

    /**
     * Contructor of the enumeration
     * @param message
     */
    private ParserCode(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }


}
