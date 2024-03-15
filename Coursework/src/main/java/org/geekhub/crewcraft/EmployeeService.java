package org.geekhub.crewcraft;

import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.geekhub.crewcraft.model.EmployeeEntity;
import org.geekhub.crewcraft.repository.EmployeesRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeesRepository employeesRepository;

    public EmployeeService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public void saveEmployee(EmployeeEntity employeeEntity) {
        employeesRepository.saveRecord(employeeEntity);
    }

    public void deleteEmployee(int id) {
        employeesRepository.deleteRecord(id);
    }

    public Optional<EmployeeEntity> getEmployeeById(int id) {
        return employeesRepository.getRecord(id);
    }

    public Optional<EmployeeEntity> getEmployeeByEmail(String email) {
        return employeesRepository.getRecord(email);
    }

    public List<EmployeeEntity> getAllEmployees() {
        return employeesRepository.getRecords();
    }

    public List<EmployeeEntity> getEmployeesByCity(String city) {
        return employeesRepository.getRecords(city);
    }

    public List<EmployeeEntity> getEmployeesByPosition(EmployeePosition employeePosition) {
        return employeesRepository.getRecords(employeePosition);
    }

    public List<EmployeeEntity> getEmployeesByGender(EmployeeGender employeeGender) {
        return employeesRepository.getRecords(employeeGender);
    }
}

