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
@RequestMapping("/api/v1/employees")
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
    @ResponseStatus(reason = "Employee card created", value = HttpStatus.CREATED)
    public void createEmployeeCard(@RequestBody EmployeeCardDto employeeCardDto) {
        employeeCardService.saveEmployeeCard(EmployeeCardConverter.employeeFromDto(employeeCardDto));
    }

    @PutMapping("/{cardId}")
    @ResponseStatus(reason = "Employee card updated", value = HttpStatus.OK)
    public void updateEmployeeCard(@PathVariable long cardId, @RequestBody EmployeeCardDto employeeCardDto) {
        EmployeeCardEntity employeeCardEntity = employeeCardService.getEmployeeCardById(cardId);
        if (employeeCardEntity != null) {
            employeeCardService.updateEmployeeCard(
                EmployeeCardConverter.updateEmployeeFromDto(employeeCardEntity, employeeCardDto)
            );
        }
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(reason = "Employee card deleted", value = HttpStatus.NO_CONTENT)
    public void deleteEmployeeCard(@PathVariable Long cardId) {
        employeeCardService.deleteEmployeeCard(cardId);
    }
}
