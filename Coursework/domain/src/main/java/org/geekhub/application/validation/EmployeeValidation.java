package org.geekhub.application.validation;

import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.exception.ValidationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class EmployeeValidation {

    public static final String FULL_NAME_REGEX = "^[A-Z][a-zA-Z]*$";
    public static final String CITY_REGEX = "^[A-Z][a-zA-Z]*$";
    public static final int MAXIMUM_AGE_TO_WORK = 80;
    public static final int MINIMUM_AGE_TO_WORK = 16;
    public static final int ONE_YEAR_TO_HIRE = 1;

    private EmployeeValidation() {
    }

    private static void isInvalidNames(String name) {
        if (name == null || name.length() <= 3 || name.length() >= 30) {
            throw new ValidationException("Name/Surname must be at range 3 - 30");
        }

        if (!Pattern.compile(FULL_NAME_REGEX).matcher(name).matches()) {
            throw new ValidationException("Name/Surname must contains valid symbols");
        }
    }

    private static boolean isValidBirthday(LocalDate birthday) {
        LocalDate minDate = LocalDate.now().minusYears(MAXIMUM_AGE_TO_WORK);
        LocalDate maxDate = LocalDate.now().minusYears(MINIMUM_AGE_TO_WORK);

        return birthday != null && !birthday.isBefore(minDate) && !birthday.isAfter(maxDate);
    }

    private static boolean isValidHireDate(LocalDate hireDate) {
        LocalDate minDate = LocalDate.now().minusYears(ONE_YEAR_TO_HIRE);
        LocalDate maxDate = LocalDate.now();

        return hireDate != null && !hireDate.isBefore(minDate) && !hireDate.isAfter(maxDate);
    }

    private static boolean isValidGenderOrPosition(EmployeeCardEntity employeeCardEntity) {
        return Arrays.asList(EmployeePosition.values()).contains(employeeCardEntity.getEmployeePosition())
               || Arrays.asList(EmployeeGender.values()).contains(employeeCardEntity.getEmployeeGender());
    }

    private static void isInvalidCity(String city) {
        if (city != null && (city.length() >= 30 || city.length() <= 2)) {
            throw new ValidationException("City name must be in range 3 - 30");
        }

        if (city != null && !Pattern.compile(CITY_REGEX).matcher(city).matches()) {
            throw new ValidationException("City name must contains valid symbols");
        }
    }

    public static EmployeeCardEntity validateFields(EmployeeCardEntity employeeCardEntity) {
        if (!isValidBirthday(employeeCardEntity.getBirthday())) {
            throw new ValidationException("Work age range from 16 to 80 y.o.");
        }

        if (!isValidGenderOrPosition(employeeCardEntity)) {
            throw new ValidationException("Invalid employee position or gender");
        }

        if (!isValidHireDate(employeeCardEntity.getHireDate())) {
            throw new ValidationException("Hire date can't be later than today, or not earlier than a year ago");
        }

        if (employeeCardEntity.getCity() != null) {
            String city = employeeCardEntity.getCity();
            if (city == null || city.isEmpty()) {
                employeeCardEntity.setCity("Unknown");
            }
        }

        isInvalidNames(employeeCardEntity.getFirstName());
        isInvalidNames(employeeCardEntity.getLastName());
        isInvalidCity(employeeCardEntity.getCity());
        employeeCardEntity.setCity(Objects.requireNonNull(employeeCardEntity.getCity()).trim());

        return employeeCardEntity;
    }
}
