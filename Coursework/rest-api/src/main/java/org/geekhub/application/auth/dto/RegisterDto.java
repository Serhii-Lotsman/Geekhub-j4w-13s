package org.geekhub.application.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record RegisterDto(
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Size(min = 6, max = 30, message = "Email must be between 6 and 30 characters long")
    @Email(message = "Invalid email format")
    @NonNull String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password cannot contain special characters")
    @NonNull String password,

    @NotBlank(message = "Confirm password is required")
    @NonNull String confirmPassword
) {
}
