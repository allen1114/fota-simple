package com.hunter.fota.exception;

public class LogicalErrorException extends RuntimeException {

    public LogicalErrorException() {
    }

    public LogicalErrorException(String message) {
        super(message);
    }

    public LogicalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicalErrorException(Throwable cause) {
        super(cause);
    }

    public LogicalErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
