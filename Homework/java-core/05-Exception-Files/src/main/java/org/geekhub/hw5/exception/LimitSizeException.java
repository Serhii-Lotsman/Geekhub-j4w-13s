package org.geekhub.hw5.exception;

public class LimitSizeException extends Exception {
    public LimitSizeException() {
    }

    public LimitSizeException(String message) {
        super(message);
    }

    public LimitSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitSizeException(Throwable cause) {
        super(cause);
    }
}
