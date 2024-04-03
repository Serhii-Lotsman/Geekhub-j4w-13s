package org.geekhub.crewcraft.employee.model;

import org.geekhub.repository.employee.enums.EmployeeGender;
import org.geekhub.repository.employee.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record EmployeeDTO(
    Long id,
    @NonNull String fullName,
    @NonNull LocalDate birthday,
    @NonNull String email,
    @NonNull EmployeePosition employeePosition,
    @Nullable String city,
    boolean isMarried,
    @NonNull EmployeeGender employeeGender,
    OffsetDateTime hireDate
) {
}
