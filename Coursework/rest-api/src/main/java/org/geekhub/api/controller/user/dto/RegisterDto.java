package org.geekhub.api.controller.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.geekhub.repository.enums.EmployeeGender;
import org.geekhub.repository.enums.EmployeePosition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record RegisterDto(
    @NotBlank(message = "Email is required")
    @Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters long")
    @Email(message = "Invalid email format")
    @NonNull String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password cannot contain special characters")
    @NonNull String password,
    @Pattern(regexp = "^[a-zA-Z]+\\s[a-zA-Z]+$", message = "Full name must be in the format 'First Last'")
    @Size(min = 3, max = 30, message = "Full name must be between 3 and 30 characters long")
    @NonNull String fullName,
    @NonNull LocalDate birthday,
    @NonNull EmployeePosition employeePosition,
    @Size(max = 30, message = "City must be maximum 30 characters long")
    @Nullable String city,
    boolean isMarried,
    @NonNull EmployeeGender employeeGender,
    @NonNull LocalDate hireDate
) {
}
