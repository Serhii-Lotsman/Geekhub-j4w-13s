package org.geekhub.hw6.exception;

public class FileException extends RuntimeException{

    public FileException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
