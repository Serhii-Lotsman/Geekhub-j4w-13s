package org.geekhub.application.employeeCard;

import org.geekhub.application.exception.AuthException;
import org.geekhub.application.exception.EmployeeCardException;
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
    private final UserValidation userValidation;

    public EmployeeCardService(EmployeeCardRepository employeeCardRepository, UserValidation userValidation) {
        this.employeeCardRepository = employeeCardRepository;
        this.userValidation = userValidation;
    }

    public void saveEmployee(EmployeeCardEntity employeeCardEntity) {
        if (!Arrays.asList(EmployeePosition.values()).contains(employeeCardEntity.getEmployeePosition())) {
            throw new AuthException("Invalid employee position");
        }
        if (!Arrays.asList(EmployeeGender.values()).contains(employeeCardEntity.getEmployeeGender())) {
            throw new AuthException("Invalid employee gender");
        }
        if (!userValidation.isValidFullName(employeeCardEntity.getFullName())
            || employeeCardEntity.getFullName().trim().length() <= 3
               && employeeCardEntity.getFullName().trim().length() >= 30) {
            throw new AuthException("Invalid full name. Example: 'First Last'");
        }
        if (employeeCardEntity.getCity() != null) {
            String city = employeeCardEntity.getCity();
            if (city == null || city.trim().isEmpty()) {
                employeeCardEntity.setCity("Unknown");
            } else if (city.length() >= 30) {
                throw new AuthException("Invalid city name. Maximum character equals 30");
            }
        } else {
            throw new AuthException("Employee card entity is null");
        }
        employeeCardRepository.saveEmployee(employeeCardEntity);
    }

    public void deleteEmployee(int id) {
        employeeCardRepository.deleteRecord(id);
    }

    public EmployeeCardEntity getEmployeeById(int id) {
        return employeeCardRepository.getRecord(id)
            .orElseThrow(() -> new EmployeeCardException("EmployeeCard with id " + id + " not found"));
    }

    public EmployeeCardEntity getEmployeeByEmail(String email) {
        return employeeCardRepository.getRecord(email)
            .orElseThrow(() -> new EmployeeCardException("EmployeeCard with email " + email + " not found"));
    }

    public List<EmployeeCardEntity> getAllEmployees() {
        return employeeCardRepository.getRecords();
    }

    public List<EmployeeCardEntity> getEmployeesByCity(String city) {
        return employeeCardRepository.getRecords(city);
    }

    public List<EmployeeCardEntity> getEmployeesByPosition(EmployeePosition employeePosition) {
        return employeeCardRepository.getRecords(employeePosition);
    }

    public List<EmployeeCardEntity> getEmployeesByGender(EmployeeGender employeeGender) {
        return employeeCardRepository.getRecords(employeeGender);
    }
}

