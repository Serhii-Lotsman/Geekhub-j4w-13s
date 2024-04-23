package org.geekhub.application.exception;

public class UniqueUserException extends RuntimeException {
    public UniqueUserException(String message) {
        super(message);
    }
}
