package com.mqtt.admin.exception_handler.exception;

public class ConnectionNotFoundException extends RuntimeException {
    public ConnectionNotFoundException() {
        super("Connection not found!");
    }

    public ConnectionNotFoundException(String message) {
        super(message);
    }

    public ConnectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConnectionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
