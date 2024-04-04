package org.geekhub.crewcraft.employee.converter;

import org.geekhub.crewcraft.employee.dto.EmployeeDto;
import org.geekhub.repository.employee.model.EmployeeEntity;
import org.springframework.lang.NonNull;

public class EmployeeConverter {

    private EmployeeConverter() {
    }

    public static EmployeeEntity toEntity(@NonNull EmployeeDto employeeDto) {
        return new EmployeeEntity(
            employeeDto.fullName(),
            employeeDto.birthday(),
            employeeDto.email(),
            employeeDto.employeePosition(),
            employeeDto.city(),
            employeeDto.isMarried(),
            employeeDto.employeeGender(),
            employeeDto.hireDate()
        );
    }

    public static EmployeeDto toDTO(@NonNull EmployeeEntity employeeEntity) {
        return new EmployeeDto(
            employeeEntity.id(),
            employeeEntity.fullName(),
            employeeEntity.birthday(),
            employeeEntity.email(),
            employeeEntity.employeePosition(),
            employeeEntity.city(),
            employeeEntity.isMarried(),
            employeeEntity.employeeGender(),
            employeeEntity.hireDate()
        );
    }
}
