package org.geekhub.application.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.geekhub.application.user.model.UserRole;
import org.springframework.lang.NonNull;

import java.util.List;

public class UserDto {

    private Long id;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Size(min = 6, max = 30, message = "Email must be between 6 and 30 characters long")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password cannot contain special characters")
    private String password;

    private List<UserRole> roles;

    public UserDto() {
    }

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDto(
        String email,
        String password,
        List<UserRole> roles
    ) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(@NonNull List<UserRole> roles) {
        this.roles = roles;
    }
}
