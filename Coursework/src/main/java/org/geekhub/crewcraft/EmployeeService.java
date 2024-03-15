package org.geekhub.crewcraft;

import org.geekhub.crewcraft.converter.EmployeeConverter;
import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.geekhub.crewcraft.model.EmployeeDTO;
import org.geekhub.crewcraft.model.EmployeeEntity;
import org.geekhub.crewcraft.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<EmployeeDTO> getEmployeeById(int id) {
        Optional<EmployeeEntity> employee = employeeRepository.getRecord(id);
        return employee.map(EmployeeConverter::toDTO);
    }

    public Optional<EmployeeDTO> getEmployeeByEmail(String email) {
        Optional<EmployeeEntity> employee = employeeRepository.getRecord(email);
        return employee.map(EmployeeConverter::toDTO);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.getRecords();
        return getEmployeeDTOList(employees);
    }

    public List<EmployeeDTO> getEmployeesByCity(String city) {
        List<EmployeeEntity> employees = employeeRepository.getRecords(city);
        return getEmployeeDTOList(employees);
    }

    public List<EmployeeDTO> getEmployeesByPosition(EmployeePosition employeePosition) {
        List<EmployeeEntity> employees = employeeRepository.getRecords(employeePosition);
        return getEmployeeDTOList(employees);
    }

    public List<EmployeeDTO> getEmployeesByGender(EmployeeGender employeeGender) {
        List<EmployeeEntity> employees = employeeRepository.getRecords(employeeGender);
        return getEmployeeDTOList(employees);
    }

    private List<EmployeeDTO> getEmployeeDTOList(List<EmployeeEntity> employees) {
        return employees.stream()
            .map(EmployeeConverter::toDTO)
            .collect(Collectors.toList());
    }
}

