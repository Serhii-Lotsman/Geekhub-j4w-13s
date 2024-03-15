package org.geekhub.crewcraft.controller;

import org.geekhub.crewcraft.EmployeeService;
import org.geekhub.crewcraft.model.EmployeeEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<EmployeeEntity> employeeRecordList() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public int employeeCreate() {
        return 2;
    }

}
