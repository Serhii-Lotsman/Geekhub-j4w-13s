package org.geekhub.crewcraft.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.geekhub.crewcraft.EmployeeService;
import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.geekhub.crewcraft.model.EmployeeDTO;
import org.geekhub.crewcraft.model.EmployeeEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;
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
    public void createEmployee(
        @RequestParam @Parameter(description = "Name and Surname (ex. Lionel Messi)") String fullName,
        @RequestParam @Parameter(description = "yyyy-MM-dd") LocalDate birthday,
        @RequestParam @Parameter(description = "example@any.com") String email,
        @RequestParam EmployeePosition position,
        @RequestParam
        @Size(min = 6, message = "Password must be at least 6 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and numbers")
        String password,
        @RequestParam(required = false) String city,
        @RequestParam boolean isMarried,
        @RequestParam EmployeeGender gender
    ) {
        EmployeeEntity employee = new EmployeeEntity(
            fullName, birthday, email, position, password, city, isMarried, gender, OffsetDateTime.now()
        );
        employeeService.saveEmployee(employee);
    }

}
