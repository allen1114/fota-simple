package com.hunter.fota.exception;


public class JPushException extends RuntimeException {

    public JPushException() {
    }

    public JPushException(String message) {
        super(message);
    }

    public JPushException(String message, Throwable cause) {
        super(message, cause);
    }

    public JPushException(Throwable cause) {
        super(cause);
    }

    public JPushException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
