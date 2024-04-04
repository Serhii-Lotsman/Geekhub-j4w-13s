package org.geekhub.api.controller.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.crewcraft.employee.EmployeeService;
import org.geekhub.crewcraft.employee.converter.EmployeeConverter;
import org.geekhub.crewcraft.employee.dto.EmployeeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@OpenAPIDefinition(
    info = @Info(
        title = "CrewCraftAPI",
        version = "3.0",
        description = "HR manager application",
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "Crew Craft",
            url = "http://localhost:8089/"
        )
    )
)
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    @Tag(name = "get-all-employees")
    public List<EmployeeDto> getEmployees() {
        return employeeService.getAllEmployees().stream()
            .map(EmployeeConverter::toDTO)
            .toList();
    }



}
