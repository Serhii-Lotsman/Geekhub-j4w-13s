package org.geekhub.application.converter;

import org.geekhub.application.employeeCard.dto.EmployeeCardDto;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.springframework.lang.NonNull;

public class EmployeeCardConverter {

    private EmployeeCardConverter() {
    }

    public static EmployeeCardEntity employeeFromDto(@NonNull EmployeeCardDto employeeCardDto) {
        EmployeeCardEntity employeeCardEntity = new EmployeeCardEntity();
        employeeCardEntity.setFirstName(employeeCardDto.firstName().trim());
        employeeCardEntity.setLastName(employeeCardDto.lastName().trim());
        employeeCardEntity.setBirthday(employeeCardDto.birthday());
        employeeCardEntity.setEmail(employeeCardDto.email().trim());
        employeeCardEntity.setEmployeePosition(employeeCardDto.employeePosition());
        employeeCardEntity.setCity(employeeCardDto.city());
        employeeCardEntity.setMarried(employeeCardDto.isMarried());
        employeeCardEntity.setEmployeeGender(employeeCardDto.employeeGender());
        employeeCardEntity.setHireDate(employeeCardDto.hireDate());
        return employeeCardEntity;
    }

    public static EmployeeCardDto employeeToDto(@NonNull EmployeeCardEntity employeeCardEntity) {
        return new EmployeeCardDto(
            employeeCardEntity.getId(),
            employeeCardEntity.getFirstName(),
            employeeCardEntity.getLastName(),
            employeeCardEntity.getBirthday(),
            employeeCardEntity.getEmail(),
            employeeCardEntity.getEmployeePosition(),
            employeeCardEntity.getCity(),
            employeeCardEntity.isMarried(),
            employeeCardEntity.getEmployeeGender(),
            employeeCardEntity.getHireDate()
        );
    }

    public static EmployeeCardEntity updateEmployeeFromDto(
        @NonNull EmployeeCardEntity employeeCardEntity,
        @NonNull EmployeeCardDto employeeCardDto
    ) {
        employeeCardEntity.setFirstName(employeeCardDto.firstName().trim());
        employeeCardEntity.setLastName(employeeCardDto.lastName().trim());
        employeeCardEntity.setBirthday(employeeCardDto.birthday());
        employeeCardEntity.setEmployeePosition(employeeCardDto.employeePosition());
        employeeCardEntity.setCity(employeeCardDto.city());
        employeeCardEntity.setMarried(employeeCardDto.isMarried());
        employeeCardEntity.setEmployeeGender(employeeCardDto.employeeGender());
        employeeCardEntity.setHireDate(employeeCardDto.hireDate());
        return employeeCardEntity;
    }
}
