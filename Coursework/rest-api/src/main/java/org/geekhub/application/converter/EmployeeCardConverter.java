package org.geekhub.application.converter;

import org.geekhub.application.employeeCard.dto.EmployeeCardDto;
import org.geekhub.application.auth.dto.RegisterDto;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.springframework.lang.NonNull;

public class EmployeeCardConverter {

    private EmployeeCardConverter() {
    }

    public static EmployeeCardEntity employeeFromDto(@NonNull RegisterDto registerDto) {
        EmployeeCardEntity employeeCardEntity = new EmployeeCardEntity();
        employeeCardEntity.setFullName(registerDto.fullName().trim());
        employeeCardEntity.setBirthday(registerDto.birthday());
        employeeCardEntity.setEmail(registerDto.email().trim());
        employeeCardEntity.setEmployeePosition(registerDto.employeePosition());
        employeeCardEntity.setCity(registerDto.city());
        employeeCardEntity.setMarried(registerDto.isMarried());
        employeeCardEntity.setEmployeeGender(registerDto.employeeGender());
        employeeCardEntity.setHireDate(registerDto.hireDate());
        return employeeCardEntity;
    }

    public static EmployeeCardDto toDto(@NonNull EmployeeCardEntity employeeCardEntity) {
        return new EmployeeCardDto(
            employeeCardEntity.getFullName(),
            employeeCardEntity.getBirthday(),
            employeeCardEntity.getEmployeePosition(),
            employeeCardEntity.getCity(),
            employeeCardEntity.isMarried(),
            employeeCardEntity.getEmployeeGender(),
            employeeCardEntity.getHireDate()
        );
    }
}
