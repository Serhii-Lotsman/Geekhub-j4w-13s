package org.geekhub.crewcraft.controller;

import org.geekhub.crewcraft.EmployeeService;
import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.geekhub.crewcraft.model.EmployeeDTO;
import org.geekhub.crewcraft.model.EmployeeEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public void createEmployee(
        String fullName,
        LocalDate birthday,
        String email,
        EmployeePosition position,
        String password,
        String city,
        boolean isMarried,
        EmployeeGender gender
    ) {
        EmployeeEntity employee = new EmployeeEntity(
            fullName, birthday, email, position, password, city, isMarried, gender, OffsetDateTime.now()
        );
        employeeService.saveEmployee(employee);
    }

}
