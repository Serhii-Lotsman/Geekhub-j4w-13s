package org.geekhub.application.user.dto;

import org.geekhub.application.user.model.UserRole;
import org.springframework.lang.NonNull;

import java.util.List;

public class UserDto {

    private Long id;
    private String email;
    private String password;
    private List<UserRole> roles;

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDto(
        Long id,
        String email,
        List<UserRole> roles
    ) {
        this.id = id;
        this.email = email;
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
