package com.mqtt.admin.exception_handler.exception;

public class ActiveConnectionFoundException extends RuntimeException {
    public ActiveConnectionFoundException() {
    }

    public ActiveConnectionFoundException(String message) {
        super(message);
    }

    public ActiveConnectionFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActiveConnectionFoundException(Throwable cause) {
        super(cause);
    }

    public ActiveConnectionFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
