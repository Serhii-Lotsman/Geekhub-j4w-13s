package org.geekhub.application.employeeCard;

import org.geekhub.application.exception.EmployeeCardException;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.validation.UserValidation;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeCardService {

    private final EmployeeCardRepository employeeCardRepository;

    public EmployeeCardService(EmployeeCardRepository employeeCardRepository) {
        this.employeeCardRepository = employeeCardRepository;
    }

    public void saveEmployeeCard(EmployeeCardEntity employeeCardEntity) {
        if (!Arrays.asList(EmployeePosition.values()).contains(employeeCardEntity.getEmployeePosition())) {
            throw new ValidationException("Invalid employee position");
        }

        if (!Arrays.asList(EmployeeGender.values()).contains(employeeCardEntity.getEmployeeGender())) {
            throw new ValidationException("Invalid employee gender");
        }

        if (isInvalidName(employeeCardEntity.getFirstName())
            || isInvalidName(employeeCardEntity.getLastName())) {
            throw new ValidationException("Invalid format name or surname");
        }

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

    private static boolean isInvalidName(String name) {
        return name.trim().length() >= 30 || name.trim().length() <= 3 || !UserValidation.validNames(name);
    }
}

