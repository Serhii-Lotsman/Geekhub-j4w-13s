package org.geekhub.hw5.exception;

public class ContentLengthNotKnownException extends Exception {
    public ContentLengthNotKnownException() {
    }

    public ContentLengthNotKnownException(String message) {
        super(message);
    }

    public ContentLengthNotKnownException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentLengthNotKnownException(Throwable cause) {
        super(cause);
    }
}
