package org.geekhub.application.employeeCard.dto;

import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record EmployeeCardDto(
    Long id,
    @NonNull String firstName,
    @NonNull String lastName,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NonNull LocalDate birthday,
    @NonNull String email,
    @NonNull EmployeePosition employeePosition,
    @Nullable String city,
    boolean isMarried,
    @NonNull EmployeeGender employeeGender,
    @NonNull LocalDate hireDate
) {
}
