package org.geekhub.crewcraft.employee.converter;

import org.geekhub.crewcraft.employee.model.EmployeeDTO;
import org.geekhub.repository.employee.model.EmployeeEntity;
import org.springframework.lang.NonNull;

public class EmployeeConverter {

    private EmployeeConverter() {
    }

    public static EmployeeEntity toEntity(@NonNull EmployeeDTO employeeDTO) {
        return new EmployeeEntity(
            employeeDTO.fullName(),
            employeeDTO.birthday(),
            employeeDTO.email(),
            employeeDTO.employeePosition(),
            null,
            employeeDTO.city(),
            employeeDTO.isMarried(),
            employeeDTO.employeeGender(),
            employeeDTO.hireDate()
        );
    }

    public static EmployeeDTO toDTO(@NonNull EmployeeEntity employeeEntity) {
        return new EmployeeDTO(
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
