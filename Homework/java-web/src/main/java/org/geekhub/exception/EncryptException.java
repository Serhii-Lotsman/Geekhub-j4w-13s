package org.geekhub.exception;

public class EncryptException extends RuntimeException{
    public EncryptException() {
        super();
    }

    public EncryptException(String message) {
        super(message);
    }
}
