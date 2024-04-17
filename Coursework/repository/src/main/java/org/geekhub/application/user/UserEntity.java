package org.geekhub.application.user;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public class UserEntity {
    @Nullable
    private Integer id;
    private String email;
    private String password;
    private List<UserRole> roles;

    public UserEntity() {
    }

    public UserEntity(@NonNull String email,
                      @NonNull String password,
                      @NonNull List<UserRole> roles) {
        this.id = null;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
