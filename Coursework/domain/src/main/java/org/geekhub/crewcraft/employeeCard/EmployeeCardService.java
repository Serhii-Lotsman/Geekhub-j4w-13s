package org.geekhub.crewcraft.employeeCard;

import org.geekhub.crewcraft.exception.AuthException;
import org.geekhub.crewcraft.exception.EmployeeCardException;
import org.geekhub.crewcraft.validation.UserValidation;
import org.geekhub.repository.enums.EmployeeGender;
import org.geekhub.repository.enums.EmployeePosition;
import org.geekhub.repository.employeeCard.model.EmployeeCardEntity;
import org.geekhub.repository.employeeCard.EmployeeCardRepository;
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
        if ((employeeCardEntity.getCity() != null
            && employeeCardEntity.getCity().trim().isBlank())) {
            employeeCardEntity.setCity("Unknown");
        } else if (employeeCardEntity.getCity().length() >= 30) {
            throw new AuthException("Invalid city name. Maximum character equals 30");
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

