package org.geekhub.repository.employee.model;

import org.geekhub.repository.employee.enums.EmployeeGender;
import org.geekhub.repository.employee.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record EmployeeEntity(
    Long id,
    @NonNull String fullName,
    @NonNull LocalDate birthday,
    @NonNull String email,
    @NonNull EmployeePosition employeePosition,
    @Nullable String city,
    boolean isMarried,
    @NonNull EmployeeGender employeeGender,
    @NonNull LocalDate hireDate
) {

    public EmployeeEntity(
        @NonNull String fullName,
        @NonNull LocalDate birthday,
        @NonNull String email,
        @NonNull EmployeePosition employeePosition,
        @Nullable String city,
        boolean isMarried,
        @NonNull EmployeeGender employeeGender,
        @NonNull LocalDate hireDate
    ) {
        this(
            null,
            fullName,
            birthday,
            email,
            employeePosition,
            city,
            isMarried,
            employeeGender,
            hireDate
        );
    }
}
