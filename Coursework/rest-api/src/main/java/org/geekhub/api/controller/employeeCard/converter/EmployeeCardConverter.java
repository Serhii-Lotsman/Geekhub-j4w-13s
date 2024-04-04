package org.geekhub.api.controller.employeeCard.converter;

import org.geekhub.api.controller.employeeCard.dto.EmployeeCardDto;
import org.geekhub.repository.employeeCard.model.EmployeeCardEntity;
import org.springframework.lang.NonNull;

public class EmployeeCardConverter {

    private EmployeeCardConverter() {
    }

    public static EmployeeCardEntity toEntity(@NonNull EmployeeCardDto employeeCardDto) {
        return new EmployeeCardEntity(
            employeeCardDto.id(),
            employeeCardDto.fullName(),
            employeeCardDto.birthday(),
            employeeCardDto.email(),
            employeeCardDto.employeePosition(),
            employeeCardDto.city(),
            employeeCardDto.isMarried(),
            employeeCardDto.employeeGender(),
            employeeCardDto.hireDate()
        );
    }

    public static EmployeeCardDto toDTO(@NonNull EmployeeCardEntity employeeCardEntity) {
        return new EmployeeCardDto(
            employeeCardEntity.getId(),
            employeeCardEntity.getFullName(),
            employeeCardEntity.getBirthday(),
            employeeCardEntity.getEmail(),
            employeeCardEntity.getEmployeePosition(),
            employeeCardEntity.getCity(),
            employeeCardEntity.isMarried(),
            employeeCardEntity.getEmployeeGender(),
            employeeCardEntity.getHireDate()
        );
    }
}
