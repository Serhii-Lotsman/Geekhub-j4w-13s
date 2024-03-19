package org.geekhub.crewcraft.model;

import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record EmployeeEntity(
    Long id,
    @NonNull String fullName,
    @NonNull LocalDate birthday,
    @NonNull String email,
    @NonNull EmployeePosition employeePosition,
    String password,
    @Nullable String city,
    boolean isMarried,
    @NonNull EmployeeGender employeeGender,
    OffsetDateTime hireDate
) {

    public EmployeeEntity(
        @NonNull String fullName,
        @NonNull LocalDate birthday,
        @NonNull String email,
        @NonNull EmployeePosition employeePosition,
        String password,
        @Nullable String city,
        boolean isMarried,
        @NonNull EmployeeGender employeeGender,
        @NonNull OffsetDateTime hireDate
    ) {
        this(
            null,
            fullName,
            birthday,
            email,
            employeePosition,
            password,
            city,
            isMarried,
            employeeGender,
            hireDate
        );
    }
}
