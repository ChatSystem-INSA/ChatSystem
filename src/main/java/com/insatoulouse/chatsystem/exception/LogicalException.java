package com.insatoulouse.chatsystem.exception;


public class LogicalException extends Exception {
    public LogicalException() {
    }

    public LogicalException(String message) {
        super(message);
    }

    public LogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicalException(Throwable cause) {
        super(cause);
    }
}
