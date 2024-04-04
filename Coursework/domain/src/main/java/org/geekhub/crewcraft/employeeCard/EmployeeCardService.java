package org.geekhub.crewcraft.employeeCard;

import org.geekhub.crewcraft.exception.EmployeeCardException;
import org.geekhub.repository.employeeCard.enums.EmployeeGender;
import org.geekhub.repository.employeeCard.enums.EmployeePosition;
import org.geekhub.repository.employeeCard.model.EmployeeCardEntity;
import org.geekhub.repository.employeeCard.EmployeeCardRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeCardService {

    private final EmployeeCardRepository employeeCardRepository;

    public EmployeeCardService(EmployeeCardRepository employeeCardRepository) {
        this.employeeCardRepository = employeeCardRepository;
    }

    public void saveEmployee(EmployeeCardEntity employeeCardEntity) {
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

