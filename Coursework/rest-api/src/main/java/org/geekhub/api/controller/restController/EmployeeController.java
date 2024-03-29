package org.geekhub.api.controller.restController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.crewcraft.EmployeeService;
import org.geekhub.crewcraft.model.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    @Tag(name = "create-employee")
    public void createEmployee() {
        //employeeService.saveEmployee();
    }

}
