package org.geekhub.application.employeeCard.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.converter.EmployeeCardConverter;
import org.geekhub.application.employeeCard.EmployeeCardService;
import org.geekhub.application.employeeCard.dto.EmployeeCardDto;
import org.geekhub.application.exception.UserException;
import org.geekhub.application.user.CustomUserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class EmployeeCardController {

    private final EmployeeCardService employeeCardService;
    private final CustomUserDetailsService customUserDetailsService;

    public EmployeeCardController(
        EmployeeCardService employeeCardService,
        CustomUserDetailsService customUserDetailsService
    ) {
        this.employeeCardService = employeeCardService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    @Tag(name = "get-all-employee-cards")
    public List<EmployeeCardDto> getEmployeeCards() {
        return employeeCardService.getAllEmployees().stream()
            .map(EmployeeCardConverter::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    @Tag(name = "get-employee-card")
    public EmployeeCardDto getEmployeeCard(@PathVariable Long id) {
        return EmployeeCardConverter.toDto(employeeCardService.getEmployeeCardById(id));
    }

    @PostMapping
    @Tag(name = "create-employee-card")
    public void createEmployeeCard(@RequestBody EmployeeCardDto employeeCardDto) {
        if (!customUserDetailsService.isEmailExist(employeeCardDto.email())) {
            throw new UserException("Email not found");
        }
        employeeCardService.saveEmployeeCard(EmployeeCardConverter.employeeFromDto(employeeCardDto));
    }

    @DeleteMapping("/{id}")
    @Tag(name = "delete-employee-card")
    public void deleteEmployeeCard(@PathVariable Long id) {
        employeeCardService.deleteEmployeeCard(id);
    }


}
