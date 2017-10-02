package com.ef.exception;


import java.text.MessageFormat;

/**
 * Created by sergio.leottau on 1/10/17.
 */
public class ParserException extends RuntimeException {

    /**
     * Parser code
     */
    private ParserCode parserCode;

    /**
     * arguments
     */
    private String[] arguments;

    /**
     * @param parserCode
     * @param cause
     * @param arguments
     */
    public ParserException(ParserCode parserCode, Throwable cause, String... arguments) {
        super(parserCode.toString(), cause);
        this.parserCode = parserCode;
        this.arguments = arguments;
    }

    /**
     * @param parserCode
     * @param arguments
     */
    public ParserException(ParserCode parserCode, String... arguments) {
        super(parserCode.toString());
        this.parserCode = parserCode;
        this.arguments = arguments;
    }

    /**
     * @param parserCode
     */
    public ParserException(ParserCode parserCode) {
        super(parserCode.toString());
        this.parserCode = parserCode;
    }

    public ParserCode getParserCode() {
        return parserCode;
    }

    @Override
    public String getMessage() {

        String message = super.getMessage();

        if (parserCode != null) {

            if (arguments != null && arguments.length > 0) {
                MessageFormat messageFormat = new MessageFormat(parserCode.toString());
                message = messageFormat.format(arguments);
            } else {
                message = parserCode.toString();
            }

        }

        return message;
    }


}
