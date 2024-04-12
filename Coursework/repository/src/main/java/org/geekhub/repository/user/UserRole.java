package org.geekhub.repository.user;

import org.geekhub.repository.enums.Role;

public class UserRole {
    private String email;
    private Role role;

    public UserRole() {
    }

    public UserRole(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
