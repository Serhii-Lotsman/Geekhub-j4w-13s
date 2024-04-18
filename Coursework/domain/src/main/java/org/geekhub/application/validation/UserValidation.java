package org.geekhub.application.validation;

import java.util.regex.Pattern;

public class UserValidation {

    private UserValidation() {
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String FULL_NAME_REGEX = "^[A-Z][a-zA-Z]*$";

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public static boolean validNames(String name) {
        return Pattern.compile(FULL_NAME_REGEX).matcher(name).matches();
    }
}
