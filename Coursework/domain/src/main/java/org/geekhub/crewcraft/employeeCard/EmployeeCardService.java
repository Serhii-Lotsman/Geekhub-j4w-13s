package org.geekhub.crewcraft.employeeCard;

import org.geekhub.repository.employeeCard.enums.EmployeeGender;
import org.geekhub.repository.employeeCard.enums.EmployeePosition;
import org.geekhub.repository.employeeCard.model.EmployeeCardEntity;
import org.geekhub.repository.employeeCard.EmployeeCardRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Optional<EmployeeCardEntity> getEmployeeById(int id) {
        return employeeCardRepository.getRecord(id);
    }

    public Optional<EmployeeCardEntity> getEmployeeByEmail(String email) {
        return employeeCardRepository.getRecord(email);
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

