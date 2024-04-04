package org.geekhub.repository.employeeCard.model;

import org.geekhub.repository.employeeCard.enums.EmployeeGender;
import org.geekhub.repository.employeeCard.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record EmployeeCardEntity(
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

    public EmployeeCardEntity(
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
