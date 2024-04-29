package org.geekhub.application.validation;

import org.geekhub.application.exception.ValidationException;

import java.util.regex.Pattern;

public class UserValidation {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String UPPERCASE_LETTER_PASSWORD = ".*[A-Z].*";
    public static final String DIGIT_PASSWORD = ".*\\d.*";
    public static final String ALPHANUMERIC_PASSWORD = "^[a-zA-Z0-9]*$";
    public static final int MINIMUM_SYMBOL = 6;
    public static final int MAXIMUM_SYMBOL = 30;

    private UserValidation() {
    }

    public static boolean isInvalidEmail(String email) {
        if (email == null || email.length() < MINIMUM_SYMBOL || email.length() > MAXIMUM_SYMBOL) {
            throw new ValidationException("Email must be at range 6 - 30");
        }

        if (!Pattern.compile(EMAIL_REGEX).matcher(email).matches()) {
            throw new ValidationException("Email must contains valid symbols");
        }
        return false;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < MINIMUM_SYMBOL || password.length() > MAXIMUM_SYMBOL) {
            throw new ValidationException("Password must be at range 6 - 30");
        }

        if (!Pattern.compile(UPPERCASE_LETTER_PASSWORD).matcher(password).matches()) {
            throw new ValidationException("Password must contain at least one uppercase letter");
        }

        if (!Pattern.compile(DIGIT_PASSWORD).matcher(password).matches()) {
            throw new ValidationException("Password must contain at least one digit");
        }

        if (!Pattern.compile(ALPHANUMERIC_PASSWORD).matcher(password).matches()) {
            throw new ValidationException("Password must be alphanumeric");
        }
        return true;
    }
}
