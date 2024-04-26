package org.geekhub.application;

import org.geekhub.application.exception.DatabaseException;
import org.geekhub.application.exception.EmployeeCardException;
import org.geekhub.application.exception.SessionException;
import org.geekhub.application.exception.UniqueUserException;
import org.geekhub.application.exception.UserException;
import org.geekhub.application.exception.UserExistException;
import org.geekhub.application.exception.ValidationException;
import org.springdoc.api.ErrorMessage;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage validationErrorMessage(ValidationException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler({UniqueUserException.class, UserExistException.class, SessionException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage existErrorMessage(RuntimeException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler({UserException.class, EmployeeCardException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundErrorMessage(RuntimeException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler({DatabaseException.class, NestedRuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage dbErrorMessage(RuntimeException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}
