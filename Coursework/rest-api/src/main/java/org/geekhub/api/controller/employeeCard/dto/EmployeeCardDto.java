package org.geekhub.api.controller.employeeCard.dto;

import org.geekhub.repository.enums.EmployeeGender;
import org.geekhub.repository.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record EmployeeCardDto(
    @NonNull String fullName,
    @NonNull LocalDate birthday,
    @NonNull EmployeePosition employeePosition,
    @Nullable String city,
    boolean isMarried,
    @NonNull EmployeeGender employeeGender,
    @NonNull LocalDate hireDate
) {
}
