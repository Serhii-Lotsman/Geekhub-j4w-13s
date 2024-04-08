package org.geekhub.repository.user;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {
    private Long id;
    private String email;
    private String password;
    private final List<UserRole> roles = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.roles.clear();
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }
}
