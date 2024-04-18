package org.geekhub.application.employeeCard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.hibernate.validator.internal.util.stereotypes.Immutable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record EmployeeCardDto(
    Long id,

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Invalid name format")
    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters long")
    @NonNull String firstName,

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Invalid name format")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters long")
    @NonNull String lastName,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NonNull LocalDate birthday,

    @Immutable
    @NonNull String email,

    @NonNull EmployeePosition employeePosition,

    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Invalid city format")
    @Size(max = 30, message = "City must be maximum 30 characters long")
    @Nullable String city,

    boolean isMarried,

    @NonNull EmployeeGender employeeGender,

    @NonNull LocalDate hireDate
) {
}
