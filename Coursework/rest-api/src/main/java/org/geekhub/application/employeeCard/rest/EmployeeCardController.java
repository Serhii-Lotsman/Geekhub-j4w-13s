package org.geekhub.application.employeeCard.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.converter.EmployeeCardConverter;
import org.geekhub.application.employeeCard.EmployeeCardService;
import org.geekhub.application.employeeCard.dto.EmployeeCardDto;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequestMapping("/api/v3/employees")
@Tag(name = "employee-card")
public class EmployeeCardController {

    private final EmployeeCardService employeeCardService;

    public EmployeeCardController(EmployeeCardService employeeCardService) {
        this.employeeCardService = employeeCardService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeCardDto> getEmployeeCards() {
        return employeeCardService.getAllEmployees().stream()
            .map(EmployeeCardConverter::employeeToDto)
            .toList();
    }

    @GetMapping("/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeCardDto getEmployeeCard(@PathVariable Long cardId) {
        return EmployeeCardConverter.employeeToDto(
            employeeCardService.getEmployeeCardById(cardId)
        );
    }

    @GetMapping("/my-card")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeCardDto getMyCard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return EmployeeCardConverter.employeeToDto(
            employeeCardService.getEmployeeCardByEmail(userEmail)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createEmployeeCard(@RequestBody EmployeeCardDto employeeCardDto) {
        employeeCardService.saveEmployeeCard(EmployeeCardConverter.employeeFromDto(employeeCardDto));
        return "Employee card created";
    }

    @PutMapping("/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateEmployeeCard(@PathVariable long cardId, @RequestBody EmployeeCardDto employeeCardDto) {
        EmployeeCardEntity employeeCardEntity = employeeCardService.getEmployeeCardById(cardId);
        if (employeeCardEntity != null) {
            employeeCardService.updateEmployeeCard(
                EmployeeCardConverter.updateEmployeeFromDto(employeeCardEntity, employeeCardDto)
            );
        }
        return "Employee card updated";
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteEmployeeCard(@PathVariable Long cardId) {
        employeeCardService.deleteEmployeeCard(cardId);
        return "Employee card deleted";
    }
}
