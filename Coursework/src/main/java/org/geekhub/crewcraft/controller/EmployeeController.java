package org.geekhub.crewcraft.controller;

import org.geekhub.crewcraft.EmployeeService;
import org.geekhub.crewcraft.converter.EmployeeConverter;
import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.geekhub.crewcraft.model.EmployeeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<EmployeeDTO> employeeRecordList() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public void employeeCreate(
        String fullName,
        LocalDate birthday,
        String email,
        EmployeePosition position,
        String password,
        String city,
        boolean isMarried,
        EmployeeGender gender
    ) {
        EmployeeDTO employeeDTO = new EmployeeDTO(
            null, fullName, birthday, email, position, password, city, isMarried, gender, null
        );
        employeeService.saveEmployee(EmployeeConverter.toEntity(employeeDTO));
    }

}
