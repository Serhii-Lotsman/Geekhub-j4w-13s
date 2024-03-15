package org.geekhub.crewcraft.converter;

import org.geekhub.crewcraft.model.EmployeeDTO;
import org.geekhub.crewcraft.model.EmployeeEntity;
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
            employeeDTO.password(),
            employeeDTO.city(),
            employeeDTO.isMarried(),
            employeeDTO.employeeGender()
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
