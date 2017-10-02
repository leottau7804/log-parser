package com.ef.exception;

/**
 * Created by sergio.leottau on 1/10/17.
 */
public enum ParserCode {

    INVALID_ARGUMENTS("You must specify either access log path (logFile) or searching parameters (startDate, threshold, duration)"),
    NOT_ENOUGH_SEARCH_ARGUMENTS("To do a search you must specify all search parameters (startDate, threshold, duration).");

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
