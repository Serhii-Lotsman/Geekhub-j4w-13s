package org.geekhub.repository.user;

import org.geekhub.repository.enums.Role;

public class UserRole {
    private Integer id;
    private Role role;

    public UserRole() {
    }

    public UserRole(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
