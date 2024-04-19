package org.geekhub.application.employeeCard;

import org.geekhub.application.exception.EmployeeCardException;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.validation.UserValidation;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeCardService {

    public static final int MAXIMUM_AGE_TO_WORK = 80;
    public static final int MINIMUM_AGE_TO_WORK = 16;
    public static final int ONE_YEAR_TO_HIRE = 1;
    private final EmployeeCardRepository employeeCardRepository;

    public EmployeeCardService(EmployeeCardRepository employeeCardRepository) {
        this.employeeCardRepository = employeeCardRepository;
    }

    public void saveEmployeeCard(EmployeeCardEntity employeeCardEntity) {
        if (employeeCardRepository.employeeEmailExist(employeeCardEntity.getEmail())) {
            throw new ValidationException("Email is already taken!");
        }

        if (!isValidBirthday(employeeCardEntity.getBirthday())) {
            throw new ValidationException("Work age range from 16 to 80 y.o.");
        }

        if (!isValidHireDate(employeeCardEntity.getHireDate())) {
            throw new ValidationException("Hire date can't be later than today, or not earlier than a year ago");
        }

        if (!isValidGenderOrPosition(employeeCardEntity)) {
            throw new ValidationException("Invalid employee position or gender");
        }

        if (isInvalidName(employeeCardEntity.getFirstName())
            || isInvalidName(employeeCardEntity.getLastName())) {
            throw new ValidationException("Invalid format name or surname");
        }

        isValidCity(employeeCardEntity);

        employeeCardRepository.saveEmployeeCard(employeeCardEntity);
    }

    public void deleteEmployeeCard(Long id) {
        employeeCardRepository.deleteEmployeeCard(id);
    }

    public EmployeeCardEntity getEmployeeCardById(Long id) {
        return employeeCardRepository.getEmployeeCard(id)
            .orElseThrow(() -> new EmployeeCardException("EmployeeCard with id " + id + " not found"));
    }

    public EmployeeCardEntity getEmployeeCardByEmail(String email) {
        return employeeCardRepository.getEmployeeCard(email)
            .orElseThrow(() -> new EmployeeCardException("EmployeeCard with email " + email + " not found"));
    }

    public List<EmployeeCardEntity> getAllEmployees() {
        return employeeCardRepository.getEmployeeCards();
    }

    public List<EmployeeCardEntity> getEmployeeCardsByCity(String city) {
        return employeeCardRepository.getEmployeeCards(city);
    }

    public List<EmployeeCardEntity> getEmployeeCardsByPosition(EmployeePosition employeePosition) {
        return employeeCardRepository.getEmployeeCards(employeePosition);
    }

    public List<EmployeeCardEntity> getEmployeeCardsByGender(EmployeeGender employeeGender) {
        return employeeCardRepository.getEmployeeCards(employeeGender);
    }

    private boolean isInvalidName(String name) {
        return name.trim().length() >= 30 || name.trim().length() <= 3 || !UserValidation.validNames(name);
    }

    private boolean isValidBirthday(LocalDate birthday) {
        LocalDate minDate = LocalDate.now().minusYears(MAXIMUM_AGE_TO_WORK);
        LocalDate maxDate = LocalDate.now().minusYears(MINIMUM_AGE_TO_WORK);

        return birthday != null && !birthday.isBefore(minDate) && !birthday.isAfter(maxDate);
    }

    private boolean isValidHireDate(LocalDate hireDate) {
        LocalDate minDate = LocalDate.now().minusYears(ONE_YEAR_TO_HIRE);
        LocalDate maxDate = LocalDate.now();

        return hireDate != null && !hireDate.isBefore(minDate) && !hireDate.isAfter(maxDate);
    }

    private boolean isValidGenderOrPosition(EmployeeCardEntity employeeCardEntity) {
        return Arrays.asList(EmployeePosition.values()).contains(employeeCardEntity.getEmployeePosition())
               || Arrays.asList(EmployeeGender.values()).contains(employeeCardEntity.getEmployeeGender());
    }

    private void isValidCity(EmployeeCardEntity employeeCardEntity) {
        if (employeeCardEntity.getCity() != null) {
            String city = employeeCardEntity.getCity();
            if (city == null || city.trim().isEmpty()) {
                employeeCardEntity.setCity("Unknown");
            } else if (city.length() >= 30) {
                throw new ValidationException("Invalid city name. Maximum character equals 30");
            }
        } else {
            throw new ValidationException("Employee card entity is null");
        }
    }
}

