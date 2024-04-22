package org.geekhub.application.user.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public class UserEntity {

    @Nullable
    private Long id;
    private String email;
    private String password;
    private List<UserRole> roles;

    public UserEntity() {
    }

    public UserEntity(
        @NonNull String email,
        @NonNull String password,
        @NonNull List<UserRole> roles
    ) {
        this.id = null;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(
        @NonNull Long id,
        @NonNull String email
    ) {
        this.id = id;
        this.email = email;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
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
