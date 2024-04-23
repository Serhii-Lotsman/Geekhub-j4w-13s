package org.geekhub.application.employeeCard.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.converter.EmployeeCardConverter;
import org.geekhub.application.employeeCard.EmployeeCardService;
import org.geekhub.application.employeeCard.dto.EmployeeCardDto;
import org.springframework.http.HttpStatus;
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
        return EmployeeCardConverter.employeeToDto(employeeCardService.getEmployeeCardById(cardId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployeeCard(@RequestBody EmployeeCardDto employeeCardDto) {
        employeeCardService.saveEmployeeCard(EmployeeCardConverter.employeeFromDto(employeeCardDto));
    }

    /*@PutMapping("/{cardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateEmployeeCard(@RequestBody EmployeeCardDto employeeCardDto, @PathVariable String cardId) {
        employeeCardService.updateEmployeeCard(EmployeeCardConverter.employeeFromDto(employeeCardDto));
    }*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeCard(@PathVariable Long id) {
        employeeCardService.deleteEmployeeCard(id);
    }
}
