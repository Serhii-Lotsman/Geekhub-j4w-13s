package org.geekhub.crewcraft.exception;

public class EmployeeCardException extends RuntimeException {
    public EmployeeCardException(String message) {
        super(message);
    }

    public EmployeeCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
