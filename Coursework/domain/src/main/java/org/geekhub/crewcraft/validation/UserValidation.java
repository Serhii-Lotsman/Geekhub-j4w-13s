package org.geekhub.crewcraft.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidation {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String FULL_NAME_REGEX = "^[a-zA-Z]+\\s[a-zA-Z]+$";

    public boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public boolean isValidFullName(String fullName) {
        return Pattern.compile(FULL_NAME_REGEX).matcher(fullName).matches();
    }
}
