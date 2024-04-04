package org.geekhub.crewcraft.employee;

import org.geekhub.repository.employee.enums.EmployeeGender;
import org.geekhub.repository.employee.enums.EmployeePosition;
import org.geekhub.repository.employee.model.EmployeeEntity;
import org.geekhub.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void saveEmployee(EmployeeEntity employeeEntity) {
        employeeRepository.saveEmployee(employeeEntity);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteRecord(id);
    }

    public Optional<EmployeeEntity> getEmployeeById(int id) {
        return employeeRepository.getRecord(id);
    }

    public Optional<EmployeeEntity> getEmployeeByEmail(String email) {
        return employeeRepository.getRecord(email);
    }

    public List<EmployeeEntity> getAllEmployees() {
       return employeeRepository.getRecords();
    }

    public List<EmployeeEntity> getEmployeesByCity(String city) {
        return employeeRepository.getRecords(city);
    }

    public List<EmployeeEntity> getEmployeesByPosition(EmployeePosition employeePosition) {
        return employeeRepository.getRecords(employeePosition);
    }

    public List<EmployeeEntity> getEmployeesByGender(EmployeeGender employeeGender) {
        return employeeRepository.getRecords(employeeGender);
    }
}

