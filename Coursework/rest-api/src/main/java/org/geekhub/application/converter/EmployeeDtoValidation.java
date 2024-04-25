package org.geekhub.application.converter;

import org.geekhub.application.employeeCard.dto.EmployeeCardDto;
import org.geekhub.application.exception.ValidationException;

import static org.geekhub.application.validation.EmployeeValidation.isInvalidCity;
import static org.geekhub.application.validation.EmployeeValidation.isInvalidNames;
import static org.geekhub.application.validation.EmployeeValidation.isValidBirthday;
import static org.geekhub.application.validation.EmployeeValidation.isValidGenderOrPosition;
import static org.geekhub.application.validation.EmployeeValidation.isValidHireDate;

public class EmployeeDtoValidation {

    private EmployeeDtoValidation() {
    }

    public static void validateDtoFields(EmployeeCardDto employeeCardDto) {
        if (!isValidBirthday(employeeCardDto.birthday())) {
            throw new ValidationException("Work age range from 16 to 80 y.o.");
        }

        if (!isValidGenderOrPosition(employeeCardDto.employeePosition(), employeeCardDto.employeeGender())) {
            throw new ValidationException("Invalid employee position or gender");
        }

        if (!isValidHireDate(employeeCardDto.hireDate())) {
            throw new ValidationException("Hire date can't be later than today, or not earlier than a year ago");
        }

        isInvalidNames(employeeCardDto.firstName());
        isInvalidNames(employeeCardDto.lastName());
        isInvalidCity(employeeCardDto.city());
    }
}
