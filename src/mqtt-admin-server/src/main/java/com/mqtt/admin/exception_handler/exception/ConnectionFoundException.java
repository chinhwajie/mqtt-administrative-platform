package com.mqtt.admin.exception_handler.exception;

public class ConnectionFoundException extends RuntimeException {
    public ConnectionFoundException() {
        super("Connection found!");
    }

    public ConnectionFoundException(String message) {
        super(message);
    }

    public ConnectionFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionFoundException(Throwable cause) {
        super(cause);
    }

    public ConnectionFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
