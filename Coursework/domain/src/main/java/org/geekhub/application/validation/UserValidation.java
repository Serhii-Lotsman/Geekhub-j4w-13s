package org.geekhub.application.validation;

import org.geekhub.application.exception.ValidationException;

import java.util.regex.Pattern;

public class UserValidation {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String UPPERCASE_LETTER_PASSWORD = ".*[A-Z].*";
    public static final String DIGIT_PASSWORD = ".*\\d.*";
    public static final String ALPHANUMERIC_PASSWORD = "^[a-zA-Z0-9]*$";

    private UserValidation() {
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.length() <= 6 || email.length() >= 30) {
            throw new ValidationException("Email must be at range 6 - 30");
        }

        if (!Pattern.compile(EMAIL_REGEX).matcher(email).matches()) {
            throw new ValidationException("Email must contains valid symbols");
        }
        return true;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() <= 6 || password.length() >= 30) {
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
