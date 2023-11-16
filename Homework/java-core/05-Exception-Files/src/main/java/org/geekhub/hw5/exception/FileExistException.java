package org.geekhub.hw5.exception;

public class FileExistException extends Exception {
    public FileExistException() {
    }

    public FileExistException(String message) {
        super(message);
    }

    public FileExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileExistException(Throwable cause) {
        super(cause);
    }
}
